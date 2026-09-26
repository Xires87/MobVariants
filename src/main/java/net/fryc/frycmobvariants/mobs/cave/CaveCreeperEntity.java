package net.fryc.frycmobvariants.mobs.cave;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class CaveCreeperEntity extends Creeper {

    private static final int EXPLOSION_RADIUS = 3;
    private int instantExplodeTime = 33;

    public CaveCreeperEntity(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }


    public void tick(){
        if (this.isAlive()) {
            if(this.instantExplodeTime < 33) this.instantExplodeTime++;
            if(this.isOnFire()) this.instantExplodeTime -= 3;
            if(this.instantExplodeTime <= 0) this.explodeCreeper();
        }
        super.tick();
    }

    private void explodeCreeper() {
        if (this.level() instanceof ServerLevel level) {
            float explosionMultiplier = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float)EXPLOSION_RADIUS * explosionMultiplier, Level.ExplosionInteraction.MOB);
            this.spawnLingeringCloud();
            this.triggerOnDeathMobEffects(level, RemovalReason.KILLED);
            this.discard();
        }

    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> activeEffects = this.getActiveEffects();
        if (!activeEffects.isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(300);
            cloud.setPotionDurationScale(0.25F);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());

            for(MobEffectInstance mobEffect : activeEffects) {
                cloud.addEffect(new MobEffectInstance(mobEffect));
            }

            this.level().addFreshEntity(cloud);
        }
    }
}
