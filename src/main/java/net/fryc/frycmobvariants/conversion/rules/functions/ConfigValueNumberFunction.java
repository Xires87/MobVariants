package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.fryc.frycmobvariants.util.NumberComparator;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;

import java.util.Random;
import java.util.function.BiPredicate;

public record ConfigValueNumberFunction(String varName, NumberComparator comparator, Number value) implements BiPredicate<Mob, Random> {

    public static final String ID = "config_value_number";

    @Override
    public boolean test(Mob mob, Random random) {
        Object varValue = null;
        try {
            varValue = MobVariants.config.getClass().getField(this.varName()).get(MobVariants.config);
        } catch (Exception e) {
            MobVariants.LOGGER.error("Invalid config variable: '" + this.varName() + "' in '" + ID + "' function", e);
            return false;
        }

        return varValue instanceof Number number && this.comparator().compare(number, this.value());
    }

    public static ConfigValueNumberFunction fromJson(JsonObject jsonObject) {
        return new ConfigValueNumberFunction(
                GsonHelper.getAsString(jsonObject, "variable_name"),
                FrycJsonHelper.getNumberComparator(jsonObject, "comparator"),
                GsonHelper.getAsDouble(jsonObject, "comparison_value")
        );
    }
}
