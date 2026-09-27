package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
abstract class PiglinBrainMixin {

    @Inject(method = "isZombified(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private static void isScaredOfZombifiedPiglinBrute(Entity entity, CallbackInfoReturnable<Boolean> ret) {
        if(entity.getType() == ModMobs.ZOMBIFIED_PIGLIN_BRUTE){
            ret.setReturnValue(true);
        }
    }

}
