package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Zombie.class)
abstract class ZombieConvertMixin extends Monster implements CanConvert {

    @Unique
    private int inPowderSnowTime = 0;
    @Unique
    private int convertToFrozenZombieTime = 300;

    protected ZombieConvertMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    // TODO frozen zombie conversion

/*
    //converts zombie to frozen zombie
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToFrozenZombie(CallbackInfo info) {
        ZombieEntity zombie = ((ZombieEntity)(Object)this);
        if(!zombie.getWorld().isClient()){
            if(MobVariants.config.convertZombiesToFrozenZombiesInPowderSnow){
                if(zombie.isAlive() && !zombie.isAiDisabled() && !zombie.canFreeze() && !zombie.getType().equals(ModMobs.FROZEN_ZOMBIE)){
                    if (zombie.inPowderSnow) {
                        if (inPowderSnowTime >= 140) {
                            --convertToFrozenZombieTime;
                            if (convertToFrozenZombieTime < 0) {
                                zombie.playSound(SoundEvents.ENTITY_PLAYER_HURT_FREEZE, 1.0F, 0.4F);
                                zombie.convertTo(ModMobs.FROZEN_ZOMBIE, true);
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

 */
}
