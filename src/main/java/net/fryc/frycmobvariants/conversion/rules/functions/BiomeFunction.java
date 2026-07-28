package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.tags.ModBiomeTags;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.biome.Biome;

import java.util.Random;
import java.util.function.BiPredicate;

public record BiomeFunction(TagKey<Biome> biomes) implements BiPredicate<MobEntity, Random> {

    public static final String ID = "biome";

    @Override
    public boolean test(MobEntity mob, Random random) {
        return mob.getWorld().getBiome(mob.getBlockPos()).isIn(this.biomes());
    }

    public static BiomeFunction fromJson(JsonObject jsonObject) {
        return new BiomeFunction(ModBiomeTags.getTag(Identifier.of(JsonHelper.getString(jsonObject, "tag"))));
    }
}
