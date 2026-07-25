package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.entity.mob.MobEntity;

import java.util.Random;
import java.util.function.BiPredicate;

public record FixedChanceFunction(double value) implements BiPredicate<MobEntity, Random> {

    public static final String ID = "fixed_chance";

    @Override
    public boolean test(MobEntity mob, Random random) {
        return random.nextDouble() < this.value();
    }

    public static FixedChanceFunction fromJson(JsonObject jsonObject) {
        double value;
        try {
            value = FrycJsonHelper.getValue(jsonObject);
        } catch (Exception e) {
            MobVariants.LOGGER.error("Failed to get value from '" + ID + "' function", e);
            value = 0.0;
        }

        return new FixedChanceFunction(value);
    }
}
