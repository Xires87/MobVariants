package net.fryc.frycmobvariants.mobs;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class ExecutionerEntity extends WitherSkeletonEntity {

    public ExecutionerEntity(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createExecutionerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23335589123124123523).add(EntityAttributes.GENERIC_MAX_HEALTH, 46).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }


    //used only in summons and spawn eggs, executioners don't spawn naturally
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
        this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        int equipChance = random.nextInt(100);
        if(equipChance > 45){
            if(equipChance <= 49) this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
            else if(equipChance <= 78) this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            else this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        }
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.24F;
    }

}
