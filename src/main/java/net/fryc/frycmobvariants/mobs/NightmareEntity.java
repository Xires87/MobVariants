package net.fryc.frycmobvariants.mobs;


import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.EnumSet;


public class NightmareEntity extends GhastEntity {

    public NightmareEntity(EntityType<? extends GhastEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(5, new FlyRandomlyGoal(this));
        this.goalSelector.add(7, new LookAtTargetGoal(this));
        this.goalSelector.add(7, new NightmareEntity.ShootFireballGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, (entity) -> {
            return Math.abs(((LivingEntity)entity).getY() - this.getY()) <= 4.0;
        }));
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.6F;
    }

    public static DefaultAttributeContainer.Builder createNightmareAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
    }


    private static class FlyRandomlyGoal extends Goal {
        private final GhastEntity ghast;

        public FlyRandomlyGoal(GhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.ghast.getX();
                double e = moveControl.getTargetY() - this.ghast.getY();
                double f = moveControl.getTargetZ() - this.ghast.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        public boolean shouldContinue() {
            return false;
        }

        public void start() {
            Random random = this.ghast.getRandom();
            double d = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double f = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    static class LookAtTargetGoal extends Goal {
        private final GhastEntity ghast;

        public LookAtTargetGoal(GhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return true;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3d vec3d = this.ghast.getVelocity();
                this.ghast.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
                this.ghast.bodyYaw = this.ghast.getYaw();
            } else {
                LivingEntity livingEntity = this.ghast.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0) {
                    double e = livingEntity.getX() - this.ghast.getX();
                    double f = livingEntity.getZ() - this.ghast.getZ();
                    this.ghast.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.ghast.bodyYaw = this.ghast.getYaw();
                }
            }

        }
    }

    private static class ShootFireballGoal extends Goal {
        private final NightmareEntity ghast;
        public int cooldown;

        java.util.Random random = new java.util.Random();
        public ShootFireballGoal(NightmareEntity ghast) {
            this.ghast = ghast;
        }

        public boolean canStart() {
            return this.ghast.getTarget() != null;
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
            this.ghast.setShooting(false);
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.ghast.getTarget();
            if (livingEntity != null) {
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0 && this.ghast.canSee(livingEntity)) {
                    World world = this.ghast.world;
                    ++this.cooldown;
                    if (this.cooldown == 10 && !this.ghast.isSilent()) {
                        world.syncWorldEvent((PlayerEntity)null, 1015, this.ghast.getBlockPos(), 0);
                    }

                    if (this.cooldown == 20) {
                        double e = 4.0;
                        Vec3d vec3d = this.ghast.getRotationVec(1.0F);
                        double f = livingEntity.getX() - (this.ghast.getX() + vec3d.x * 4.0);
                        double g = livingEntity.getBodyY(0.5) - (0.5 + this.ghast.getBodyY(0.5));
                        double h = livingEntity.getZ() - (this.ghast.getZ() + vec3d.z * 4.0);
                        if (!this.ghast.isSilent()) {
                            world.syncWorldEvent((PlayerEntity)null, 1016, this.ghast.getBlockPos(), 0);
                        }
                        if(random.nextBoolean()){
                            for(int i = -3; i<3; i++){
                                SmallFireballEntity fireballEntity = new SmallFireballEntity(world, this.ghast, f + random.nextDouble(-2.0, 2.0), g + random.nextDouble(-2.0, 2.0), h + + random.nextDouble(-2.0, 2.0));
                                fireballEntity.setPosition(this.ghast.getX() + i + vec3d.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                                world.spawnEntity(fireballEntity);
                            }
                        }
                        else {
                            FireballEntity fireballEntity = new FireballEntity(world, this.ghast, f, g, h, this.ghast.getFireballStrength() + 1);
                            fireballEntity.setPosition(this.ghast.getX() + vec3d.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                            world.spawnEntity(fireballEntity);
                        }


                        this.cooldown = -40;
                    }
                } else if (this.cooldown > 0) {
                    --this.cooldown;
                }

                this.ghast.setShooting(this.cooldown > 10);
            }
        }
    }
}
