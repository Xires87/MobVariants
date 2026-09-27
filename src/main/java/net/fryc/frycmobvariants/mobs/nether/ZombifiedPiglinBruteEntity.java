package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombifiedPiglinBruteEntity extends ZombifiedPiglin {


    public ZombifiedPiglinBruteEntity(EntityType<? extends ZombifiedPiglin> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createZombifiedPiglinBruteAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 42).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0).add(Attributes.MOVEMENT_SPEED, 0.26050000417232513).add(Attributes.ATTACK_DAMAGE, 6.0);
    }


    public boolean isAngryAt(LivingEntity entity, ServerLevel level) {
        if (!this.canAttack(entity)) {
            return false;
        }

        return super.isAngryAt(entity, level) || (this.distanceToSqr(entity) < 24 && this.hasLineOfSight(entity));
    }

/*
    public boolean shouldAngerAt(LivingEntity entity) {
        if (!this.canAttack(entity)) {
            return false;
        } else {
            return entity.getType() == EntityTypes.PLAYER && this.isAngryAtAllPlayers((ServerLevel) entity.level()) || entity.getUuid().equals(this.getAngryAt()) || (this.squaredDistanceTo(entity) < 24 && this.canSee(entity));
        }
    }

 */

    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIFIED_PIGLIN_ANGRY;
    }

    public boolean isBaby(){
        return false;
    }

    public void setBaby(boolean baby){
    }
}
