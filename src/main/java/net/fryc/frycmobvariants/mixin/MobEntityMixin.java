package net.fryc.frycmobvariants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Mob.class)
abstract class MobEntityMixin extends LivingEntity implements Targeting, EquipmentUser, Leashable, CanConvert {

    @Shadow protected abstract void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty);

    @Unique
    boolean canConvert = true;

    @Unique
    Random random = new Random();

    @Unique
    Runnable nextTickUpdate = null;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(at = @At("TAIL"), method = "tick()V")
    public void tryToConvertMob(CallbackInfo info) {
        Mob mob = ((Mob)(Object)this);
        if(!mob.level().isClientSide()){
            if(this.nextTickUpdate != null){
                this.nextTickUpdate.run();
                this.nextTickUpdate = null;
            }

            if(mob.hasEffect(MobEffects.NAUSEA)) this.canConvert = false;
            if(this.canConvert){
                MobConvertingHelper.detectMobAndTryToConvert(mob, this.random);
                this.canConvert = false;
            }
        }
    }

    @Inject(method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;", at = @At("TAIL"))
    private void preventConversionForSpecifiedSpawnReasons(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> ret) {
        if((spawnReason == EntitySpawnReason.SPAWN_ITEM_USE && !MobVariants.config.convertMobsSpawnedBySpawnEgg) ||
                (spawnReason == EntitySpawnReason.COMMAND && !MobVariants.config.convertMobsSpawnedByCommand) ||
                (spawnReason == EntitySpawnReason.SPAWNER && !MobVariants.config.convertMobsSpawnedByNormalSpawner) ||
                (spawnReason == EntitySpawnReason.TRIAL_SPAWNER && !MobVariants.config.convertMobsSpawnedByTrialSpawner)) {
            this.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 10, 0, false, false));
        }
    }

    @WrapOperation(
            method = "convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)Lnet/minecraft/world/entity/Mob;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;")
    )
    private Entity setNauseaAfterConverting(EntityType<? extends Mob> instance, Level level, EntitySpawnReason spawnReason, Operation<Entity> original) {
        Entity mobEntity = original.call(instance, level, spawnReason);
        if(mobEntity != null){
            if(mobEntity instanceof Mob mob){
                mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 10, 0, false, false));
                return mobEntity;
            }

            MobVariants.LOGGER.error(
                    "Trying to convert a mob to a non-mob entity! Given entity should extend 'Mob' but '" +
                            mobEntity.getClass() + "' does not! It may be caused by a conversion rule (invalid outcome mob)."
            );
        }

        return null;
    }

    //reading canConvert from Nbt
    @Inject(method = "readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(ValueInput input, CallbackInfo ci) {
        this.canConvert = input.getBooleanOr("MobVariantsCanConvert", true);
    }

    //writing canConvert to Nbt
    @Inject(method = "addAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueOutput;)V", at = @At("TAIL"))
    private void writeCanConvertToNbt(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("MobVariantsCanConvert", this.canConvert);
    }

    public void setCanConvertToTrue(){
        this.canConvert = true;
    }

    public void setCanConvertToFalse(){
        this.canConvert = false;
    }

    public void initMobEquipment() {
        this.populateDefaultEquipmentSlots(this.getRandom(), ((ServerLevel) this.level()).getCurrentDifficultyAt(this.blockPosition()));
    }

    public void setNextTickUpdate(Runnable nextTickUpdate) {
        this.nextTickUpdate = nextTickUpdate;
    }

}
