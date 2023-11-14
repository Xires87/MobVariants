package net.fryc.frycmobvariants.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
abstract class MobEntityMixin {


    // prevents mobs from converting when spawned with spawn egg or command
    @Inject(method = "initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;" +
            "Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;", at = @At("TAIL"))
    private void setNauseaAfterSpawningWithEgg(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                               @Nullable EntityData entityData, @Nullable NbtCompound entityNbt, CallbackInfoReturnable<EntityData> ret) {
        MobEntity dys = ((MobEntity)(Object)this);
        if(spawnReason == SpawnReason.SPAWN_EGG || spawnReason == SpawnReason.COMMAND){
            if(dys != null){
                dys.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10, 0, false, false));
            }
        }
    }
}
