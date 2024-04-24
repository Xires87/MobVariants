package net.fryc.frycmobvariants.mobs.cave;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UndeadWarriorEntity extends SkeletonEntity {

    public UndeadWarriorEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints += 1;
    }

    public static DefaultAttributeContainer.Builder createUndeadWarriorAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2505).add(EntityAttributes.GENERIC_MAX_HEALTH, 22).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2f);
    }

    //used only in summons and spawn eggs, udead warriors don't spawn naturally
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(random.nextInt(100) > MobVariants.config.undeadWarriorAttributes.undeadWarriorSpawnWithBowChance) this.equipStack(EquipmentSlot.MAINHAND, getUndeadWarriorSword());
        else this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0);
        this.updateAttackType();
        return entityData2;
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier);
        if (persistentProjectileEntity instanceof ArrowEntity) {
            int duration = MobVariants.config.undeadWarriorAttributes.undeadWarriorsEffectDuration > 0 ? MobVariants.config.undeadWarriorAttributes.undeadWarriorsEffectDuration : 1;
            int amplifier = MobVariants.config.undeadWarriorAttributes.undeadWarriorsEffectAmplifier > 0 ? MobVariants.config.undeadWarriorAttributes.undeadWarriorsEffectAmplifier - 1 : 0;
            ((ArrowEntity)persistentProjectileEntity).addEffect(new StatusEffectInstance(MobVariants.config.undeadWarriorAttributes.undeadWarriorsArrowEffect.getStatusEffect(), duration, amplifier));
        }

        return persistentProjectileEntity;
    }

    public static ItemStack getUndeadWarriorSword(){
        return new ItemStack(Items.STONE_SWORD);
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch() - 0.25F);
        }

    }

    protected void playHurtSound(DamageSource source) {
        this.resetSoundDelay();
        SoundEvent soundEvent = this.getHurtSound(source);
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch() - 0.15F);
        }

    }

    private void resetSoundDelay() {
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
    }
}
