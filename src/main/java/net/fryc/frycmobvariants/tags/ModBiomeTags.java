package net.fryc.frycmobvariants.tags;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> EXPLORER_SPAWN_BIOMES = ModBiomeTags.register("explorer_spawn_biomes");

    public static final TagKey<Biome> SNOWY_BIOMES = ModBiomeTags.register("snowy_biomes");


    private ModBiomeTags(){
    }
    private static TagKey<Biome> register(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(MobVariants.MOD_ID, id));
    }
}
