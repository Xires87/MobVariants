package net.fryc.frycmobvariants.util;


import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Random;

public class StatusEffectHelper {

    public static HashMap<String, Pair<Integer, Integer>> availableStatusEffects = new HashMap<>();

    /**
     *
     * @param random random
     * @return Pair containing a status effect and a pair of integers (duration and amplifier)
     */
    public static Pair<StatusEffect, Pair<Integer, Integer>> pickRandomStatusEffect(Random random) {
        int bound = !availableStatusEffects.isEmpty() ? availableStatusEffects.size() : 1;
        int i = random.nextInt(0, bound);
        int j = 0;
        for(String key : availableStatusEffects.keySet()){
            if(j == i){
                StatusEffect effect = StringHelper.getStatusEffectFromString(key);
                Pair<Integer, Integer> durAndAmp = availableStatusEffects.get(key);

                return new Pair<>(effect, durAndAmp);
            }
            j++;
        }

        return new Pair<>(StatusEffects.WEAKNESS, new Pair<>(400, 1));
    }

    public static void initializeStatusEffectMap(){
        availableStatusEffects = StringHelper.transformStringToIntMap(MobVariants.config.undeadWarriorAttributes.undeadWarriorsArrowEffect);
    }

}
