package net.fryc.frycmobvariants.mobs.biome;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class FrozenZombieEntity extends Zombie {

    private int ticksUntilDaylightConversion = 280;

    public FrozenZombieEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createFrozenZombieAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.23000000417232513).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 6.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean bl = super.doHurtTarget(level, target);
        if (bl && this.getMainHandItem().isEmpty() && target instanceof LivingEntity) {
            float f = level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 260 * (int)f), this);
        }

        return bl;
    }


    public void tick(){
        super.tick();
        if(!this.level().isClientSide()){
            if(MobVariants.config.enableFrozenZombieConvertingToNormalZombie){
                if(this.isAlive() && !this.isNoAi()){
                    if((this.level().dimensionType().attributes().contains(EnvironmentAttributes.WATER_EVAPORATES) || this.ticksUntilDaylightConversion <= 0)){
                        float health = this.getHealth();
                        int i = this.getRemainingFireTicks();
                        this.playSound(SoundEvents.PLAYER_HURT_FREEZE, 1.0F, 0.4F);
                        this.convertTo(EntityTypes.ZOMBIE, new ConversionParams(ConversionType.SINGLE, true, true, this.getTeam()), mob -> {
                            mob.setHealth(health);
                            if(i > 0) mob.setRemainingFireTicks(i);
                            mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 60));
                        });
                    }
                    else{
                        if(!this.isInPowderSnow){
                            if(this.isInWaterOrRain() || (this.level().environmentAttributes().getValue(EnvironmentAttributes.MONSTERS_BURN, this.position()) && this.level().canSeeSky(this.blockPosition()))){
                                this.ticksUntilDaylightConversion -= 1;
                            }
                            if(this.isOnFire()){
                                this.ticksUntilDaylightConversion -= 7;
                            }
                        }
                        else {
                            if(this.ticksUntilDaylightConversion < 280){
                                this.ticksUntilDaylightConversion += 2;
                            }
                        }
                    }
                }
            }
        }
    }


    protected boolean isSunSensitive() {
        return false;
    }

    public boolean canFreeze() {
        return false;
    }

}
