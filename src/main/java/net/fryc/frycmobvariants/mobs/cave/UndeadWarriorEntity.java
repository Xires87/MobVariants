package net.fryc.frycmobvariants.mobs.cave;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.StatusEffectHelper;
import net.fryc.frycmobvariants.util.StringHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UndeadWarriorEntity extends SkeletonEntity {

    public java.util.Random rand = new java.util.Random();
    public int tippedArrowsAmount;
    public Pair<RegistryEntry<StatusEffect>, Pair<Integer, Integer>> tippedArrowEffect;

    public static Map<Item, Pair<Float, Float>> undeadWarriorWeapons = new HashMap<>(Map.of(Items.BOW, new Pair<>(0.0F, 0.50F), Items.STONE_SWORD, new Pair<>(0.50F, 1.0F)));

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
        this.equipStack(EquipmentSlot.MAINHAND, getUndeadWarriorWeapon());
        if(!(this.getMainHandStack().getItem() instanceof RangedWeaponItem)){
            this.tippedArrowsAmount = -1;
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0);
        this.updateAttackType();
        return entityData2;
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        PersistentProjectileEntity persistentProjectileEntity = super.createArrowProjectile(arrow, damageModifier, shotFrom);
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

    public static ItemStack getUndeadWarriorWeapon(){
        return MobConvertingHelper.getRandomItemStack(UndeadWarriorEntity.undeadWarriorWeapons);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("TippedArrowsAmount", this.tippedArrowsAmount);
        nbt.putString("TippedArrowEffect", this.tippedArrowEffect.getA().getIdAsString());
        nbt.putInt("TippedArrowDuration", this.tippedArrowEffect.getB().getA());
        nbt.putInt("TippedArrowAmplifier", this.tippedArrowEffect.getB().getB());
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
                    int lootingLevel = damageSource.getAttacker() instanceof LivingEntity entity ? EnchantmentHelper.getEquipmentLevel(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(Enchantments.LOOTING), entity) : 0;
                    if(rand.nextInt(0, 100) < this.tippedArrowsAmount * MobVariants.config.undeadWarriorAttributes.undeadWarriorsTippedArrowDropChancePerTippedArrowHeld + 1 + lootingLevel * 2){
                        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
                        int duration = this.tippedArrowEffect.getB().getA()*9;
                        int amp = this.tippedArrowEffect.getB().getB() > 0 ? this.tippedArrowEffect.getB().getB() - 1 : 0;
                        stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(
                                Optional.empty(),
                                Optional.of(this.tippedArrowEffect.getA().value().getColor()),
                                List.of(new StatusEffectInstance(this.tippedArrowEffect.getA(), duration, amp))
                        ));
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
