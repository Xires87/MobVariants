package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
abstract class PiglinBrainMixin {

// TODO pozmieniac nazwy tych mixinow zeby pasowaly
    @Inject(method = "isZombified(Lnet/minecraft/world/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private static void isScaredOfZombifiedPiglinBrute(EntityType<?> entityType, CallbackInfoReturnable<Boolean> ret) {
        if(entityType == ModMobs.ZOMBIFIED_PIGLIN_BRUTE){
            ret.setReturnValue(true);
        }
    }

}
