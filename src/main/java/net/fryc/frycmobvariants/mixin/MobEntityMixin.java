package net.fryc.frycmobvariants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
abstract class MobEntityMixin extends LivingEntity implements EquipmentHolder, Leashable, Targeter, CanConvert {

    @Shadow protected abstract void initEquipment(net.minecraft.util.math.random.Random random, LocalDifficulty localDifficulty);

    @Unique
    boolean canConvert = true;

    @Unique
    Random random = new Random();

    @Unique
    Runnable nextTickUpdate = null;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("TAIL"), method = "tick()V")
    public void tryToConvertMob(CallbackInfo info) {
        MobEntity mob = ((MobEntity)(Object)this);
        if(!mob.getWorld().isClient()){
            if(this.nextTickUpdate != null){
                this.nextTickUpdate.run();
                this.nextTickUpdate = null;
            }

            if(mob.hasStatusEffect(StatusEffects.NAUSEA)) this.canConvert = false;
            if(this.canConvert){
                MobConvertingHelper.detectMobAndTryToConvert(mob, this.random);
                this.canConvert = false;
            }
        }
    }

    @Inject(method = "initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;" +
            "Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;)Lnet/minecraft/entity/EntityData;", at = @At("TAIL"))
    private void preventConversionForSpecifiedSpawnReasons(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, CallbackInfoReturnable<EntityData> ret) {
        if((spawnReason == SpawnReason.SPAWN_EGG && !MobVariants.config.convertMobsSpawnedBySpawnEgg) ||
                (spawnReason == SpawnReason.COMMAND && !MobVariants.config.convertMobsSpawnedByCommand) ||
                (spawnReason == SpawnReason.SPAWNER && !MobVariants.config.convertMobsSpawnedByNormalSpawner) ||
                (spawnReason == SpawnReason.TRIAL_SPAWNER && !MobVariants.config.convertMobsSpawnedByTrialSpawner)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10, 0, false, false));
        }
    }

    @WrapOperation(
            method = "convertTo(Lnet/minecraft/entity/EntityType;Z)Lnet/minecraft/entity/mob/MobEntity;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;")
    )
    private Entity setNauseaAfterConverting(EntityType<? extends MobEntity> instance, World world, Operation<Entity> original) {
        Entity mobEntity = original.call(instance, world);
        if(mobEntity != null){
            if(mobEntity instanceof MobEntity mob){
                mob.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10, 0, false, false));
                return mobEntity;
            }

            MobVariants.LOGGER.error(
                    "Trying to convert a mob to a non-mob entity! Given entity should extend 'MobEntity' but '" +
                            mobEntity.getClass() + "' does not! It may be caused by a conversion rule (invalid outcome mob)."
            );
        }

        return null;
    }

    //reading canConvert from Nbt
    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("MobVariantsCanConvert")){
            NbtCompound nbtCompound = nbt.getCompound("MobVariantsCanConvert");
            this.canConvert = nbtCompound.getBoolean("canConvert");
        }
    }

    //writing canConvert to Nbt
    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void writeCanConvertToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(!this.canConvert){
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("canConvert", false);
            nbt.put("MobVariantsCanConvert", nbtCompound);
        }
    }

    public void setCanConvertToTrue(){
        this.canConvert = true;
    }

    public void setCanConvertToFalse(){
        this.canConvert = false;
    }

    public void initMobEquipment() {
        this.initEquipment(this.getRandom(), this.getWorld().getLocalDifficulty(this.getBlockPos()));
    }

    public void setNextTickUpdate(Runnable nextTickUpdate) {
        this.nextTickUpdate = nextTickUpdate;
    }

}
