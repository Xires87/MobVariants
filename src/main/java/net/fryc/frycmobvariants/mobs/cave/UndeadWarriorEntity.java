package net.fryc.frycmobvariants.mobs.cave;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.StatusEffectHelper;
import net.fryc.frycmobvariants.util.StringHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UndeadWarriorEntity extends Skeleton {

    private static final int LOOTING_ENCHANTMENT_MULTIPLIER = 2;
    private static final int DROPPED_TIPPED_ARROW_DURATION_MULTIPLIER = 9;

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


    protected AbstractArrow getArrow(ItemStack projectile, float power, @Nullable ItemStack firingWeapon) {
        AbstractArrow arrow = super.getArrow(projectile, power, firingWeapon);
        if(this.tippedArrowsAmount > 0){
            if (arrow instanceof Arrow) {
                int duration = this.tippedArrowEffect.getB().getA() > 0 ? this.tippedArrowEffect.getB().getA() : 1;
                int amplifier = this.tippedArrowEffect.getB().getB() > 0 ? this.tippedArrowEffect.getB().getB() - 1 : 0;
                ((Arrow) arrow).addEffect(new MobEffectInstance(this.tippedArrowEffect.getA(), duration, amplifier));
            }
            this.tippedArrowsAmount--;
        }

        return arrow;
    }

    public static ItemStack getUndeadWarriorWeapon(){
        return MobConvertingHelper.getRandomItemStack(UndeadWarriorEntity.undeadWarriorWeapons);
    }

    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("TippedArrowsAmount", this.tippedArrowsAmount);
        output.putString("TippedArrowEffect", BuiltInRegistries.MOB_EFFECT.getKey(this.tippedArrowEffect.getA().value()).toString());
        output.putInt("TippedArrowDuration", this.tippedArrowEffect.getB().getA());
        output.putInt("TippedArrowAmplifier", this.tippedArrowEffect.getB().getB());
    }

    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.tippedArrowsAmount = input.getIntOr("TippedArrowsAmount", -1);
        this.tippedArrowEffect = new Pair<>(
                StringHelper.getStatusEffectFromString(input.getStringOr("TippedArrowEffect", "minecraft:weakness")),
                new Pair<>(input.getIntOr("TippedArrowDuration", 100), input.getIntOr("TippedArrowAmplifier", 0))
        );
    }


    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch() - 0.25F);
    }

    protected void playHurtSound(DamageSource source) {
        this.resetSoundDelay();
        this.playSound(this.getHurtSound(source), this.getSoundVolume(), this.getVoicePitch() - 0.15F);

    }

    private void resetSoundDelay() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer) {
        if(killedByPlayer){
            if(this.tippedArrowsAmount > -1){
                Optional<Holder.Reference<Enchantment>> optional = level.registryAccess().get(Enchantments.LOOTING);
                int lootingLevel = source.getEntity() instanceof LivingEntity entity && optional.isPresent() ? EnchantmentHelper.getEnchantmentLevel(optional.get(), entity) : 0;
                if(rand.nextInt(0, 100) < this.tippedArrowsAmount * MobVariants.config.undeadWarriorAttributes.undeadWarriorsTippedArrowDropChancePerTippedArrowHeld + 1 + lootingLevel * LOOTING_ENCHANTMENT_MULTIPLIER){
                    ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
                    int duration = this.tippedArrowEffect.getB().getA() * DROPPED_TIPPED_ARROW_DURATION_MULTIPLIER;
                    int amp = this.tippedArrowEffect.getB().getB() > 0 ? this.tippedArrowEffect.getB().getB() - 1 : 0;
                    stack.set(DataComponents.POTION_CONTENTS, new PotionContents(
                            Optional.empty(),
                            Optional.of(this.tippedArrowEffect.getA().value().getColor()),
                            List.of(new MobEffectInstance(this.tippedArrowEffect.getA(), duration, amp)),
                            Optional.empty()
                    ));
                    this.spawnAtLocation(level, stack);
                }
                int arrowCount = rand.nextInt(0, 3 + lootingLevel);
                if(arrowCount > 0){
                    this.spawnAtLocation(level, new ItemStack(Items.ARROW, arrowCount));
                }
            }
        }

        super.dropCustomDeathLoot(level, source, killedByPlayer);
    }
}
