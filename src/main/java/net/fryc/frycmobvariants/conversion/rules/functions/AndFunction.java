package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;

public record AndFunction(List<BiPredicate<Mob, Random>> functions) implements BiPredicate<Mob, Random> {

    public static final String ID = "AND";

    @Override
    public boolean test(Mob mob, Random random) {
        return this.functions().stream().allMatch(predicate -> predicate.test(mob, random));
    }

    public static AndFunction fromJson(JsonObject jsonObject) {
        ArrayList<BiPredicate<Mob, Random>> list = new ArrayList<>();
        JsonArray array = GsonHelper.getAsJsonArray(jsonObject, "functions");

        array.forEach(jsonElement -> {
            if(jsonElement.isJsonObject()) {
                list.add(FrycJsonHelper.getMobConversionFunction(jsonElement.getAsJsonObject()));
            }
        });

        return new AndFunction(List.copyOf(list));
    }
}
