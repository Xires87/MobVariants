package net.fryc.frycmobvariants.tags;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> UNDERGROUND_BIOMES = ModBiomeTags.register("underground_biomes");
    public static final TagKey<Biome> EXPLORER_SPAWN_BIOMES = ModBiomeTags.register("explorer_spawn_biomes");
    public static final TagKey<Biome> TROPICAL_SPIDER_SPAWN_BIOMES = ModBiomeTags.register("tropical_spider_spawn_biomes");
    public static final TagKey<Biome> TOXIC_SLIME_SPAWN_BIOMES = ModBiomeTags.register("toxic_slime_spawn_biomes");

    public static final TagKey<Biome> FROZEN_ZOMBIE_SPAWN_BIOMES = ModBiomeTags.register("frozen_zombie_spawn_biomes");
    public static final TagKey<Biome> BLOATED_CORPSE_SPAWN_BIOMES = ModBiomeTags.register("bloated_corpse_spawn_biomes");
    public static final TagKey<Biome> CORSAIR_SPAWN_BIOMES = ModBiomeTags.register("corsair_spawn_biomes");


    private ModBiomeTags(){
    }
    private static TagKey<Biome> register(String id) {
        return TagKey.of(RegistryKeys.BIOME, Identifier.of(MobVariants.MOD_ID, id));
    }
}
