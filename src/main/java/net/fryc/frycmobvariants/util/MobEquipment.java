package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.ExecutionerEntity;
import net.fryc.frycmobvariants.mobs.nether.SoulStealerEntity;

public class MobEquipment {

    public static void initializePossibleEquipment(){
        StatusEffectHelper.initializeStatusEffectMap();

        CorsairEntity.corsairWeapons = StringHelper.transformStringToFloatMap(MobVariants.config.corsairWeapons);
        UndeadWarriorEntity.undeadWarriorWeapons = StringHelper.transformStringToFloatMap(MobVariants.config.undeadWarriorAttributes.undeadWarriorWeapons);
        SoulStealerEntity.soulStealerWeapons = StringHelper.transformStringToFloatMap(MobVariants.config.soulStealerWeapons);

        ExecutionerEntity.executionerWeapons = StringHelper.transformStringToFloatMap(MobVariants.config.executionerAttributes.executionerWeapons);
        ExecutionerEntity.executionerHelmets = StringHelper.transformStringToFloatMap(MobVariants.config.executionerAttributes.executionerHelmets);
        ExecutionerEntity.executionerChestplates = StringHelper.transformStringToFloatMap(MobVariants.config.executionerAttributes.executionerChestplates);
        ExecutionerEntity.executionerLeggings = StringHelper.transformStringToFloatMap(MobVariants.config.executionerAttributes.executionerLeggings);
        ExecutionerEntity.executionerBoots = StringHelper.transformStringToFloatMap(MobVariants.config.executionerAttributes.executionerBoots);
    }
}
