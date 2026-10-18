package net.fryc.frycmobvariants.mobs;

import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.fryc.frycmobvariants.mobs.biome.ToxicSlimeEntity;
import net.fryc.frycmobvariants.mobs.nether.InfectedPiglinEntity;
import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.fryc.frycmobvariants.mobs.nether.NightmareEntity;
import net.fryc.frycmobvariants.mobs.nether.ZombifiedPiglinBruteEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModSpawnRules {


    public static void registerModSpawnRules() {
        SpawnPlacements.register(ModMobs.FORGOTTEN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.EXPLORER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.FROZEN_ZOMBIE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.BLOATED_CORPSE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.CAVE_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.ARMORED_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.TROPICAL_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.EXECUTIONER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.INFECTED_PIGLIN_BRUTE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModMobs.SOUL_STEALER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);

        SpawnPlacements.register(ModMobs.INFECTED_PIGLIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, InfectedPiglinEntity::checkInfectedPiglinSpawnRules);
        SpawnPlacements.register(ModMobs.LAVA_SLIME, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LavaSlimeEntity::checkLavaSlimeSpawnRules);
        SpawnPlacements.register(ModMobs.NIGHTMARE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NightmareEntity::checkNightmareSpawnRules);
        SpawnPlacements.register(ModMobs.ZOMBIFIED_PIGLIN_BRUTE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglinBruteEntity::checkZombifiedPiglinBruteSpawnRules);
        SpawnPlacements.register(ModMobs.CORSAIR, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorsairEntity::checkCorsairSpawnRules);
        SpawnPlacements.register(ModMobs.TOXIC_SLIME, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ToxicSlimeEntity::checkToxicSlimeSpawnRules);

    }
}
