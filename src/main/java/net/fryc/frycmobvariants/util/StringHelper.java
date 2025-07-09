package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;

public class StringHelper {

    /**
     *
     * @param stringsAndNumbers string containing strings and numbers separated with semicolon in the following pattern: String;int;int;String;int;int
     * @return map containing String as a key and pair of the next 2 ints as a value
     */
    public static HashMap<String, Pair<Integer, Integer>> transformStringToIntMap(String stringsAndNumbers){
        HashMap<String, Pair<Integer, Integer>> map = new HashMap<>();
        Iterator<String> iterator = Arrays.stream(stringsAndNumbers.split(";", 0)).iterator();
        int i = 0;
        String key = "";
        int fValue = 0;
        int sValue = 0;
        while(iterator.hasNext()){
            String string = iterator.next();
            switch (i){
                case 0 -> key = string;
                case 1 -> {
                    try {
                        fValue = Integer.parseInt(string);
                    }
                    catch (Exception e){
                        MobVariants.LOGGER.error("An error occurred while reading MobVariants config. Given String could not be parsed to int", e);
                    }
                }
                case 2 ->  {
                    try {
                        sValue = Integer.parseInt(string);
                    }
                    catch (Exception e){
                        MobVariants.LOGGER.error("An error occurred while reading MobVariants config. Given String could not be parsed to int", e);
                    }
                }
                default -> {
                    map.put(key, new Pair<>(fValue, sValue));
                    key = string;
                    i = 0;
                }
            }
            i++;
        }
        if(i == 3){
            map.put(key, new Pair<>(fValue, sValue));
        }

        return map;
    }

    /**
     *
     * @param stringsAndNumbers string containing strings and numbers separated with semicolon in the following pattern: String;float;String;float
     * @return map containing Item as a key and pair of 2 floats as a value - first float is previous value of the second float (0 for first value)
     */
    public static HashMap<Item, Pair<Float, Float>> transformStringToFloatMap(String stringsAndNumbers){
        HashMap<Item, Pair<Float, Float>> map = new HashMap<>();
        Iterator<String> iterator = Arrays.stream(stringsAndNumbers.split(";", 0)).iterator();
        int i = 0;
        String key = "";
        float fValue = 0.0F;
        float sValue = 0.0F;
        while(iterator.hasNext()){
            String string = iterator.next();
            switch (i){
                case 0 -> key = string;
                case 1 -> {
                    try {
                        fValue = Float.parseFloat(string) + sValue;
                    }
                    catch (Exception e){
                        MobVariants.LOGGER.error("An error occurred while reading MobVariants config. Given String could not be parsed to float", e);
                    }
                }
                default -> {
                    putIfKeyNotNull(map, transformStringToItem(key), sValue, fValue);
                    sValue = fValue;
                    key = string;
                    i = 0;
                }
            }
            i++;
        }
        if(i == 2){
            putIfKeyNotNull(map, transformStringToItem(key), sValue, fValue);
        }

        return map;
    }

    private static void putIfKeyNotNull(Map<Item, Pair<Float, Float>> map, @Nullable Item key, float sValue, float fValue){
        if(key != null){
            map.put(key, new Pair<>(sValue, fValue));
        }
    }

    @Nullable
    public static Item transformStringToItem(String item){
        Optional<Item> optional = Registries.ITEM.getOrEmpty(new Identifier(item));
        if(optional.isEmpty()){
            MobVariants.LOGGER.error("An error occurred while reading MobVariants config. Unknown item: " + item);
            return null;
        }

        return optional.get();
    }

    public static StatusEffect getStatusEffectFromString(String statusEffect){
        return Registries.STATUS_EFFECT.getOrEmpty(new Identifier(statusEffect)).orElseGet(() -> {
            MobVariants.LOGGER.error("Unable to find the following status effect: '" + statusEffect + "'");
            return StatusEffects.WEAKNESS;
        });
    }
}
