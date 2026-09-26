package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class ToxicSlimeEntity extends Slime {

    private static final int EASY_POISON_DURATION = 40;
    private static final int NORMAL_POISON_DURATION = 80;
    private static final int HARD_POISON_DURATION = 120;

    public ToxicSlimeEntity(EntityType<? extends Slime> type, Level level) {
        super(type, level);
    }

    protected void dealDamage(LivingEntity target) {
        if (this.level() instanceof ServerLevel level) {
            if (this.isAlive() && this.doTeamsAllowDamage(target) && this.isWithinMeleeAttackRange(target) && this.hasLineOfSight(target)) {
                DamageSource damageSource = this.damageSources().mobAttack(this);
                if (target.hurtServer(level, damageSource, this.getAttackDamage())) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, this.getSize() * getPoisonDuration(this.level().getDifficulty()), 0));
                    this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    EnchantmentHelper.doPostAttackEffects(level, target, damageSource);
                }
            }
        }
    }

    protected boolean canDealDamage() {
        return this.isEffectiveAi();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance newEffect) {
        return newEffect.getEffect() != MobEffects.POISON && super.canBeAffected(newEffect);
    }

    private static int getPoisonDuration(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> EASY_POISON_DURATION;
            case NORMAL -> NORMAL_POISON_DURATION;
            case HARD -> HARD_POISON_DURATION;
            default -> 0;
        };
    }
}
