package net.fryc.frycmobvariants.conversion;

import net.fryc.frycmobvariants.conversion.rules.MobConvertingRule;
import net.fryc.frycmobvariants.conversion.rules.functions.AndFunction;
import net.fryc.frycmobvariants.conversion.rules.functions.MobConversionFunctions;
import net.fryc.frycmobvariants.conversion.rules.functions.OrFunction;
import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.List;

public class MobConversion {

    public static final HashMap<EntityType<?>, List<MobConvertingRule>> MOB_CONVERTING_RULES = new HashMap<>();

    public static void registerMobConversionFunctionTypes() {
        MobConversionFunctions.registerMobConversionFunctionType("OR", OrFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType("AND", AndFunction::fromJson);
    }
}
