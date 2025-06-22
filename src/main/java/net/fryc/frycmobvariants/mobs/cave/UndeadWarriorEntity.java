package net.fryc.frycmobvariants.mobs.cave;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.StatusEffectHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.List;

public class UndeadWarriorEntity extends SkeletonEntity {

    public java.util.Random rand = new java.util.Random();
    public int tippedArrowsAmount;
    public Pair<StatusEffect, Pair<Integer, Integer>> tippedArrowEffect;

    public UndeadWarriorEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.tippedArrowsAmount = rand.nextInt(
                MobVariants.config.undeadWarriorAttributes.undeadWarriorsMinTippedArrowsCount,
                MobVariants.config.undeadWarriorAttributes.undeadWarriorsMaxTippedArrowsCount + 1
        );
        this.tippedArrowEffect = StatusEffectHelper.pickRandomStatusEffect(rand);
        this.experiencePoints += 1;
    }

    public static DefaultAttributeContainer.Builder createUndeadWarriorAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2505).add(EntityAttributes.GENERIC_MAX_HEALTH, 22).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2f);
    }

    //used only in summons and spawn eggs, undead warriors don't spawn naturally
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(random.nextInt(100) > MobVariants.config.undeadWarriorAttributes.undeadWarriorSpawnWithBowChance) {
            this.equipStack(EquipmentSlot.MAINHAND, getUndeadWarriorSword());
            this.tippedArrowsAmount = -1;
        }
        else this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0);
        this.updateAttackType();
        return entityData2;
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier);
        if(this.tippedArrowsAmount > 0){
            if (persistentProjectileEntity instanceof ArrowEntity) {
                int duration = this.tippedArrowEffect.getB().getA() > 0 ? this.tippedArrowEffect.getB().getA() : 1;
                int amplifier = this.tippedArrowEffect.getB().getB() > 0 ? this.tippedArrowEffect.getB().getB() - 1 : 0;
                ((ArrowEntity)persistentProjectileEntity).addEffect(new StatusEffectInstance(this.tippedArrowEffect.getA(), duration, amplifier));
            }
            this.tippedArrowsAmount--;
        }
        return persistentProjectileEntity;
    }

    public static ItemStack getUndeadWarriorSword(){
        return new ItemStack(Items.STONE_SWORD);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Identifier id = Registries.STATUS_EFFECT.getId(tippedArrowEffect.getA());
        if(id != null){
            nbt.putInt("TippedArrowsAmount", this.tippedArrowsAmount);
            nbt.putString("TippedArrowEffect", id.toString());
            nbt.putInt("TippedArrowDuration", this.tippedArrowEffect.getB().getA());
            nbt.putInt("TippedArrowAmplifier", this.tippedArrowEffect.getB().getB());
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TippedArrowsAmount")) {
            this.tippedArrowsAmount = nbt.getInt("TippedArrowsAmount");
        }
        if(nbt.contains("TippedArrowEffect") && nbt.contains("TippedArrowDuration") && nbt.contains("TippedArrowAmplifier")){
            this.tippedArrowEffect = new Pair<>(StringHelper.getStatusEffectFromString(nbt.getString("TippedArrowEffect")), new Pair<>(nbt.getInt("TippedArrowDuration"), nbt.getInt("TippedArrowAmplifier")));
        }
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

    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        if(!this.getWorld().isClient()){
            if(causedByPlayer){
                if(this.tippedArrowsAmount > -1){
                    int lootingLevel = damageSource.getAttacker() instanceof LivingEntity entity ? EnchantmentHelper.getEquipmentLevel(Enchantments.LOOTING, entity) : 0;
                    if(rand.nextInt(0, 100) < this.tippedArrowsAmount * MobVariants.config.undeadWarriorAttributes.undeadWarriorsTippedArrowDropChancePerTippedArrowHeld + 1 + lootingLevel * 2){
                        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
                        int duration = this.tippedArrowEffect.getB().getA()*9;
                        int amp = this.tippedArrowEffect.getB().getB() > 0 ? this.tippedArrowEffect.getB().getB() - 1 : 0;
                        PotionUtil.setCustomPotionEffects(stack, List.of(new StatusEffectInstance(this.tippedArrowEffect.getA(), duration, amp)));
                        this.dropStack(stack);
                    }
                    int arrowCount = rand.nextInt(0, 3 + lootingLevel);
                    if(arrowCount > 0){
                        this.dropStack(new ItemStack(Items.ARROW, arrowCount));
                    }
                }
            }
        }
        super.dropLoot(damageSource, causedByPlayer);
    }
}
