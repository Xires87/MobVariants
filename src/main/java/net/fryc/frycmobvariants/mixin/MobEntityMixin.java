package net.fryc.frycmobvariants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
abstract class MobEntityMixin extends LivingEntity implements EquipmentHolder, Targeter {

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // prevents mobs from converting when spawned with spawn egg or command
    @Inject(method = "initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;" +
            "Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;)Lnet/minecraft/entity/EntityData;", at = @At("TAIL"))
    private void setNauseaAfterSpawningWithEgg(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                               @Nullable EntityData entityData, CallbackInfoReturnable<EntityData> ret) {
        if(spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND){
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
            }
        }
        return mobEntity;
    }

}
