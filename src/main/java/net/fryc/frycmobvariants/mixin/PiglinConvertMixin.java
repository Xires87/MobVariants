package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PiglinEntity.class)
abstract class PiglinConvertMixin extends AbstractPiglinEntity implements CrossbowUser, InventoryOwner {
    boolean canConvert = true;
    Random random = new Random();

    public PiglinConvertMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }


    //converts piglin to infected piglin
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "mobTick()V")
    public void convertToPiglinVariant(CallbackInfo info) {
        if(!world.isClient){
            PiglinEntity piglin = ((PiglinEntity)(Object)this);
            if(piglin.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                if(piglin.getClass() == PiglinEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.piglinConvertChance){
                        piglin.convertTo(ModMobs.INFECTED_PIGLIN, true);
                    }
                    canConvert = false;
                }
            }
        }
    }
}
