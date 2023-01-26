package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class FrozenZombieEntity extends ZombieEntity {

    private int ticksUntilDaylightConversion = 280;

    public FrozenZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createFrozenZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_ARMOR, 6.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200 * (int)f), this);
        }

        return bl;
    }


    public void tick(){
        super.tick();
        if((this.getWorld().getDimension().ultrawarm() || this.ticksUntilDaylightConversion <= 0) && this.isAlive()){
            float health = this.getHealth();
            int i = this.getFireTicks() / 20;
            this.playSound(SoundEvents.ENTITY_PLAYER_HURT_FREEZE, 1.0F, 0.4F);
            ZombieEntity zombie = this.convertTo(EntityType.ZOMBIE, true);
            if(zombie != null){
                zombie.setHealth(health);
                if(i > 0) zombie.setOnFireFor(i);
                zombie.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60));
            }
        }
        else{
            if(!this.inPowderSnow){
                if((this.getWorld().isDay() && this.getWorld().isSkyVisible(this.getBlockPos())) || this.isWet()){
                    this.ticksUntilDaylightConversion -= 1;
                }
                if(this.isOnFire()){
                    this.ticksUntilDaylightConversion -= 9;
                }
            }
            else {
                if(this.ticksUntilDaylightConversion < 280){
                    this.ticksUntilDaylightConversion += 2;
                }
            }
        }
    }


    protected boolean burnsInDaylight() {
        return false;
    }

    public boolean canFreeze() {
        return false;
    }

    public double getHeightOffset() {
        return this.isBaby() ? 0.0 : -0.45;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? 0.93F : 1.74F;
    }



}
