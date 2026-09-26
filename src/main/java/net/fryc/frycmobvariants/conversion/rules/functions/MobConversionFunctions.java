package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.Mob;

import java.util.HashMap;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class MobConversionFunctions {

    private static final HashMap<String, Function<JsonObject, BiPredicate<Mob, Random>>> MOB_CONVERSION_FUNCTION_TYPES = new HashMap<>();

    public static void registerMobConversionFunctionType(String key, Function<JsonObject, BiPredicate<Mob, Random>> jsonToBiPredicate) {
        if(!MOB_CONVERSION_FUNCTION_TYPES.containsKey(key)) {
            MOB_CONVERSION_FUNCTION_TYPES.put(key, jsonToBiPredicate);
        }
    }

    public static Function<JsonObject, BiPredicate<Mob, Random>> getMobConversionFunctionType(String key) {
        return MOB_CONVERSION_FUNCTION_TYPES.get(key);
    }

    public static Function<JsonObject, BiPredicate<Mob, Random>> getMobConversionFunctionTypeOrDefault(String key, Function<JsonObject, BiPredicate<Mob, Random>> defaultValue) {
        return MOB_CONVERSION_FUNCTION_TYPES.getOrDefault(key, defaultValue);
    }
}
