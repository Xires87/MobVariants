package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class ExecutionerEntity extends WitherSkeleton {

    public static Map<Item, Pair<Float, Float>> executionerWeapons = new HashMap<>(Map.of(Items.STONE_AXE, new Pair<>(0.0F, 1.0F)));
    public static Map<Item, Pair<Float, Float>> executionerHelmets = new HashMap<>(Map.of(Items.IRON_HELMET, new Pair<>(0.0F, 1.0F)));
    public static Map<Item, Pair<Float, Float>> executionerChestplates = new HashMap<>(Map.of(Items.IRON_CHESTPLATE, new Pair<>(0.0F, 0.50F)));
    public static Map<Item, Pair<Float, Float>> executionerLeggings = new HashMap<>(Map.of(Items.IRON_LEGGINGS, new Pair<>(0.0F, 0.25F)));
    public static Map<Item, Pair<Float, Float>> executionerBoots = new HashMap<>(Map.of(Items.IRON_BOOTS, new Pair<>(0.0F, 0.04F)));

    public ExecutionerEntity(EntityType<? extends WitherSkeleton> type, Level level) {
        super(type, level);
        this.xpReward += 8;
    }


    public static AttributeSupplier.Builder createExecutionerAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.23335589123124123523).add(Attributes.MAX_HEALTH, 46).add(Attributes.KNOCKBACK_RESISTANCE, 0.6).add(Attributes.ATTACK_DAMAGE, 5.0);
    }


    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
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
        this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch() - 0.25F);
    }

    protected void playHurtSound(DamageSource source) {
        this.resetSoundDelay();
        this.playSound(this.getHurtSound(source), this.getSoundVolume(), this.getVoicePitch() - 0.20F);
    }

    private void resetSoundDelay() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    public static void initExecutionerEquipment(AbstractSkeleton skeleton){
        skeleton.setItemSlot(EquipmentSlot.MAINHAND, getExecutionerAxe());
        skeleton.setItemSlot(EquipmentSlot.HEAD, getExecutionerHelmet());
        skeleton.setItemSlot(EquipmentSlot.CHEST, getExecutionerChestplate());
        skeleton.setItemSlot(EquipmentSlot.LEGS, getExecutionerLeggings());
        skeleton.setItemSlot(EquipmentSlot.FEET, getExecutionerBoots());
    }

}
