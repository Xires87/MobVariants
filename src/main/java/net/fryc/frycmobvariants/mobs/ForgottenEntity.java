package net.fryc.frycmobvariants.mobs;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ForgottenEntity extends ZombieEntity {
    public ForgottenEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createForgottenAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23200000417232513).add(EntityAttributes.GENERIC_MAX_HEALTH, 26).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0).add(EntityAttributes.GENERIC_ARMOR, 4.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }


    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 120 * (int)f, 1), this);
        }

        return bl;
    }

    //forgotten can't spawn as baby
    public boolean isBaby() {
        return false;
    }


    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }
}
