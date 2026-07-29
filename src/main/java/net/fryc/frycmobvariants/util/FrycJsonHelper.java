package net.fryc.frycmobvariants.util;

import com.google.gson.*;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.conversion.rules.MobConversionEquipment;
import net.fryc.frycmobvariants.conversion.rules.functions.MobConversionFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiPredicate;

public class FrycJsonHelper {

    public static RegistryEntry<EntityType<? extends Entity>> asEntityType(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            String string = element.getAsString();
            return Registries.ENTITY_TYPE.getEntry(Identifier.of(string)).orElseThrow(() -> new JsonSyntaxException("Expected " + name + " to be an entity type, was unknown string '" + string + "'"));
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be an entity type, was " + JsonHelper.getType(element));
        }
    }

    public static RegistryEntry<EntityType<? extends Entity>> getEntityType(JsonObject object, String key) {
        if (object.has(key)) {
            return asEntityType(object.get(key), key);
        } else {
            throw new JsonSyntaxException("Missing " + key + ", expected to find an item");
        }
    }

    public static String getMobConversionFunctionType(JsonObject object) {
        return JsonHelper.getString(object, "type");
    }

    public static BiPredicate<MobEntity, Random> getMobConversionFunction(JsonObject functionObject) {
        return MobConversionFunctions.getMobConversionFunctionTypeOrDefault(
                getMobConversionFunctionType(functionObject),
                (obj) -> (mob, rand) -> false
        ).apply(functionObject);
    }

    public static MobConversionEquipment getMobConversionEquipment(JsonObject equipmentObject) {
        boolean keepEquipment = JsonHelper.getBoolean(equipmentObject, "keep_equipment", true);
        boolean initEquipment = JsonHelper.getBoolean(equipmentObject, "init_equipment", false);
        List<MobConversionEquipment.MobConvertItem> customEquipment = getMobConversionCustomEquipment(JsonHelper.getArray(equipmentObject, "custom_equipment", new JsonArray()));

        return new MobConversionEquipment(keepEquipment, initEquipment, customEquipment);
    }

    private static List<MobConversionEquipment.MobConvertItem> getMobConversionCustomEquipment(JsonArray customEquipment) {
        ArrayList<MobConversionEquipment.MobConvertItem> itemList = new ArrayList<>();

        customEquipment.forEach(el -> {
            if(el.isJsonObject()) {
                Item item = JsonHelper.getItem(el.getAsJsonObject(), "item").value();
                EquipmentSlot slot = getEquipmentSlot(el.getAsJsonObject(), "slot");
                double chance = JsonHelper.getDouble(el.getAsJsonObject(), "chance");

                itemList.add(new MobConversionEquipment.MobConvertItem(slot, item, chance));
            }
        });

        return List.copyOf(itemList);
    }

    public static EquipmentSlot getEquipmentSlot(JsonObject object, String key) {
        return EquipmentSlot.byName(JsonHelper.getString(object, key).toLowerCase(Locale.ROOT));
    }

    public static NumberComparator getNumberComparator(JsonObject jsonObject, String key) {
        return NumberComparator.getByName(JsonHelper.getString(jsonObject, key));
    }

    public static NumberComparator getNumberComparator(JsonObject jsonObject, String key, NumberComparator defaultValue) {
        try {
            return NumberComparator.getByName(JsonHelper.getString(jsonObject, key));
        } catch (Exception ignored) { }

        return defaultValue;
    }

    public static double getValue(JsonObject object) throws NoSuchFieldException, IllegalAccessException {
        JsonPrimitive el = object.get("value").getAsJsonPrimitive();
        double multiplier = JsonHelper.getDouble(object, "multiplier", 1.0);

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
