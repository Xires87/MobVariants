package net.fryc.frycmobvariants.conversion;

import net.fryc.frycmobvariants.conversion.rules.MobConvertingRule;
import net.fryc.frycmobvariants.conversion.rules.functions.*;
import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.List;

public class MobConversion {

    public static final HashMap<EntityType<?>, List<MobConvertingRule>> MOB_CONVERTING_RULES = new HashMap<>();

    public static void registerMobConversionFunctionTypes() {
        MobConversionFunctions.registerMobConversionFunctionType(OrFunction.ID, OrFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(AndFunction.ID, AndFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(NotFunction.ID, NotFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(FixedChanceFunction.ID, FixedChanceFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(ConfigValueNumberFunction.ID, ConfigValueNumberFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(MobPositionChanceFunction.ID, MobPositionChanceFunction::fromJson);
        MobConversionFunctions.registerMobConversionFunctionType(MobPositionFunction.ID, MobPositionFunction::fromJson);
        // TODO dodac biome i dimension function
    }
}
