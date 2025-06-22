package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class ExecutionerEntity extends WitherSkeletonEntity {

    public static Map<Item, Pair<Float, Float>> executionerWeapons = new HashMap<>(Map.of(Items.STONE_AXE, new Pair<>(0.0F, 1.0F)));
    public static Map<Item, Pair<Float, Float>> executionerHelmets = new HashMap<>(Map.of(Items.IRON_HELMET, new Pair<>(0.0F, 1.0F)));
    public static Map<Item, Pair<Float, Float>> executionerChestplates = new HashMap<>(Map.of(Items.IRON_CHESTPLATE, new Pair<>(0.0F, 0.50F)));
    public static Map<Item, Pair<Float, Float>> executionerLeggings = new HashMap<>(Map.of(Items.IRON_LEGGINGS, new Pair<>(0.0F, 0.25F)));
    public static Map<Item, Pair<Float, Float>> executionerBoots = new HashMap<>(Map.of(Items.IRON_BOOTS, new Pair<>(0.0F, 0.04F)));

    public ExecutionerEntity(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints += 8;
    }

    public static DefaultAttributeContainer.Builder createExecutionerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23335589123124123523).add(EntityAttributes.GENERIC_MAX_HEALTH, 46).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }


    //used only in summons and spawn eggs, executioners don't spawn naturally
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        initExecutionerEquipment(this);
    }

    public static ItemStack getExecutionerAxe(){
        return MobConvertingHelper.getRandomItemStack(ExecutionerEntity.executionerWeapons);
    }

    public static ItemStack getExecutionerHelmet(){
        return MobConvertingHelper.getRandomItemStack(ExecutionerEntity.executionerHelmets);
    }

    public static ItemStack getExecutionerChestplate(){
        return MobConvertingHelper.getRandomItemStack(ExecutionerEntity.executionerChestplates);
    }

    public static ItemStack getExecutionerLeggings(){
        return MobConvertingHelper.getRandomItemStack(ExecutionerEntity.executionerLeggings);
    }

    public static ItemStack getExecutionerBoots(){
        return MobConvertingHelper.getRandomItemStack(ExecutionerEntity.executionerBoots);
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
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch() - 0.20F);
        }

    }

    private void resetSoundDelay() {
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
    }

    public static void initExecutionerEquipment(AbstractSkeletonEntity skeleton){
        skeleton.equipStack(EquipmentSlot.MAINHAND, getExecutionerAxe());
        skeleton.equipStack(EquipmentSlot.HEAD, getExecutionerHelmet());
        skeleton.equipStack(EquipmentSlot.CHEST, getExecutionerChestplate());
        skeleton.equipStack(EquipmentSlot.LEGS, getExecutionerLeggings());
        skeleton.equipStack(EquipmentSlot.FEET, getExecutionerBoots());
    }

}
