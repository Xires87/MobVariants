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

public record OrFunction(List<BiPredicate<Mob, Random>> functions) implements BiPredicate<Mob, Random> {

    public static final String ID = "OR";

    @Override
    public boolean test(Mob mob, Random random) {
        return this.functions().stream().anyMatch(predicate -> predicate.test(mob, random));
    }

    public static OrFunction fromJson(JsonObject jsonObject) {
        ArrayList<BiPredicate<Mob, Random>> list = new ArrayList<>();
        JsonArray array = JsonHelper.getArray(jsonObject, "functions");

        array.forEach(jsonElement -> {
            if(jsonElement.isJsonObject()) {
                list.add(FrycJsonHelper.getMobConversionFunction(jsonElement.getAsJsonObject()));
            }
        });

        return new OrFunction(List.copyOf(list));
    }
}
