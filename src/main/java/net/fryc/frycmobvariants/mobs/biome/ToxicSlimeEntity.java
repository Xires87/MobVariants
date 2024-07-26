package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ToxicSlimeEntity extends SlimeEntity {

    public ToxicSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
        super(entityType, world);
    }


    protected void damage(LivingEntity target) {
        if (this.isAlive() && this.isInAttackRange(target) && this.canSee(target)) {
            DamageSource damageSource = this.getDamageSources().mobAttack(this);
            if (target.damage(damageSource, this.getDamageAmount())) {
                int i = this.getSize();
                int duration;
                if(this.getWorld().getDifficulty() == Difficulty.EASY){
                    duration = 40;
                }
                else if(this.getWorld().getDifficulty() == Difficulty.NORMAL){
                    duration = 80;
                }
                else{
                    duration = 120;
                }
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * duration, 0));
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                World var4 = this.getWorld();
                if (var4 instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)var4;
                    EnchantmentHelper.onTargetDamaged(serverWorld, target, damageSource);
                }
            }
        }
    }

    protected boolean canAttack() {
        return this.canMoveVoluntarily();
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if(effect.getEffectType() == StatusEffects.POISON) return false;
        return super.canHaveStatusEffect(effect);
    }
}
