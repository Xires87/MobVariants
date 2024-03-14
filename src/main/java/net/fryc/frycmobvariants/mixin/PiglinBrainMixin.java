package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
abstract class PiglinBrainMixin {


    @Inject(method = "isZombified(Lnet/minecraft/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private static void isScaredOfZombifiedPiglinBrute(EntityType<?> entityType, CallbackInfoReturnable<Boolean> ret) {
        if(entityType == ModMobs.ZOMBIFIED_PIGLIN_BRUTE){
            ret.setReturnValue(true);
        }
    }

}
