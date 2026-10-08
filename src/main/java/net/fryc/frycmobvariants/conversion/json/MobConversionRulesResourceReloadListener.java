package net.fryc.frycmobvariants.conversion.json;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.conversion.MobConversion;
import net.fryc.frycmobvariants.conversion.rules.MobConversionEquipment;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingOutcome;
import net.fryc.frycmobvariants.conversion.rules.MobConvertingRule;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiPredicate;

public class MobConversionRulesResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private static final String MOB_CONVERSION_RULES_PATH = "mob_conversion_rules";

    @Override
    public Identifier getFabricId() {
        return Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, MOB_CONVERSION_RULES_PATH);
    }


    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        MobConversion.MOB_CONVERTING_RULES.clear();

        for(Identifier id : manager.listResources(MOB_CONVERSION_RULES_PATH, path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().open()) {
                JsonObject jsonObject = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();

                int priority = GsonHelper.getAsInt(jsonObject, "priority", 1);
                Holder<EntityType<? extends Entity>> targetEntity = FrycJsonHelper.getEntityType(jsonObject, "target_mob");
                Holder<EntityType<? extends Entity>> outcomeEntity = FrycJsonHelper.getEntityType(jsonObject, "outcome_mob");

                // non-mob target will just not work and non-mob outcome will print an error in logs
                EntityType<? extends Entity> targetMob = targetEntity.value();
                EntityType<? extends Entity> outcomeMob = outcomeEntity.value();

                JsonObject requirementsObject = GsonHelper.getAsJsonObject(jsonObject, "requirements");
                JsonObject equipmentObject = GsonHelper.getAsJsonObject(jsonObject, "equipment");

                BiPredicate<Mob, Random> requirements = FrycJsonHelper.getMobConversionFunction(requirementsObject);
                MobConversionEquipment equipment = FrycJsonHelper.getMobConversionEquipment(equipmentObject);

                MobConvertingRule rule = new MobConvertingRule(priority, (mob, random) -> {
                    if(mob.getType().equals(targetMob) /* <-- redundant check */ && requirements.test(mob, random)) {
                        return new MobConvertingOutcome(equipment, outcomeMob);
                    }

                    return null;
                });

                MobConversion.MOB_CONVERTING_RULES.putIfAbsent(targetMob, new ArrayList<>());
                MobConversion.MOB_CONVERTING_RULES.get(targetMob).add(rule);

            } catch(Exception e) {
                MobVariants.LOGGER.error("Error occurred while loading resource json: " + id.toString(), e);
            }
        }
    }
}
