package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.minecraft.entity.mob.MobEntity;

import java.util.HashMap;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class MobConversionFunctions {

    private static final HashMap<String, Function<JsonObject, BiPredicate<MobEntity, Random>>> MOB_CONVERSION_FUNCTION_TYPES = new HashMap<>();

    public static void registerMobConversionFunctionType(String key, Function<JsonObject, BiPredicate<MobEntity, Random>> jsonToBiPredicate) {
        if(!MOB_CONVERSION_FUNCTION_TYPES.containsKey(key)) {
            MOB_CONVERSION_FUNCTION_TYPES.put(key, jsonToBiPredicate);
        }
    }
    // TODO dodac reszte funkcji
    public static Function<JsonObject, BiPredicate<MobEntity, Random>> getMobConversionFunctionType(String key) {
        return MOB_CONVERSION_FUNCTION_TYPES.get(key);
    }

    public static Function<JsonObject, BiPredicate<MobEntity, Random>> getMobConversionFunctionTypeOrDefault(String key, Function<JsonObject, BiPredicate<MobEntity, Random>> defaultValue) {
        return MOB_CONVERSION_FUNCTION_TYPES.getOrDefault(key, defaultValue);
    }
}
