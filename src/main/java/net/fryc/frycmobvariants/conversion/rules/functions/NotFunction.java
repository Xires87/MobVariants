package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;

import java.util.Random;
import java.util.function.BiPredicate;

public record NotFunction(BiPredicate<Mob, Random> function) implements BiPredicate<Mob, Random> {

    public static final String ID = "NOT";

    @Override
    public boolean test(Mob mob, Random random) {
        return !this.function().test(mob, random);
    }

    public static NotFunction fromJson(JsonObject jsonObject) {
        JsonObject object = GsonHelper.getAsJsonObject(jsonObject, "function");

        return new NotFunction(FrycJsonHelper.getMobConversionFunction(object));
    }
}
