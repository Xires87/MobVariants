package net.fryc.frycmobvariants.mobs.nether;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class SoulStealerEntity extends SkeletonEntity {

    public static Map<Item, Pair<Float, Float>> soulStealerWeapons = new HashMap<>(Map.of(Items.IRON_HOE, new Pair<>(0.0F, 1.0F)));

    public SoulStealerEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.experiencePoints += 3;
    }

    public static DefaultAttributeContainer.Builder createSoulStealerAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_MAX_HEALTH, 20);
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, getSoulsStealerWeapon());
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0);
        this.updateAttackType();
        return entityData2;
    }

    protected float getVelocityMultiplier() {
        return this.isOnSoulSpeedBlock() ? 1.47F : super.getVelocityMultiplier();
    }

    private boolean isOnSoulSpeedBlock() {
        return this.isOnGround() && this.getWorld().getBlockState(this.getPosWithYOffset(0.2F)).isIn(BlockTags.SOUL_SPEED_BLOCKS);
    }

    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity entity) {
                if (this.getWorld().getDifficulty() == Difficulty.EASY && MobVariants.config.soulStealersBaseMagicDamage > 0.0F) {
                    entity.damage(this.getWorld().getDamageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage);
                }
                else if (this.getWorld().getDifficulty() == Difficulty.NORMAL && MobVariants.config.soulStealersBaseMagicDamage > -1.0F) {
                    entity.damage(this.getWorld().getDamageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage + 1.0F);
                }
                else if(this.getWorld().getDifficulty() == Difficulty.HARD && MobVariants.config.soulStealersBaseMagicDamage > -3.0F) {
                    entity.damage(this.getWorld().getDamageSources().indirectMagic(this, this), MobVariants.config.soulStealersBaseMagicDamage + 3.0F);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static ItemStack getSoulsStealerWeapon(){
        return MobConvertingHelper.getRandomItemStack(SoulStealerEntity.soulStealerWeapons);
    }




}
