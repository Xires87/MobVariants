package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.ConversionType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
abstract class ZombieConvertMixin extends Monster implements CanConvert {

    @Unique
    private int inPowderSnowTime = 0;
    @Unique
    private int convertToFrozenZombieTime = 300;

    protected ZombieConvertMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }



    //converts zombie to frozen zombie
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToFrozenZombie(CallbackInfo info) {
        Zombie zombie = ((Zombie)(Object)this);
        if(!zombie.level().isClientSide()){
            if(MobVariants.config.convertZombiesToFrozenZombiesInPowderSnow){
                if(zombie.isAlive() && !zombie.isNoAi() && !zombie.canFreeze() && !zombie.getType().equals(ModMobs.FROZEN_ZOMBIE)){
                    if (zombie.isInPowderSnow) {
                        if (inPowderSnowTime >= 140) {
                            --convertToFrozenZombieTime;
                            if (convertToFrozenZombieTime < 0) {
                                zombie.playSound(SoundEvents.PLAYER_HURT_FREEZE, 1.0F, 0.4F);
                                zombie.convertTo(ModMobs.FROZEN_ZOMBIE, new ConversionParams(ConversionType.SINGLE, true, true, zombie.getTeam()), EntitySpawnReason.NATURAL, mob -> {});
                            }
                        } else {
                            ++inPowderSnowTime;
                            if (inPowderSnowTime >= 140) {
                                convertToFrozenZombieTime = 300;
                            }
                        }
                    } else {
                        inPowderSnowTime = -1;
                    }
                }
            }
        }
    }


}
