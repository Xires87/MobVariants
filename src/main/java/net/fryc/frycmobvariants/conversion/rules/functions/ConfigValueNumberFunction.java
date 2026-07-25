package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.fryc.frycmobvariants.util.NumberComparator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.JsonHelper;

import java.util.Random;
import java.util.function.BiPredicate;

public record ConfigValueNumberFunction(String varName, NumberComparator comparator, Number value) implements BiPredicate<MobEntity, Random> {

    public static final String ID = "config_value_number";

    @Override
    public boolean test(MobEntity mob, Random random) {
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
                JsonHelper.getString(jsonObject, "variable_name"),
                FrycJsonHelper.getNumberComparator(jsonObject, "comparator"),
                JsonHelper.getDouble(jsonObject, "comparison_value")
        );
    }
}
