package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.BlockRemovalCountdown;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class LavaSlimeEntity extends MagmaCubeEntity {

    public LavaSlimeEntity(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createLavaSlimeAttributes() {
        return MagmaCubeEntity.createMagmaCubeAttributes();
    }


    public void remove(Entity.RemovalReason reason) {
        int i = this.getSize();
        if (!this.getWorld().isClient() && i > 2 && this.isDead()) {
            BlockPos pos = this.getBlockPos();
            ServerWorld world = (ServerWorld) this.getWorld();
            if(world.getBlockState(pos).isAir() || world.getBlockState(pos).isReplaceable()){
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                int time = MobVariants.config.timeToRemoveLavaLeftByLavaSlime;
                if(time > 10){
                    ((BlockRemovalCountdown) world).startLavaRemovalCountdown(pos, time);
                }
            }
        }

        super.remove(reason);
    }

    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.squaredDistanceTo(target) < 0.6 * (double)i * 0.6 * (double)i && this.canSee(target) && target.damage(this.getDamageSources().mobAttack(this), this.getDamageAmount())) {
                if(this.getWorld().getDifficulty() == Difficulty.NORMAL){
                    target.setOnFireFor(i*2);
                }
                else if(this.getWorld().getDifficulty() == Difficulty.HARD){
                    target.setOnFireFor(i*3);
                }
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.applyDamageEffects(this, target);
            }
        }

    }

    public boolean hurtByWater() {
        return true;
    }
}
