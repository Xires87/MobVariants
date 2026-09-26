package net.fryc.frycmobvariants.util;

import com.google.gson.*;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.conversion.rules.MobConversionEquipment;
import net.fryc.frycmobvariants.conversion.rules.functions.MobConversionFunctions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiPredicate;

public class FrycJsonHelper {

    public static Holder<EntityType<?>> asEntityType(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            String string = element.getAsString();
            return BuiltInRegistries.ENTITY_TYPE.get(Identifier.parse(string)).orElseThrow(() -> new JsonSyntaxException("Expected " + name + " to be an entity type, was unknown string '" + string + "'"));
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be an entity type, was " + GsonHelper.getType(element));
        }
    }

    public static Holder<EntityType<? extends Entity>> getEntityType(JsonObject object, String key) {
        if (object.has(key)) {
            return asEntityType(object.get(key), key);
        } else {
            throw new JsonSyntaxException("Missing " + key + ", expected to find an entity type");
        }
    }

    public static String getMobConversionFunctionType(JsonObject object) {
        return GsonHelper.getAsString(object, "type");
    }

    public static BiPredicate<Mob, Random> getMobConversionFunction(JsonObject functionObject) {
        return MobConversionFunctions.getMobConversionFunctionTypeOrDefault(
                getMobConversionFunctionType(functionObject),
                (obj) -> (mob, rand) -> false
        ).apply(functionObject);
    }

    public static MobConversionEquipment getMobConversionEquipment(JsonObject equipmentObject) {
        boolean keepEquipment = GsonHelper.getAsBoolean(equipmentObject, "keep_equipment", true);
        boolean initEquipment = GsonHelper.getAsBoolean(equipmentObject, "init_equipment", false);
        List<MobConversionEquipment.MobConvertItem> customEquipment = getMobConversionCustomEquipment(GsonHelper.getAsJsonArray(equipmentObject, "custom_equipment", new JsonArray()));

        return new MobConversionEquipment(keepEquipment, initEquipment, customEquipment);
    }

    private static List<MobConversionEquipment.MobConvertItem> getMobConversionCustomEquipment(JsonArray customEquipment) {
        ArrayList<MobConversionEquipment.MobConvertItem> itemList = new ArrayList<>();

        customEquipment.forEach(el -> {
            if(el.isJsonObject()) {
                Item item = GsonHelper.getAsItem(el.getAsJsonObject(), "item").value();
                EquipmentSlot slot = getEquipmentSlot(el.getAsJsonObject(), "slot");
                double chance = GsonHelper.getAsDouble(el.getAsJsonObject(), "chance");

                itemList.add(new MobConversionEquipment.MobConvertItem(slot, item, chance));
            }
        });

        return List.copyOf(itemList);
    }

    public static EquipmentSlot getEquipmentSlot(JsonObject object, String key) {
        return EquipmentSlot.byName(GsonHelper.getAsString(object, key).toLowerCase(Locale.ROOT));
    }

    public static NumberComparator getNumberComparator(JsonObject jsonObject, String key) {
        return NumberComparator.getByName(GsonHelper.getAsString(jsonObject, key));
    }

    public static NumberComparator getNumberComparator(JsonObject jsonObject, String key, NumberComparator defaultValue) {
        try {
            return NumberComparator.getByName(GsonHelper.getAsString(jsonObject, key));
        } catch (Exception ignored) { }

        return defaultValue;
    }

    public static double getValue(JsonObject object) throws NoSuchFieldException, IllegalAccessException {
        JsonPrimitive el = object.get("value").getAsJsonPrimitive();
        double multiplier = GsonHelper.getAsDouble(object, "multiplier", 1.0);

        if(el.isString()) {
            return ((Number) MobVariants.config.getClass().getField(el.getAsString()).get(MobVariants.config)).doubleValue() * multiplier;
        }

        return el.getAsDouble() * multiplier;
    }

    public static double getValue(JsonObject object, double defaultValue) throws NoSuchFieldException, IllegalAccessException {
        if(object.has("value")){
            return getValue(object);
        }

        return defaultValue;
    }
}
