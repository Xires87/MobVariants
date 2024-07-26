package net.fryc.frycmobvariants.mobs;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.biome.*;
import net.fryc.frycmobvariants.mobs.cave.ArmoredSpiderEntity;
import net.fryc.frycmobvariants.mobs.cave.CaveCreeperEntity;
import net.fryc.frycmobvariants.mobs.cave.ForgottenEntity;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModMobs {

    //cave variants
    public static final EntityType<ForgottenEntity> FORGOTTEN = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "forgotten"),
            EntityType.Builder.create(ForgottenEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<UndeadWarriorEntity> UNDEAD_WARRIOR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "undead_warrior"),
            EntityType.Builder.create(UndeadWarriorEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.99F).eyeHeight(1.74F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<ArmoredSpiderEntity> ARMORED_SPIDER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "armored_spider"),
            EntityType.Builder.create(ArmoredSpiderEntity::new, SpawnGroup.MONSTER).dimensions(1.4F, 0.9F).eyeHeight(0.65F).passengerAttachments(0.765F).maxTrackingRange(8).build());

    public static final EntityType<CaveCreeperEntity> CAVE_CREEPER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "cave_creeper"),
            EntityType.Builder.create(CaveCreeperEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.7F).maxTrackingRange(8).build());

    //biome variants
    public static final EntityType<ExplorerEntity> EXPLORER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "explorer"),
            EntityType.Builder.create(ExplorerEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<BloatedCorpseEntity> BLOATED_CORPSE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "bloated_corpse"),
            EntityType.Builder.create(BloatedCorpseEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<FrozenZombieEntity> FROZEN_ZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "frozen_zombie"),
            EntityType.Builder.create(FrozenZombieEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<TropicalSpiderEntity> TROPICAL_SPIDER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "tropical_spider"),
            EntityType.Builder.create(TropicalSpiderEntity::new, SpawnGroup.MONSTER).dimensions(1.4F, 0.9F).eyeHeight(0.65F).passengerAttachments(0.765F).maxTrackingRange(8).build());

    public static final EntityType<CorsairEntity> CORSAIR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "corsair"),
            EntityType.Builder.create(CorsairEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.99F).eyeHeight(1.74F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<ToxicSlimeEntity> TOXIC_SLIME = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "toxic_slime"),
            EntityType.Builder.create(ToxicSlimeEntity::new, SpawnGroup.MONSTER).dimensions(0.52F, 0.52F).eyeHeight(0.325F).spawnBoxScale(4.0F).maxTrackingRange(10).build());


    //nether variants
    public static final EntityType<ExecutionerEntity> EXECUTIONER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "executioner"),
            EntityType.Builder.create(ExecutionerEntity::new, SpawnGroup.MONSTER).makeFireImmune().allowSpawningInside(Blocks.WITHER_ROSE).dimensions(0.83F, 2.6F).eyeHeight(2.2F).vehicleAttachment(-0.875F).maxTrackingRange(8).build());

    public static final EntityType<NightmareEntity> NIGHTMARE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "nightmare"),
            EntityType.Builder.create(NightmareEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(4.0F, 4.0F).eyeHeight(2.6F).passengerAttachments(4.0625F).vehicleAttachment(0.5F).maxTrackingRange(10).build());

    public static final EntityType<InfectedPiglinEntity> INFECTED_PIGLIN = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "infected_piglin"),
            EntityType.Builder.create(InfectedPiglinEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(0.6F, 1.95F).eyeHeight(1.79F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<InfectedPiglinBruteEntity> INFECTED_PIGLIN_BRUTE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "infected_piglin_brute"),
            EntityType.Builder.create(InfectedPiglinBruteEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).makeFireImmune().eyeHeight(1.79F).passengerAttachments(2.0125F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<ZombifiedPiglinBruteEntity> ZOMBIFIED_PIGLIN_BRUTE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "zombified_piglin_brute"),
            EntityType.Builder.create(ZombifiedPiglinBruteEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(0.6F, 1.95F).eyeHeight(1.79F).passengerAttachments(2.0F).vehicleAttachment(-0.7F).maxTrackingRange(8).build());

    public static final EntityType<SoulStealerEntity> SOUL_STEALER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "soul_stealer"),
            EntityType.Builder.create(SoulStealerEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.99F).eyeHeight(1.74F).vehicleAttachment(-0.7F).maxTrackingRange(8).makeFireImmune().build());

    public static final EntityType<LavaSlimeEntity> LAVA_SLIME = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MobVariants.MOD_ID, "lava_slime"),
            EntityType.Builder.create(LavaSlimeEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(0.52F, 0.52F).eyeHeight(0.325F).spawnBoxScale(4.0F).maxTrackingRange(8).build());

    public static void registerModMobs(){
        //cave variants
        FabricDefaultAttributeRegistry.register(FORGOTTEN, ForgottenEntity.createForgottenAttributes());
        FabricDefaultAttributeRegistry.register(UNDEAD_WARRIOR, UndeadWarriorEntity.createUndeadWarriorAttributes());
        FabricDefaultAttributeRegistry.register(ARMORED_SPIDER, ArmoredSpiderEntity.createArmoredSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_CREEPER, CaveCreeperEntity.createCreeperAttributes());

        //biome variants
        FabricDefaultAttributeRegistry.register(EXPLORER, ExplorerEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(BLOATED_CORPSE, BloatedCorpseEntity.createBloatedCorpseAttributes());
        FabricDefaultAttributeRegistry.register(FROZEN_ZOMBIE, FrozenZombieEntity.createFrozenZombieAttributes());
        FabricDefaultAttributeRegistry.register(TROPICAL_SPIDER, TropicalSpiderEntity.createTropicalSpiderAttributes());
        FabricDefaultAttributeRegistry.register(CORSAIR, CorsairEntity.createCorsairAttributes());
        FabricDefaultAttributeRegistry.register(TOXIC_SLIME, HostileEntity.createHostileAttributes());

        //nether variants
        FabricDefaultAttributeRegistry.register(EXECUTIONER, ExecutionerEntity.createExecutionerAttributes());
        FabricDefaultAttributeRegistry.register(NIGHTMARE, NightmareEntity.createNightmareAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN, InfectedPiglinEntity.createPiglinAttributes());
        FabricDefaultAttributeRegistry.register(INFECTED_PIGLIN_BRUTE, InfectedPiglinBruteEntity.createPiglinBruteAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIFIED_PIGLIN_BRUTE, ZombifiedPiglinBruteEntity.createZombifiedPiglinBruteAttributes());
        FabricDefaultAttributeRegistry.register(SOUL_STEALER, SoulStealerEntity.createSoulStealerAttributes());
        FabricDefaultAttributeRegistry.register(LAVA_SLIME, LavaSlimeEntity.createLavaSlimeAttributes());

    }
}
