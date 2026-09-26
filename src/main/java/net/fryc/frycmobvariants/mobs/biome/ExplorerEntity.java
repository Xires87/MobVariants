package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;

public class ExplorerEntity extends Zombie {
    private static final EntityDataAccessor<Byte> EXPLORER_FLAGS_ID = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.BYTE);

    public ExplorerEntity(net.minecraft.world.entity.EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }


    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    //explorers take 70% less damage from falling
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if(source.is(DamageTypeTags.IS_FALL)){
            super.hurtServer(level, source, damage * 0.30F);
        }

        return super.hurtServer(level, source, damage);
    }

    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level());
    }


    public boolean onClimbable() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.entityData.get(EXPLORER_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean value) {
        byte flags = (Byte)this.entityData.get(EXPLORER_FLAGS_ID);
        if (value) {
            flags = (byte)(flags | 1);
        } else {
            flags = (byte)(flags & -2);
        }

        this.entityData.set(EXPLORER_FLAGS_ID, flags);
    }

    protected void defineSynchedData(final SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(EXPLORER_FLAGS_ID, (byte)0);
    }

    public boolean canFreeze() {
        return true;
    }

}
