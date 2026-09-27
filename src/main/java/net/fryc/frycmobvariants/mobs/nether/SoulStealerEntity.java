package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class SoulStealerEntity extends Skeleton {

    public static Map<Item, Pair<Float, Float>> soulStealerWeapons = new HashMap<>(Map.of(Items.IRON_HOE, new Pair<>(0.0F, 1.0F)));

    public SoulStealerEntity(EntityType<? extends Skeleton> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.FIRE, 0.0F);
        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.xpReward += 3;
    }


    public static AttributeSupplier.Builder createSoulStealerAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 24.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.MAX_HEALTH, 20);
    }

    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, getSoulsStealerWeapon());
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty, final EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData groupData2 = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0);
        this.reassessWeaponGoal();
        return groupData2;
    }

    protected float getBlockSpeedFactor() {
        return this.isOnSoulSpeedBlock() ? 1.47F : super.getBlockSpeedFactor();
    }

    private boolean isOnSoulSpeedBlock() {
        return this.onGround() && this.level().getBlockState(this.getOnPos(0.2F)).is(BlockTags.SOUL_SPEED_BLOCKS);
    }

    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (super.doHurtTarget(level, target)) {
            if (target instanceof LivingEntity entity) {
                if (level.getDifficulty() == Difficulty.EASY && MobVariants.config.soulStealersBaseMagicDamage > 0.0F) {
                    entity.hurtServer(level, level.damageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage);
                }
                else if (level.getDifficulty() == Difficulty.NORMAL && MobVariants.config.soulStealersBaseMagicDamage > -1.0F) {
                    entity.hurtServer(level, level.damageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage + 1.0F);
                }
                else if(level.getDifficulty() == Difficulty.HARD && MobVariants.config.soulStealersBaseMagicDamage > -3.0F) {
                    entity.hurtServer(level, level.damageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage + 3.0F);
                }
            }

            return true;
        }

        return false;
    }

    public static ItemStack getSoulsStealerWeapon(){
        return MobConvertingHelper.getRandomItemStack(SoulStealerEntity.soulStealerWeapons);
    }
}
