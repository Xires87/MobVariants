package net.fryc.frycmobvariants.mobs;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;
import java.util.Iterator;

public class CaveCreeperEntity extends CreeperEntity {

    private int explosionRadius = 3;
    private int instantExplodeTime = 33;

    public CaveCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }


    public void tick(){
        if (this.isAlive()) {
            if(this.instantExplodeTime < 33) this.instantExplodeTime++;
            if(this.isOnFire()) this.instantExplodeTime -= 3;
            if(this.instantExplodeTime <= 0) this.explode();
        }
        super.tick();
    }

    private void explode() {
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            float f = this.shouldRenderOverlay() ? 2.0F : 1.0F;
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, destructionType);
            this.discard();
            this.spawnEffectsCloud();
        }

    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.world.spawnEntity(areaEffectCloudEntity);
        }

    }
}
