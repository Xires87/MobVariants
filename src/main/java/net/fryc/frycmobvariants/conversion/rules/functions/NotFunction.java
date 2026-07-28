package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.JsonHelper;

import java.util.Random;
import java.util.function.BiPredicate;

public record NotFunction(BiPredicate<MobEntity, Random> function) implements BiPredicate<MobEntity, Random> {

    public static final String ID = "NOT";

    @Override
    public boolean test(MobEntity mob, Random random) {
        return !this.function().test(mob, random);
    }

    public static NotFunction fromJson(JsonObject jsonObject) {
        JsonObject object = JsonHelper.getObject(jsonObject, "function");

        return new NotFunction(FrycJsonHelper.getMobConversionFunction(object));
    }
}
