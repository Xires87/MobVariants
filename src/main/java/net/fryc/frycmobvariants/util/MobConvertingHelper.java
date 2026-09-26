package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.conversion.MobConversion;
import net.fryc.frycmobvariants.conversion.rules.MobConversionEquipment;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingOutcome;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingRule;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class MobConvertingHelper {

    public static final Set<EntityType<? extends AbstractSkeleton>> SKELETON_UNDERWATER_CONVERSION_AVAILABLE = new HashSet<>();

    private static final Random RANDOM = new Random();

    public static ItemStack getRandomItemStack(Map<Item, Pair<Float, Float>> map) {
        float chance = RANDOM.nextFloat();
        Optional<Map.Entry<Item, Pair<Float, Float>>> optional = map.entrySet().stream().filter(entry -> {
            return chance >= entry.getValue().getA() && chance < entry.getValue().getB();
        }).findAny();

        return optional.isPresent() ? new ItemStack(optional.get().getKey()) : ItemStack.EMPTY;
    }

    public static void detectMobAndTryToConvert(Mob mob, Random random) {
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

    @SuppressWarnings("unchecked")
    private static void convertMobAndSetCustomEquipment(Mob originalMob, Random random, MobConvertingOutcome outcome) {
        int slimeSize = originalMob instanceof Slime slime ? slime.getSize() : -1;
        originalMob.convertTo((EntityType<? extends Mob>) outcome.entityType(), new ConversionParams(ConversionType.SINGLE, outcome.conversionEquipment().keepEquipment(), true, null), EntitySpawnReason.NATURAL, mob -> {
            // items need to be added next tick: adding in the same tick caused visual bugs (server/client synchronisation issues)
            // TODO sprawdzic czy nadal trzeba w osobnym ticku
            ((CanConvert) mob).setNextTickUpdate(() -> {
                if(slimeSize > -1 && mob instanceof Slime slime) {
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
                        mob.setItemSlot(equipmentSlot, new ItemStack(list.get(random.nextInt(list.size())).item()));
                    }
                });
            });
        });
    }
}
