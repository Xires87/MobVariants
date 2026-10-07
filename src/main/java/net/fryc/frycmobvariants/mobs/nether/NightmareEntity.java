package net.fryc.frycmobvariants.mobs.nether;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class NightmareEntity extends Ghast {

    private static final int ADDITIONAL_EXPLOSION_POWER = 1;


    public NightmareEntity(EntityType<? extends Ghast> type, Level level) {
        super(type, level);
        this.xpReward += 3;
    }

    protected void initGoals() {
        this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new GhastLookGoal(this));
        this.goalSelector.addGoal(7, new NightmareEntity.NightmareShootFireballGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, Player.class, 10, true, false, (target, level) -> {
                    return Math.abs(target.getY() - this.getY()) <= (double)4.0F;
                }
        ));
    }

    public static AttributeSupplier.Builder createNightmareAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0).add(Attributes.FOLLOW_RANGE, 100.0).add(Attributes.CAMERA_DISTANCE, 8.0F).add(Attributes.FLYING_SPEED, 0.06);
    }

    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch() - 0.15F);
    }

    protected void playHurtSound(DamageSource source) {
        this.resetSoundDelay();
        this.playSound(this.getHurtSound(source), this.getSoundVolume(), this.getVoicePitch() - 0.20F);
    }

    private void resetSoundDelay() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    public int getExplosionPower() {
        return super.getExplosionPower() + ADDITIONAL_EXPLOSION_POWER;
    }


    private static class NightmareShootFireballGoal extends Goal {
        private final Ghast ghast;
        public int cooldown;
        java.util.Random random = new java.util.Random();

        public NightmareShootFireballGoal(Ghast ghast) {
            this.ghast = ghast;
        }

        public boolean canUse() {
            return this.ghast.getTarget() != null;
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
            this.ghast.setCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.ghast.getTarget();
            if (livingEntity != null) {
                if (livingEntity.distanceToSqr(this.ghast) < 4096.0 && this.ghast.hasLineOfSight(livingEntity)) {
                    Level level = this.ghast.level();
                    ++this.cooldown;
                    if (this.cooldown == 10 && !this.ghast.isSilent()) {
                        level.levelEvent((Entity) null, 1015, this.ghast.blockPosition(), 0);
                    }

                    if (this.cooldown == 20) {
                        Vec3 vec3d = this.ghast.getViewVector(1.0F);
                        double f = livingEntity.getX() - (this.ghast.getX() + vec3d.x * 4.0);
                        double g = livingEntity.getY(0.5) - (0.5 + this.ghast.getY(0.5));
                        double h = livingEntity.getZ() - (this.ghast.getZ() + vec3d.z * 4.0);
                        if (!this.ghast.isSilent()) {
                            level.levelEvent((Entity)null, 1016, this.ghast.blockPosition(), 0);
                        }
                        if(this.ghast.distanceToSqr(livingEntity) < 800){
                            for(float j = 0.0f; j < 1.1f; j += 0.5f){
                                for(int i = -3; i<3; i++){
                                    SmallFireball fireballEntity = new SmallFireball(level, this.ghast, new Vec3(f + random.nextDouble(-2.8, 2.8), g + random.nextDouble(-3.5, 3.5), h + random.nextDouble(-2.8, 2.8)));
                                    fireballEntity.setPos(this.ghast.getX() + i + vec3d.x * 4.0, this.ghast.getY(0.5) + j, fireballEntity.getZ() + vec3d.z * 4.0);
                                    level.addFreshEntity(fireballEntity);
                                }
                            }
                        }
                        else {
                            LargeFireball fireballEntity = new LargeFireball(level, this.ghast, new Vec3(f,g,h), this.ghast.getExplosionPower());
                            fireballEntity.setPos(this.ghast.getX() + vec3d.x * 4.0, this.ghast.getY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                            level.addFreshEntity(fireballEntity);
                        }


                        this.cooldown = -40;
                    }
                } else if (this.cooldown > 0) {
                    --this.cooldown;
                }

                this.ghast.setCharging(this.cooldown > 10);
            }
        }
    }
}
