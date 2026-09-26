package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;

import java.util.Random;
import java.util.function.BiPredicate;

public record MobPositionChanceFunction(double baseValue, double yModifier, double xModifier, double zModifier) implements BiPredicate<Mob, Random> {

    public static final String ID = "mob_position_chance";

    @Override
    public boolean test(Mob mob, Random random) {
        return random.nextDouble() < this.baseValue() + (mob.getY() * this.yModifier()) + (mob.getX() * this.xModifier()) + (mob.getZ() * this.zModifier());
    }

    public static MobPositionChanceFunction fromJson(JsonObject jsonObject) {
        double baseValue;
        double yValue;
        double xValue;
        double zValue;

        try {
            baseValue = FrycJsonHelper.getValue(GsonHelper.getAsJsonObject(jsonObject, "base_value", new JsonObject()), 0.0);
            yValue = FrycJsonHelper.getValue(GsonHelper.getAsJsonObject(jsonObject, "y", new JsonObject()), 0.0);
            xValue = FrycJsonHelper.getValue(GsonHelper.getAsJsonObject(jsonObject, "x", new JsonObject()), 0.0);
            zValue = FrycJsonHelper.getValue(GsonHelper.getAsJsonObject(jsonObject, "z", new JsonObject()), 0.0);
        } catch (Exception e) {
            MobVariants.LOGGER.error("Either 'base_value', 'y', 'x' or 'z' is incorrectly set in '" + ID + "' function", e);
            baseValue = 0.0;
            yValue = 0.0;
            xValue = 0.0;
            zValue = 0.0;
        }

        return new MobPositionChanceFunction(baseValue, yValue, xValue, zValue);
    }
}
