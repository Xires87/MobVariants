package net.fryc.frycmobvariants.util;


import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Random;

public class StatusEffectHelper {

    public static HashMap<String, Pair<Integer, Integer>> availableStatusEffects;

    /**
     *
     * @param random random
     * @return Pair containing a status effect and a pair of integers (duration and amplifier)
     */
    public static Pair<RegistryEntry<StatusEffect>, Pair<Integer, Integer>> pickRandomStatusEffect(Random random) {
        int i = random.nextInt(0, availableStatusEffects.size());
        int j = 0;
        for(String key : availableStatusEffects.keySet()){
            if(j == i){
                RegistryEntry<StatusEffect> effect = getStatusEffectFromString(key);
                Pair<Integer, Integer> durAndAmp = availableStatusEffects.get(key);

                return new Pair<>(effect, durAndAmp);
            }
            j++;
        }

        return new Pair<>(StatusEffects.WEAKNESS, new Pair<>(400, 1));
    }

    public static void initializeStatusEffectMap(){
        availableStatusEffects = StringHelper.transformStringToMap(MobVariants.config.undeadWarriorAttributes.undeadWarriorsArrowEffect);
    }

    public static RegistryEntry<StatusEffect> getStatusEffectFromString(String statusEffect){
        return Registries.STATUS_EFFECT.getEntry(Identifier.of(statusEffect)).orElseGet(() -> {
            MobVariants.LOGGER.error("Unable to find the following status effect: '" + statusEffect + "'");
            return (RegistryEntry.Reference<StatusEffect>) StatusEffects.WEAKNESS;
        });
    }

}
