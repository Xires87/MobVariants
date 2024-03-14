package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class ZombifiedPiglinBruteEntity extends ZombifiedPiglinEntity {

    public ZombifiedPiglinBruteEntity(EntityType<? extends ZombifiedPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createZombifiedPiglinBruteAttributes() {
        return ZombieEntity.createZombieAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 42).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26050000417232513).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
    }


    public boolean shouldAngerAt(LivingEntity entity) {
        if (!this.canTarget(entity)) {
            return false;
        } else {
            return entity.getType() == EntityType.PLAYER && this.isUniversallyAngry(entity.getWorld()) || entity.getUuid().equals(this.getAngryAt()) || (this.squaredDistanceTo(entity) < 24 && this.canSee(entity));
        }
    }


    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY;
    }

    public boolean isBaby(){
        return false;
    }

    public void setBaby(boolean baby){
    }




}
