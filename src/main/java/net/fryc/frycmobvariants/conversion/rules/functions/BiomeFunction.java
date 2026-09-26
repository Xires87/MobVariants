package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.tags.ModBiomeTags;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;

import java.util.Random;
import java.util.function.BiPredicate;

public record BiomeFunction(TagKey<Biome> biomes) implements BiPredicate<Mob, Random> {

    public static final String ID = "biome";

    @Override
    public boolean test(Mob mob, Random random) {
        return mob.level().getBiome(mob.blockPosition()).is(this.biomes());
    }

    public static BiomeFunction fromJson(JsonObject jsonObject) {
        return new BiomeFunction(ModBiomeTags.getTag(Identifier.parse(GsonHelper.getAsString(jsonObject, "tag"))));
    }
}
