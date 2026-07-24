package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;

public record AndFunction(List<BiPredicate<MobEntity, Random>> functions) implements BiPredicate<MobEntity, Random> {


    @Override
    public boolean test(MobEntity mob, Random random) {
        return this.functions().stream().allMatch(predicate -> predicate.test(mob, random));
    }

    public static AndFunction fromJson(JsonObject jsonObject) {
        ArrayList<BiPredicate<MobEntity, Random>> list = new ArrayList<>();
        JsonArray array = JsonHelper.getArray(jsonObject, "functions");

        array.forEach(jsonElement -> {
            if(jsonElement.isJsonObject()) {
                list.add(FrycJsonHelper.getMobConversionFunction(jsonElement.getAsJsonObject()));
            }
        });

        return new AndFunction(List.copyOf(list));
    }
}
