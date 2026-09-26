package net.fryc.frycmobvariants.mobs.cave;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.StatusEffectHelper;
import net.fryc.frycmobvariants.util.StringHelper;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UndeadWarriorEntity extends Skeleton {

    public java.util.Random rand = new java.util.Random();
    public int tippedArrowsAmount;
    public Pair<Holder<MobEffect>, Pair<Integer, Integer>> tippedArrowEffect;

    public static Map<Item, Pair<Float, Float>> undeadWarriorWeapons = new HashMap<>(Map.of(Items.BOW, new Pair<>(0.0F, 0.50F), Items.STONE_SWORD, new Pair<>(0.50F, 1.0F)));

    public UndeadWarriorEntity(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        if(!level.isClientSide()){
            int minTippedArrows = MobVariants.config.undeadWarriorAttributes.undeadWarriorsMinTippedArrowsCount;
            this.tippedArrowsAmount = rand.nextInt(
                    minTippedArrows,
                    Math.max(MobVariants.config.undeadWarriorAttributes.undeadWarriorsMaxTippedArrowsCount + 1, minTippedArrows + 1)
            );
            this.tippedArrowEffect = StatusEffectHelper.pickRandomStatusEffect(rand);
        }
        else {
            this.tippedArrowsAmount = 1;
            this.tippedArrowEffect = new Pair<>(MobEffects.WEAKNESS, new Pair<>(400, 1));
        }

        this.xpReward += 1;
    }

    public static AttributeSupplier.Builder createUndeadWarriorAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 19.0).add(Attributes.MOVEMENT_SPEED, 0.2505).add(Attributes.MAX_HEALTH, 22).add(Attributes.KNOCKBACK_RESISTANCE, 0.2f);
    }

    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        if(this.getMainHandItem().isEmpty()) {
            super.populateDefaultEquipmentSlots(random, localDifficulty);
        }

        if(!MobVariants.config.undeadWarriorAttributes.alwaysKeepEnchantedBow || !this.getMainHandItem().isEnchanted()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, getUndeadWarriorWeapon());
        }

        if(!(this.getMainHandItem().getItem() instanceof ProjectileWeaponItem)){
            this.tippedArrowsAmount = -1;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty, final EntitySpawnReason spawnReason, @org.jspecify.annotations.Nullable SpawnGroupData groupData) {
        SpawnGroupData groupData2 = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0);
        this.reassessWeaponGoal();
        return groupData2;
    }
// TODO dokonczyc warriora
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
