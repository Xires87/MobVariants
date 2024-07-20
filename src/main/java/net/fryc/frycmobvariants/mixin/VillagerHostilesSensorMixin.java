package net.fryc.frycmobvariants.mixin;

import com.google.common.collect.ImmutableSet;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerHostilesSensor.class)
abstract class VillagerHostilesSensorMixin {

    // villagers flee from mobs from this set
    private static final ImmutableSet<EntityType<?>> MOD_SQUARED_DISTANCES_FOR_DANGER = ImmutableSet.of(
            ModMobs.FORGOTTEN,
            ModMobs.EXPLORER,
            ModMobs.BLOATED_CORPSE,
            ModMobs.FROZEN_ZOMBIE
    );

    @Inject(method = "matches(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void shouldRunFromZombie(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> ret) {
        if(MOD_SQUARED_DISTANCES_FOR_DANGER.contains(target.getType())){
            if(target.squaredDistanceTo(entity) <= 64.0){
                ret.setReturnValue(true);
            }
        }
    }

}
