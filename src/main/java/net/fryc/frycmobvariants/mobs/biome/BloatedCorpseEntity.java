package net.fryc.frycmobvariants.mobs.biome;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class BloatedCorpseEntity extends Zombie {

    public static final Predicate<LivingEntity> AFFECTED_BY_FIRE = (entity) -> {
        return !entity.fireImmune() && entity.isAlive();
    };

    public BloatedCorpseEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createBloatedCorpseAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 18).add(Attributes.MOVEMENT_SPEED, 0.21555000417232513).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 2.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected void triggerOnDeathMobEffects(ServerLevel level, RemovalReason reason) {
        super.triggerOnDeathMobEffects(level, reason);
        if(reason == RemovalReason.KILLED) {
            if(this.isOnFire() && !this.isInWaterOrRain()){
                this.spawnFireExplosion();
            }
            else {
                this.spawnNauseaCloudEffect();
            }
        }
    }

    private void spawnNauseaCloudEffect(){
        AreaEffectCloud areaEffectCloudEntity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(3.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(4);
        areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration());
        areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        //areaEffectCloudEntity.setColor(8888888);
        areaEffectCloudEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 320));
        if(MobVariants.config.bloatedCorpsesCloudPoisonDamageDifficulty.hasCorrectDifficulty(this.level())){
            areaEffectCloudEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 34));
        }

        this.level().addFreshEntity(areaEffectCloudEntity);
    }

    private void spawnFireExplosion(){
        this.spawnFireExplosionParticles();
        this.playSound(SoundEvents.BLAZE_SHOOT, 1.0f, 1.0f);

        AABB box = this.getBoundingBox().expandTowards(3.0, 3.0, 3.0);
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, box, AFFECTED_BY_FIRE);

        for (LivingEntity livingEntity : list) {
            double d = this.distanceToSqr(livingEntity);
            if (d < 12.0 && this.hasLineOfSight(livingEntity)) {
                if(livingEntity.isInWaterOrRain()){
                    livingEntity.hurtServer(((ServerLevel) this.level()), this.level().damageSources().onFire(), 0.1f);
                }
                else{
                    float damage = (float) (3.5-(d/4));
                    livingEntity.hurtServer(((ServerLevel) this.level()), this.level().damageSources().inFire(), damage);
                    livingEntity.igniteForTicks((int)(damage+1));
                }
            }
        }
    }

    private void spawnFireExplosionParticles(){
        RandomSource rand = this.getRandom();
        for(int i = 60; i > 0; i--){
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
        }
    }

    protected boolean convertsInWater() {
        return false;
    }


}
