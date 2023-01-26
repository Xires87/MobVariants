package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PiglinBruteEntity.class)
abstract class PiglinBruteConvertMixin extends AbstractPiglinEntity {

    boolean canConvert = true;
    Random random = new Random();

    public PiglinBruteConvertMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }


    //converts piglin brute to infected piglin brute
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "mobTick()V")
    public void convertToPiglinBruteVariant(CallbackInfo info) {
        if(!world.isClient){
            PiglinBruteEntity piglin = ((PiglinBruteEntity)(Object)this);
            if(piglin.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                if(piglin.getClass() == PiglinBruteEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.piglinBruteConvertChance){
                        piglin.convertTo(ModMobs.INFECTED_PIGLIN_BRUTE, true);
                    }
                    canConvert = false;
                }
            }
        }
    }
}
