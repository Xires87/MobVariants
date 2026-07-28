package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.conversion.MobConversion;
import net.fryc.frycmobvariants.conversion.rules.MobConversionEquipment;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingOutcome;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingRule;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MobConvertingHelper {

    public static ItemStack getRandomItemStack(Map<Item, Pair<Float, Float>> map) {
        float chance = ThreadLocalRandom.current().nextFloat();
        Optional<Map.Entry<Item, Pair<Float, Float>>> optional = map.entrySet().stream().filter(entry -> {
            return chance >= entry.getValue().getA() && chance < entry.getValue().getB();
        }).findAny();

        return optional.isPresent() ? new ItemStack(optional.get().getKey()) : ItemStack.EMPTY;
    }

    public static void detectMobAndTryToConvert(MobEntity mob, Random random) {
        MobConvertingOutcome outcome;
        int currentPriority = 0;
        ArrayList<MobConvertingOutcome> possibleOutcomes = new ArrayList<>();

        for(MobConvertingRule rule : MobConversion.MOB_CONVERTING_RULES.getOrDefault(mob.getType(), List.of())) {
            if(rule.priority() < currentPriority) continue;

            outcome = rule.function().test(mob, random);
            if(outcome != null) {
                if(rule.priority() > currentPriority) {
                    possibleOutcomes.clear();
                    currentPriority = rule.priority();
                }

                possibleOutcomes.add(outcome);
            }
        }

        if(!possibleOutcomes.isEmpty()) {
            convertMobAndSetCustomEquipment(
                    mob, random,
                    possibleOutcomes.get(random.nextInt(0, possibleOutcomes.size()))
            );
        }
    }

    private static void convertMobAndSetCustomEquipment(MobEntity originalMob, Random random, MobConvertingOutcome outcome) {
        int slimeSize = originalMob instanceof SlimeEntity slime ? slime.getSize() : -1;
        MobEntity mob = convertMob(originalMob, outcome);

        if(mob != null) {
            if(slimeSize > -1 && mob instanceof SlimeEntity slime) {
                slime.setSize(slimeSize, true);
            }

            if(outcome.conversionEquipment().initEquipment()) {
                ((CanConvert) mob).initMobEquipment();
            }

            outcome.conversionEquipment().customEquipment().stream().collect(
                    Collectors.groupingBy(MobConversionEquipment.MobConvertItem::slot)
            ).forEach((equipmentSlot, mobConvertItems) -> {
                List<MobConversionEquipment.MobConvertItem> list = mobConvertItems.stream().filter(item -> {
                    return random.nextDouble() < item.chance();
                }).toList();

                if(!list.isEmpty()) {
                    mob.equipStack(equipmentSlot, new ItemStack(list.get(random.nextInt(list.size())).item()));
                }
            });
        }
    }

    private static MobEntity convertMob(MobEntity originalMob, MobConvertingOutcome outcome) {
        if(originalMob.getType().equals(outcome.entityType())) {
            if(!outcome.conversionEquipment().keepEquipment()) {
                originalMob.getEquippedItems().forEach(item -> item.setCount(0));
            }

            return originalMob;
        }

        return originalMob.convertTo(outcome.entityType(), outcome.conversionEquipment().keepEquipment());
    }
}
