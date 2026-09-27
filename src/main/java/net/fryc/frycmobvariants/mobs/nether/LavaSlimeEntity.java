package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.mixin_interfaces.BlockRemovalCountdown;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.cubemob.MagmaCube;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;


public class LavaSlimeEntity extends MagmaCube {

    public LavaSlimeEntity(EntityType<? extends MagmaCube> entityType, Level level) {
        super(entityType, level);
        this.xpReward += 3;
    }

    public static AttributeSupplier.Builder createLavaSlimeAttributes() {
        return MagmaCube.createAttributes();
    }


    public void remove(Entity.RemovalReason reason) {
        int i = this.getSize();
        if (!this.level().isClientSide() && i > 2 && this.isDeadOrDying()) {
            BlockPos pos = this.blockPosition();
            ServerLevel level = (ServerLevel) this.level();
            if(level.getBlockState(pos).isAir() || level.getBlockState(pos).canBeReplaced()){
                level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                int time = MobVariants.config.timeToRemoveLavaLeftByLavaSlime;
                if(time > 10){
                    ((BlockRemovalCountdown) level).startLavaRemovalCountdown(pos, time);
                }
            }
        }

        super.remove(reason);
    }

    protected void dealDamage(LivingEntity target) {
        if(this.level() instanceof ServerLevel level) {
            if (this.isAlive() && this.doTeamsAllowDamage(target) && this.isWithinMeleeAttackRange(target) && this.hasLineOfSight(target)) {
                DamageSource damageSource = this.damageSources().mobAttack(this);
                if (target.hurtServer(level, damageSource, this.getAttackDamage())) {
                    int i = this.getSize();
                    if(level.getDifficulty() == Difficulty.NORMAL){
                        target.igniteForTicks(i*2);
                    }
                    else if(level.getDifficulty() == Difficulty.HARD){
                        target.igniteForTicks(i*3);
                    }

                    this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    EnchantmentHelper.doPostAttackEffects(level, target, damageSource);
                }
            }
        }
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    protected SoundEvent getJumpSound() {
        if(this.getSize() > 2){
            this.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 0.78f, this.getSoundPitch());
        }
        return super.getJumpSound();
    }
}
