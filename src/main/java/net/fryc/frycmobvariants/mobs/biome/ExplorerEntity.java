package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExplorerEntity extends ZombieEntity {
    private static final TrackedData<Byte> EXPLORER_FLAGS;

    public ExplorerEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }

    }

    //explorers take 70% less damage from falling
    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.isFromFalling()){
            amount*=0.30;
            super.damage(source, amount);
        }
        else{
            super.damage(source, amount);
        }
        return true;
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }


    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return ((Byte)this.dataTracker.get(EXPLORER_FLAGS) & 1) != 0;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.dataTracker.get(EXPLORER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(EXPLORER_FLAGS, b);
    }

    public double getHeightOffset() {
        return this.isBaby() ? 0.0 : -0.45;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? 0.93F : 1.74F;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(EXPLORER_FLAGS, (byte)0);
    }

    public boolean canFreeze() {
        return true;
    }


    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

    static {
        EXPLORER_FLAGS = DataTracker.registerData(ExplorerEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
