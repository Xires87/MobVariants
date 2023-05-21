package net.fryc.frycmobvariants.mobs.biome;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class CorsairEntity extends SkeletonEntity {
    boolean targetingUnderwater;
    protected final SwimNavigation waterNavigation;
    protected final MobNavigation landNavigation;

    public CorsairEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.0F);
        this.moveControl = new CorsairEntity.CorsairMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.waterNavigation = new SwimNavigation(this, world);
        this.landNavigation = new MobNavigation(this, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new CorsairEntity.WanderAroundOnSurfaceGoal(this, 1.0));
        this.goalSelector.add(3, new FleeEntityGoal(this, WolfEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(5, new CorsairEntity.LeaveWaterGoal(this, 1.0));
        this.goalSelector.add(6, new CorsairEntity.TargetAboveWaterGoal(this, 1.0, this.world.getSeaLevel()));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));

    }

    public static DefaultAttributeContainer.Builder createCorsairAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 85);
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(random.nextInt(100) < MobVariants.config.corsairSpawnWithSwordChance) this.equipStack(EquipmentSlot.MAINHAND, getCorsairSword());
    }

    protected boolean hasFinishedCurrentPath() {
        Path path = this.getNavigation().getCurrentPath();
        if (path != null) {
            BlockPos blockPos = path.getTarget();
            if (blockPos != null) {
                double d = this.squaredDistanceTo((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
                if (d < 4.0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void updateSwimming() {
        if (!this.world.isClient) {
            if (this.canMoveVoluntarily() && this.isTouchingWater() && this.isTargetingUnderwater()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.landNavigation;
                this.setSwimming(false);
            }
        }

    }

    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater() && this.isTargetingUnderwater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
        } else {
            super.travel(movementInput);
        }

    }

    public void setTargetingUnderwater(boolean targetingUnderwater) {
        this.targetingUnderwater = targetingUnderwater;
    }

    boolean isTargetingUnderwater() {
        if (this.targetingUnderwater) {
            return true;
        } else {
            LivingEntity livingEntity = this.getTarget();
            return livingEntity != null && livingEntity.isTouchingWater();
        }
    }

    public static ItemStack getCorsairSword(){
        return new ItemStack(Items.WOODEN_SWORD);
    }



    //move control
    private static class CorsairMoveControl extends MoveControl {
        private final CorsairEntity corsair;

        public CorsairMoveControl(CorsairEntity corsair) {
            super(corsair);
            this.corsair = corsair;
        }

        public void tick() {
            LivingEntity livingEntity = this.corsair.getTarget();
            if (this.corsair.isTargetingUnderwater() && this.corsair.isTouchingWater()) {
                if (livingEntity != null && livingEntity.getY() > this.corsair.getY() || this.corsair.targetingUnderwater) {
                    this.corsair.setVelocity(this.corsair.getVelocity().add(0.0, 0.002, 0.0));
                }

                if (this.state != State.MOVE_TO || this.corsair.getNavigation().isIdle()) {
                    this.corsair.setMovementSpeed(0.0F);
                    return;
                }

                double d = this.targetX - this.corsair.getX();
                double e = this.targetY - this.corsair.getY();
                double f = this.targetZ - this.corsair.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                this.corsair.setYaw(this.wrapDegrees(this.corsair.getYaw(), h, 90.0F));
                this.corsair.bodyYaw = this.corsair.getYaw();
                float i = (float)(this.speed * this.corsair.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                float j = MathHelper.lerp(0.125F, this.corsair.getMovementSpeed(), i);
                this.corsair.setMovementSpeed(j);
                this.corsair.setVelocity(this.corsair.getVelocity().add((double)j * d * 0.005, (double)j * e * 0.1, (double)j * f * 0.005));
            } else {
                if (!this.corsair.onGround) {
                    this.corsair.setVelocity(this.corsair.getVelocity().add(0.0, -0.008, 0.0));
                }

                super.tick();
            }

        }
    }




    //goal classes
    private static class LeaveWaterGoal extends MoveToTargetPosGoal {
        private final CorsairEntity corsair;

        public LeaveWaterGoal(CorsairEntity corsair, double speed) {
            super(corsair, speed, 8, 2);
            this.corsair = corsair;
        }

        public boolean canStart() {
            return super.canStart() && !this.corsair.world.isDay() && this.corsair.isTouchingWater() && this.corsair.getY() >= (double)(this.corsair.world.getSeaLevel() - 3);
        }

        public boolean shouldContinue() {
            return super.shouldContinue();
        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockPos blockPos = pos.up();
            return world.isAir(blockPos) && world.isAir(blockPos.up()) ? world.getBlockState(pos).hasSolidTopSurface(world, pos, this.corsair) : false;
        }

        public void start() {
            this.corsair.setTargetingUnderwater(false);
            this.corsair.navigation = this.corsair.landNavigation;
            super.start();
        }

        public void stop() {
            super.stop();
        }
    }

    private static class TargetAboveWaterGoal extends Goal {
        private final CorsairEntity corsair;
        private final double speed;
        private final int minY;
        private boolean foundTarget;

        public TargetAboveWaterGoal(CorsairEntity corsair, double speed, int minY) {
            this.corsair = corsair;
            this.speed = speed;
            this.minY = minY;
        }

        public boolean canStart() {
            return !this.corsair.world.isDay() && this.corsair.isTouchingWater() && this.corsair.getY() < (double)(this.minY - 2);
        }

        public boolean shouldContinue() {
            return this.canStart() && !this.foundTarget;
        }

        public void tick() {
            if (this.corsair.getY() < (double)(this.minY - 1) && (this.corsair.getNavigation().isIdle() || this.corsair.hasFinishedCurrentPath())) {
                Vec3d vec3d = NoPenaltyTargeting.findTo(this.corsair, 4, 8, new Vec3d(this.corsair.getX(), (double)(this.minY - 1), this.corsair.getZ()), 1.5707963705062866);
                if (vec3d == null) {
                    this.foundTarget = true;
                    return;
                }

                this.corsair.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }

        }

        public void start() {
            this.corsair.setTargetingUnderwater(true);
            this.foundTarget = false;
        }

        public void stop() {
            this.corsair.setTargetingUnderwater(false);
        }
    }

    private static class WanderAroundOnSurfaceGoal extends Goal {
        private final PathAwareEntity mob;
        private double x;
        private double y;
        private double z;
        private final double speed;
        private final World world;

        public WanderAroundOnSurfaceGoal(PathAwareEntity mob, double speed) {
            this.mob = mob;
            this.speed = speed;
            this.world = mob.world;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (!this.world.isDay()) {
                return false;
            } else if (this.mob.isTouchingWater()) {
                return false;
            } else {
                Vec3d vec3d = this.getWanderTarget();
                if (vec3d == null) {
                    return false;
                } else {
                    this.x = vec3d.x;
                    this.y = vec3d.y;
                    this.z = vec3d.z;
                    return true;
                }
            }
        }

        public boolean shouldContinue() {
            return !this.mob.getNavigation().isIdle();
        }

        public void start() {
            this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
        }

        @Nullable
        private Vec3d getWanderTarget() {
            Random random = this.mob.getRandom();
            BlockPos blockPos = this.mob.getBlockPos();

            for(int i = 0; i < 10; ++i) {
                BlockPos blockPos2 = blockPos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
                if (this.world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                    return Vec3d.ofBottomCenter(blockPos2);
                }
            }

            return null;
        }
    }

}
