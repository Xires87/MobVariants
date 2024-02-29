package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class BloatedCorpseEntity extends ZombieEntity {

    public static final Predicate<LivingEntity> AFFECTED_BY_FIRE = (entity) -> {
        return !entity.isFireImmune() && entity.isAlive();
    };

    public BloatedCorpseEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.goalSelector.add(1, new SwimGoal(this));
    }

    public static DefaultAttributeContainer.Builder createBloatedCorpseAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21555000417232513).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    public void onDeath(DamageSource damageSource) {
        if(this.isOnFire() && !this.isWet()){
            this.spawnFireExplosion();
        }
        else {
            this.spawnNauseaCloudEffect();
        }
        super.onDeath(damageSource);
    }

    private void spawnNauseaCloudEffect(){
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(3.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(4);
        areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration());
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 320));

        this.getWorld().spawnEntity(areaEffectCloudEntity);
    }

    private void spawnFireExplosion(){
        this.spawnFireExplosionParticles();
        this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);

        Box box = this.getBoundingBox().expand(3.0, 3.0, 3.0);
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, AFFECTED_BY_FIRE);

        for (LivingEntity livingEntity : list) {
            double d = this.squaredDistanceTo(livingEntity);
            if (d < 12.0) {
                if(livingEntity.isWet()){
                    livingEntity.damage(this.getWorld().getDamageSources().onFire(), 0.1f);
                }
                else{
                    float damage = (float) (3.5-(d/4));
                    livingEntity.damage(this.getWorld().getDamageSources().inFire(), damage);
                    livingEntity.setOnFireFor((int)(damage+1));
                }
            }
        }
    }

    private void spawnFireExplosionParticles(){
        Random rand = this.getRandom();
        for(int i = 50; i > 0; i--){
            this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1), rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
        }
    }

    protected boolean canConvertInWater() {
        return false;
    }


}
