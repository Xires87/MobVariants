package net.fryc.frycmobvariants.mobs;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fryc.frycmobvariants.mobs.biome.*;
import net.fryc.frycmobvariants.mobs.cave.ArmoredSpiderEntity;
import net.fryc.frycmobvariants.mobs.cave.CaveCreeperEntity;
import net.fryc.frycmobvariants.mobs.cave.ForgottenEntity;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;

public class ModMobs {
    
    //cave variants
    public static final EntityType<ForgottenEntity> FORGOTTEN = register(
            ModMobIds.FORGOTTEN,
            EntityType.Builder.of(ForgottenEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(new float[]{2.0125F})
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<UndeadWarriorEntity> UNDEAD_WARRIOR = register(
            ModMobIds.UNDEAD_WARRIOR,
            EntityType.Builder.of(UndeadWarriorEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<ArmoredSpiderEntity> ARMORED_SPIDER = register(
            ModMobIds.ARMORED_SPIDER,
            EntityType.Builder.of(ArmoredSpiderEntity::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .ridingOffset(0.765F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<CaveCreeperEntity> CAVE_CREEPER = register(
            ModMobIds.CAVE_CREEPER,
            EntityType.Builder.of(CaveCreeperEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    //biome variants
    public static final EntityType<ExplorerEntity> EXPLORER = register(
            ModMobIds.EXPLORER,
            EntityType.Builder.of(ExplorerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<BloatedCorpseEntity> BLOATED_CORPSE = register(
            ModMobIds.BLOATED_CORPSE,
            EntityType.Builder.of(BloatedCorpseEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<FrozenZombieEntity> FROZEN_ZOMBIE = register(
            ModMobIds.FROZEN_ZOMBIE,
            EntityType.Builder.of(FrozenZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<TropicalSpiderEntity> TROPICAL_SPIDER = register(
            ModMobIds.TROPICAL_SPIDER,
            EntityType.Builder.of(TropicalSpiderEntity::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .ridingOffset(0.765F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<CorsairEntity> CORSAIR = register(
            ModMobIds.CORSAIR,
            EntityType.Builder.of(CorsairEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<ToxicSlimeEntity> TOXIC_SLIME = register(
            ModMobIds.TOXIC_SLIME,
            EntityType.Builder.of(ToxicSlimeEntity::new, MobCategory.MONSTER)
                    .sized(0.52F, 0.52F)
                    .eyeHeight(0.325F)
                    .spawnDimensionsScale(4.0F)
                    .clientTrackingRange(10)
                    .notInPeaceful()
    );


    //nether variants
    public static final EntityType<ExecutionerEntity> EXECUTIONER = register(
            ModMobIds.EXECUTIONER,
            EntityType.Builder.of(ExecutionerEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .immuneTo(BlockTags.WITHER_SKELETON_IMMUNE_TO)
                    .sized(0.87F, 2.7F)
                    .eyeHeight(2.28F)
                    .ridingOffset(-0.875F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<NightmareEntity> NIGHTMARE = register(
            ModMobIds.NIGHTMARE,
            EntityType.Builder.of(NightmareEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(4.0F, 4.0F)
                    .eyeHeight(2.6F)
                    .passengerAttachments(4.0625F)
                    .ridingOffset(0.5F)
                    .clientTrackingRange(10)
                    .notInPeaceful()
    );

    public static final EntityType<InfectedPiglinEntity> INFECTED_PIGLIN = register(
            ModMobIds.INFECTED_PIGLIN,
            EntityType.Builder.of(InfectedPiglinEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.79F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<InfectedPiglinBruteEntity> INFECTED_PIGLIN_BRUTE = register(
            ModMobIds.INFECTED_PIGLIN_BRUTE,
            EntityType.Builder.of(InfectedPiglinBruteEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .eyeHeight(1.79F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<ZombifiedPiglinBruteEntity> ZOMBIFIED_PIGLIN_BRUTE = register(
            ModMobIds.ZOMBIFIED_PIGLIN_BRUTE,
            EntityType.Builder.of(ZombifiedPiglinBruteEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.79F)
                    .passengerAttachments(2.0F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static final EntityType<SoulStealerEntity> SOUL_STEALER = register(
            ModMobIds.SOUL_STEALER,
            EntityType.Builder.of(SoulStealerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
                    .eyeHeight(1.74F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .notInPeaceful()
    );

    public static final EntityType<LavaSlimeEntity> LAVA_SLIME = register(
            ModMobIds.LAVA_SLIME,
            EntityType.Builder.of(LavaSlimeEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.52F, 0.52F)
                    .eyeHeight(0.325F)
                    .spawnDimensionsScale(4.0F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    public static void registerModMobs(){
        //cave variants
        FabricDefaultAttributeRegistry.register(FORGOTTEN, ForgottenEntity.createForgottenAttributes());
        FabricDefaultAttributeRegistry.register(UNDEAD_WARRIOR, UndeadWarriorEntity.createUndeadWarriorAttributes());
        FabricDefaultAttributeRegistry.register(ARMORED_SPIDER, ArmoredSpiderEntity.createArmoredSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_CREEPER, CaveCreeperEntity.createAttributes());

        //biome variants
        FabricDefaultAttributeRegistry.register(EXPLORER, ExplorerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BLOATED_CORPSE, BloatedCorpseEntity.createBloatedCorpseAttributes());
        FabricDefaultAttributeRegistry.register(FROZEN_ZOMBIE, FrozenZombieEntity.createFrozenZombieAttributes());
        FabricDefaultAttributeRegistry.register(TROPICAL_SPIDER, TropicalSpiderEntity.createTropicalSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CORSAIR, CorsairEntity.createCorsairAttributes());
        FabricDefaultAttributeRegistry.register(TOXIC_SLIME, Monster.createMonsterAttributes());

        //nether variants
        FabricDefaultAttributeRegistry.register(EXECUTIONER, ExecutionerEntity.createExecutionerAttributes());
        FabricDefaultAttributeRegistry.register(NIGHTMARE, NightmareEntity.createNightmareAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN, InfectedPiglinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN_BRUTE, InfectedPiglinBruteEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIFIED_PIGLIN_BRUTE, ZombifiedPiglinBruteEntity.createZombifiedPiglinBruteAttributes());
        FabricDefaultAttributeRegistry.register(SOUL_STEALER, SoulStealerEntity.createSoulStealerAttributes());
        FabricDefaultAttributeRegistry.register(LAVA_SLIME, LavaSlimeEntity.createLavaSlimeAttributes());

    }

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }
}
