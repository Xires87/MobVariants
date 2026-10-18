package net.fryc.frycmobvariants.mobs;

import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.fryc.frycmobvariants.mobs.biome.ToxicSlimeEntity;
import net.fryc.frycmobvariants.mobs.nether.InfectedPiglinEntity;
import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.fryc.frycmobvariants.mobs.nether.NightmareEntity;
import net.fryc.frycmobvariants.mobs.nether.ZombifiedPiglinBruteEntity;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;


public class ModSpawnRules {


    public static void registerModSpawnRules() {
        SpawnRestriction.register(ModMobs.FORGOTTEN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.EXPLORER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.FROZEN_ZOMBIE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.BLOATED_CORPSE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.CAVE_CREEPER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.ARMORED_SPIDER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.TROPICAL_SPIDER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.EXECUTIONER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.INFECTED_PIGLIN_BRUTE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(ModMobs.SOUL_STEALER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

        SpawnRestriction.register(ModMobs.INFECTED_PIGLIN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedPiglinEntity::checkSpawnRules);
        SpawnRestriction.register(ModMobs.LAVA_SLIME, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LavaSlimeEntity::checkSpawnRules);
        SpawnRestriction.register(ModMobs.NIGHTMARE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightmareEntity::checkSpawnRules);
        SpawnRestriction.register(ModMobs.ZOMBIFIED_PIGLIN_BRUTE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglinBruteEntity::checkSpawnRules);
        SpawnRestriction.register(ModMobs.CORSAIR, SpawnLocationTypes.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CorsairEntity::checkSpawnRules);
        SpawnRestriction.register(ModMobs.TOXIC_SLIME, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ToxicSlimeEntity::checkSpawnRules);
    }
}
