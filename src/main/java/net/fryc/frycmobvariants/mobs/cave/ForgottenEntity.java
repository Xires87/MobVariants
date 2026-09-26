package net.fryc.frycmobvariants.mobs.cave;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class ForgottenEntity extends Zombie {

    private static final int MINING_FATIGUE_DURATION = 120;


    public ForgottenEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward += 1;
    }

    public static AttributeSupplier.Builder createForgottenAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 18.0).add(Attributes.MOVEMENT_SPEED, 0.23200000417232513).add(Attributes.MAX_HEALTH, 26).add(Attributes.ATTACK_DAMAGE, 5.0).add(Attributes.ARMOR, 4.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }


    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean bl = super.doHurtTarget(level, target);
        if (bl && this.getMainHandItem().isEmpty() && target instanceof LivingEntity living) {
            float f = ((ServerLevel) this.level()).getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, MINING_FATIGUE_DURATION * (int)f, 1), this);
        }

        return bl;
    }

    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch() - 0.30F);
    }

    protected void playHurtSound(DamageSource source) {
        this.resetAmbientSoundTime();
        this.playSound(this.getHurtSound(source), this.getSoundVolume(), this.getVoicePitch() - 0.20F);

    }

    private void resetAmbientSoundTime() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }
}
