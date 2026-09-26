package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Snowball.class)
abstract class SnowballEntityMixin {

    @ModifyVariable(method = "onHitEntity(Lnet/minecraft/util/hit/EntityHitResult;)V", at = @At("STORE"), ordinal = 0)
    private int dealDamageToLavaSlimes(int i, EntityHitResult entityHitResult) {
        if(entityHitResult.getEntity() instanceof LavaSlimeEntity){
            i += 2;
        }
        return i;
    }
}
