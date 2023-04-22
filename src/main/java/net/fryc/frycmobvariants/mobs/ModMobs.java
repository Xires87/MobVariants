package net.fryc.frycmobvariants.mobs;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.fryc.frycmobvariants.mobs.biome.ExplorerEntity;
import net.fryc.frycmobvariants.mobs.biome.FrozenZombieEntity;
import net.fryc.frycmobvariants.mobs.biome.TropicalSpiderEntity;
import net.fryc.frycmobvariants.mobs.cave.ArmoredSpiderEntity;
import net.fryc.frycmobvariants.mobs.cave.CaveCreeperEntity;
import net.fryc.frycmobvariants.mobs.cave.ForgottenEntity;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModMobs {

    //cave variants
    public static final EntityType<ForgottenEntity> FORGOTTEN = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "forgotten"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ForgottenEntity::new).dimensions(EntityDimensions.changing(0.6F, 1.95F)).build());

    public static final EntityType<UndeadWarriorEntity> UNDEAD_WARRIOR = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "undead_warrior"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, UndeadWarriorEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).build());

    public static final EntityType<ArmoredSpiderEntity> ARMORED_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "armored_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmoredSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4F, 0.9F)).build());

    public static final EntityType<CaveCreeperEntity> CAVE_CREEPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "cave_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CaveCreeperEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.73F)).build());

    //biome variants
    public static final EntityType<ExplorerEntity> EXPLORER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "explorer"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ExplorerEntity::new).dimensions(EntityDimensions.changing(0.6F, 1.95F)).build());

    public static final EntityType<FrozenZombieEntity> FROZEN_ZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "frozen_zombie"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FrozenZombieEntity::new).dimensions(EntityDimensions.changing(0.6F, 1.95F)).build());

    public static final EntityType<TropicalSpiderEntity> TROPICAL_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "tropical_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropicalSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4F, 0.9F)).build());

    public static final EntityType<CorsairEntity> CORSAIR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "corsair"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CorsairEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).build());


    //nether variants
    public static final EntityType<ExecutionerEntity> EXECUTIONER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "executioner"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ExecutionerEntity::new).dimensions(EntityDimensions.fixed(0.73F, 2.6F)).fireImmune().build());

    public static final EntityType<NightmareEntity> NIGHTMARE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "nightmare"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NightmareEntity::new).dimensions(EntityDimensions.fixed(4.0F, 4.0F)).fireImmune().build());

    public static final EntityType<InfectedPiglinEntity> INFECTED_PIGLIN = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "infected_piglin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InfectedPiglinEntity::new).dimensions(EntityDimensions.changing(0.6F, 1.95F)).fireImmune().build());

    public static final EntityType<InfectedPiglinBruteEntity> INFECTED_PIGLIN_BRUTE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "infected_piglin_brute"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InfectedPiglinBruteEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).fireImmune().build());

    public static final EntityType<SoulStealerEntity> SOUL_STEALER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MobVariants.MOD_ID, "soul_stealer"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SoulStealerEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.99F)).fireImmune().build());

    public static void registerModMobs(){
        //cave variants
        FabricDefaultAttributeRegistry.register(FORGOTTEN, ForgottenEntity.createForgottenAttributes());
        FabricDefaultAttributeRegistry.register(UNDEAD_WARRIOR, UndeadWarriorEntity.createUndeadWarriorAttributes());
        FabricDefaultAttributeRegistry.register(ARMORED_SPIDER, ArmoredSpiderEntity.createArmoredSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_CREEPER, CaveCreeperEntity.createCreeperAttributes());

        //biome variants
        FabricDefaultAttributeRegistry.register(EXPLORER, ExplorerEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(FROZEN_ZOMBIE, FrozenZombieEntity.createFrozenZombieAttributes());
        FabricDefaultAttributeRegistry.register(TROPICAL_SPIDER, TropicalSpiderEntity.createTropicalSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CORSAIR, CorsairEntity.createCorsairAttributes());

        //nether variants
        FabricDefaultAttributeRegistry.register(EXECUTIONER, ExecutionerEntity.createExecutionerAttributes());
        FabricDefaultAttributeRegistry.register(NIGHTMARE, NightmareEntity.createNightmareAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN, InfectedPiglinEntity.createPiglinAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN_BRUTE, InfectedPiglinBruteEntity.createPiglinBruteAttributes());
        FabricDefaultAttributeRegistry.register(SOUL_STEALER, SoulStealerEntity.createSoulStealerAttributes());

    }
}
