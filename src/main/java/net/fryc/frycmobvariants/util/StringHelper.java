package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.MobVariants;
import oshi.util.tuples.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class StringHelper {

    /**
     *
     * @param stringsAndNumbers string containing strings and numbers separated with semicolon in the following pattern: String;int;int;String;int;int
     * @return map containing String as a key and pair of the next 2 ints as a value
     */
    public static HashMap<String, Pair<Integer, Integer>> transformStringToMap(String stringsAndNumbers){
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
                        MobVariants.LOGGER.error(e.getMessage());
                        MobVariants.LOGGER.error("Given String could not be parsed to int");
                    }
                }
                case 2 ->  {
                    try {
                        sValue = Integer.parseInt(string);
                    }
                    catch (Exception e){
                        MobVariants.LOGGER.error(e.getMessage());
                        MobVariants.LOGGER.error("Given String could not be parsed to int");
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
}
