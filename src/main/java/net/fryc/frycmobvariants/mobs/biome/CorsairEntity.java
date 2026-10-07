package net.fryc.frycmobvariants.mobs.biome;

import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class CorsairEntity extends Skeleton {

    public boolean searchingForLand;

    public static Map<Item, Pair<Float, Float>> corsairWeapons = new HashMap<>(Map.of(Items.WOODEN_SWORD, new Pair<>(0.0F, 0.72F)));

    public CorsairEntity(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new CorsairEntity.CorsairMoveControl<>(this);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new Drowned.DrownedGoToWaterGoal(this, (double)1.0F));
        this.goalSelector.addGoal(2, new CorsairMeleeAttackGoal(this, (double)1.0F, false));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, (double)1.0F, 1.2));
        this.goalSelector.addGoal(5, new CorsairEntity.CorsairGoToBeachGoal(this, (double)1.0F));
        this.goalSelector.addGoal(6, new CorsairEntity.CorsairSwimUpGoal(this, (double)1.0F, this.level().getSeaLevel()));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (double)1.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (target, level) -> this.okTarget(target)));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public static AttributeSupplier.Builder createCorsairAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 85).add(Attributes.STEP_HEIGHT, 1.0);
    }

    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, getCorsairSword());
    }

    protected boolean closeToNextPos() {
        Path path = this.getNavigation().getPath();
        if (path != null) {
            BlockPos pos = path.getTarget();
            if (pos != null) {
                double sqrDistToNextPos = this.distanceToSqr((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
                if (sqrDistToNextPos < (double)4.0F) {
                    return true;
                }
            }
        }

        return false;
    }

    protected PathNavigation createNavigation(final Level level) {
    return new AmphibiousPathNavigation(this, level);
}

    public boolean okTarget(final @Nullable LivingEntity target) {
        if (target != null) {
            return !this.level().isBrightOutside() || target.isInWater();
        } else {
            return false;
        }
    }

    public void setSearchingForLand(boolean searchingForLand) {
        this.searchingForLand = searchingForLand;
    }

    public boolean isSearchingForLand() {
        return this.searchingForLand;
    }

    public boolean wantsToSwim() {
        if (this.searchingForLand) {
            return true;
        } else {
            LivingEntity target = this.getTarget();
            return target != null && target.isInWater();
        }
    }

    public boolean isPushedByFluid() {
        return !this.isSwimming();
    }

    public static ItemStack getCorsairSword(){
        return MobConvertingHelper.getRandomItemStack(corsairWeapons);
    }


    public static class CorsairMoveControl<T extends CorsairEntity> extends MoveControl<T> {

        public CorsairMoveControl(T mob) {
            super(mob);
        }

        public void tick() {
            LivingEntity target = ((CorsairEntity)this.mob).getTarget();
            if (((CorsairEntity)this.mob).wantsToSwim() && ((CorsairEntity)this.mob).isInWater()) {
                if (target != null && target.getY() > ((CorsairEntity)this.mob).getY() || ((CorsairEntity)this.mob).isSearchingForLand()) {
                    ((CorsairEntity)this.mob).setDeltaMovement(((CorsairEntity)this.mob).getDeltaMovement().add((double)0.0F, 0.002, (double)0.0F));
                }

                if (this.operation != Operation.MOVE_TO || ((CorsairEntity)this.mob).getNavigation().isDone()) {
                    ((CorsairEntity)this.mob).setSpeed(0.0F);
                    return;
                }

                double xd = this.wantedX - ((CorsairEntity)this.mob).getX();
                double yd = this.wantedY - ((CorsairEntity)this.mob).getY();
                double zd = this.wantedZ - ((CorsairEntity)this.mob).getZ();
                double dd = Math.sqrt(xd * xd + yd * yd + zd * zd);
                yd /= dd;
                float yRotD = (float)(Mth.atan2(zd, xd) * (double)(180F / (float)Math.PI)) - 90.0F;
                ((CorsairEntity)this.mob).setYRot(this.rotlerp(((CorsairEntity)this.mob).getYRot(), yRotD, 90.0F));
                ((CorsairEntity)this.mob).yBodyRot = ((CorsairEntity)this.mob).getYRot();
                float targetSpeed = (float)(this.speedModifier * ((CorsairEntity)this.mob).getAttributeValue(Attributes.MOVEMENT_SPEED));
                float newSpeed = Mth.lerp(0.125F, ((CorsairEntity)this.mob).getSpeed(), targetSpeed);
                ((CorsairEntity)this.mob).setSpeed(newSpeed);
                ((CorsairEntity)this.mob).setDeltaMovement(((CorsairEntity)this.mob).getDeltaMovement().add((double)newSpeed * xd * 0.005, (double)newSpeed * yd * 0.1, (double)newSpeed * zd * 0.005));
            } else {
                if (!((CorsairEntity)this.mob).onGround()) {
                    ((CorsairEntity)this.mob).setDeltaMovement(((CorsairEntity)this.mob).getDeltaMovement().add((double)0.0F, -0.008, (double)0.0F));
                }

                super.tick();
            }

        }
    }

    public static class CorsairGoToBeachGoal extends MoveToBlockGoal {
        private final CorsairEntity corsair;

        public CorsairGoToBeachGoal(CorsairEntity corsair, final double speedModifier) {
            super(corsair, speedModifier, 8, 2);
            this.corsair = corsair;
        }

        public boolean canUse() {
            return super.canUse() && !this.corsair.level().isBrightOutside() && this.corsair.isInWater() && this.corsair.getY() >= (double)(this.corsair.level().getSeaLevel() - 3);
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        protected boolean isValidTarget(final LevelReader level, final BlockPos pos) {
            BlockPos above = pos.above();
            return level.isEmptyBlock(above) && level.isEmptyBlock(above.above()) ? level.getBlockState(pos).entityCanStandOn(level, pos, this.corsair) : false;
        }

        public void start() {
            this.corsair.setSearchingForLand(false);
            super.start();
        }

        public void stop() {
            super.stop();
        }
    }

    public static class CorsairSwimUpGoal extends Goal {
        private final CorsairEntity corsair;
        private final double speedModifier;
        private final int seaLevel;
        private boolean stuck;

        public CorsairSwimUpGoal(CorsairEntity corsair, double speedModifier, int seaLevel) {
            this.corsair = corsair;
            this.speedModifier = speedModifier;
            this.seaLevel = seaLevel;
        }

        public boolean canUse() {
            return !this.corsair.level().isBrightOutside() && this.corsair.isInWater() && this.corsair.getY() < (double)(this.seaLevel - 2);
        }

        public boolean canContinueToUse() {
            return this.canUse() && !this.stuck;
        }

        public void tick() {
            if (this.corsair.getY() < (double)(this.seaLevel - 1) && (this.corsair.getNavigation().isDone() || this.corsair.closeToNextPos())) {
                Vec3 nextPos = DefaultRandomPos.getPosTowards(this.corsair, 4, 8, new Vec3(this.corsair.getX(), (double)(this.seaLevel - 1), this.corsair.getZ()), (double)((float)Math.PI / 2F));
                if (nextPos == null) {
                    this.stuck = true;
                    return;
                }

                this.corsair.getNavigation().moveTo(nextPos.x, nextPos.y, nextPos.z, this.speedModifier);
            }

        }

        public void start() {
            this.corsair.setSearchingForLand(true);
            this.stuck = false;
        }

        public void stop() {
            this.corsair.setSearchingForLand(false);
        }
    }

    private static class CorsairMeleeAttackGoal extends MeleeAttackGoal {

        private final CorsairEntity corsair;


        public CorsairMeleeAttackGoal(CorsairEntity corsair, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(corsair, speedModifier, followingTargetEvenIfNotSeen);
            this.corsair = corsair;
        }

        public boolean canUse() {
            return super.canUse() && this.corsair.okTarget(this.corsair.getTarget());
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.corsair.okTarget(this.corsair.getTarget());
        }
    }
}
