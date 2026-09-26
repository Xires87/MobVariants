package net.fryc.frycmobvariants.conversion.json;


public class MobConversionRulesResourceReloadListener /*implements SimpleSynchronousResourceReloadListener*/ {

    private static final String MOB_CONVERSION_RULES_PATH = "mob_conversion_rules";

    /*
    @Override
    public Identifier getFabricId() {
        return Identifier.of(MobVariants.MOD_ID, MOB_CONVERSION_RULES_PATH);
    }

     TODO reload listener

    @Override
    public void reload(ResourceManager manager) {
        MobConversion.MOB_CONVERTING_RULES.clear();

        for(Identifier id : manager.findResources(MOB_CONVERSION_RULES_PATH, path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonObject jsonObject = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();

                int priority = JsonHelper.getInt(jsonObject, "priority", 1);
                RegistryEntry<EntityType<? extends Entity>> targetEntity = FrycJsonHelper.getEntityType(jsonObject, "target_mob");
                RegistryEntry<EntityType<? extends Entity>> outcomeEntity = FrycJsonHelper.getEntityType(jsonObject, "outcome_mob");

                // non-mob target will just not work and non-mob outcome will print an error in logs
                EntityType<? extends Entity> targetMob = targetEntity.value();
                EntityType<? extends Entity> outcomeMob = outcomeEntity.value();

                JsonObject requirementsObject = JsonHelper.getObject(jsonObject, "requirements");
                JsonObject equipmentObject = JsonHelper.getObject(jsonObject, "equipment");

                BiPredicate<MobEntity, Random> requirements = FrycJsonHelper.getMobConversionFunction(requirementsObject);
                MobConversionEquipment equipment = FrycJsonHelper.getMobConversionEquipment(equipmentObject);

                MobConvertingRule rule = new MobConvertingRule(priority, (mob, random) -> {
                    if(mob.getType().equals(targetMob) /* <-- redundant check *//* && requirements.test(mob, random)) {
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

    */
}
