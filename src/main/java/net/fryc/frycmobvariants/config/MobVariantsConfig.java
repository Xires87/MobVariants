package net.fryc.frycmobvariants.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.util.DifficultyPicker;

@Config(name = MobVariants.MOD_ID)
public class MobVariantsConfig implements ConfigData {

    //cave
    @Comment("Zombies have a chance to convert to Forgotten when they spawn below this Y level")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int zombieToForgottenConvertLevelY = 26;

    @Comment("When this value is below 0, chance of converting increases by 1% with every Y level below value specified in previous option")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    public double fixedChanceToConvertZombieUnderSelectedYLevel = -1.0;

    @Comment("Skeletons have a chance to convert to Undead Warrior when they spawn below this Y level")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int skeletonToUndeadWarriorConvertLevelY = 26;

    @Comment("When this value is below 0, chance of converting increases by 1% with every Y level below value specified in previous option")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    public double fixedChanceToConvertSkeletonUnderSelectedYLevel = -1;

    @Comment("Spiders have a chance to convert to Armored Spider when they spawn below this Y level")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int spiderToArmoredSpiderConvertLevelY = 26;

    @Comment("When this value is below 0, chance of converting increases by 1% with every Y level below value specified in previous option")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    public double fixedChanceToConvertSpiderUnderSelectedYLevel = -1;

    @Comment("Creepers have a chance to convert to Cave Creeper when they spawn below this Y level")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int creeperToCaveCreeperConvertLevelY = 26;

    @Comment("When this value is below 0, chance of converting increases by 1% with every Y level below value specified in previous option")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    public double fixedChanceToConvertCreeperUnderSelectedYLevel = -1;


    //nether
    @Comment("Nether variants \n1.0 = 100%\n0.0 = 0%")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double witherSkeletonConvertChance = 0.12;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double ghastConvertChance = 0.27;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double piglinConvertChance = 0.30;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double piglinBruteConvertChance = 0.40;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double skeletonToSoulStealerConvertChance = 0.32;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double magmaCubeToLavaSlimeConvertChance = 0.20;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    public double zombifiedPiglinConvertChance = 0.05;


    //biomes
    @Comment("Mob conversion isn't affected by biome under this Y level, unless they spawn in one of the cave biomes in which they can convert")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public int minYLevelForBiomeVariants = 49;

    @Comment("Biome variants \n1.0 = 100%\n0.0 = 0%")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double zombieToExplorerConvertChance = 0.76;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double zombieToFrozenZombieConvertChance = 0.81;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double zombieToBloatedCorpseConvertChance = 0.76;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double spiderToTropicalSpiderConvertChance = 0.76;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double slimeToToxicSlimeConvertChance = 0.71;

    @Comment("For skeletons spawned in ocean or beach. Doesn't affect Corsairs spawning on Shipwrecks")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    public double skeletonToCorsairConvertChance = 0.61;


    //mob attributes

    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.Gui.CollapsibleObject
    public UndeadWarriorAttributes undeadWarriorAttributes = new UndeadWarriorAttributes();

    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.Gui.CollapsibleObject
    public ExecutionerAttibutes executionerAttributes = new ExecutionerAttibutes();

    @Comment("Additional magic damage dealt by Soul Stealer. On normal difficulty this value is increased by 1. On hard, by 3")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    public float soulStealersBaseMagicDamage = 1.0F;

    @Comment("List of weapons that Soul Stealer can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.Gui.Tooltip
    public String soulStealerWeapons = "minecraft:iron_hoe;1.0";

    @Comment("20 = 1s. Set it to 10 (or lower) to prevent lava from disappearing")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    public int timeToRemoveLavaLeftByLavaSlime = 30;
// TODO dokonczyc
    @Comment("List of weapons that Corsair can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.Gui.Tooltip
    public String corsairWeapons = "minecraft:wooden_sword;0.72";

    @Comment("Minimal difficulty at which Bloated Corpse's cloud deals poison damage. Set to NONE to remove poison damage from the cloud")
    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.Gui.Tooltip
    public DifficultyPicker bloatedCorpsesCloudPoisonDamageDifficulty = DifficultyPicker.HARD;

    @ConfigEntry.Category("mobattributes")
    public boolean convertZombiesToFrozenZombiesInPowderSnow = true;

    @ConfigEntry.Category("mobattributes")
    public boolean convertSkeletonsToCorsairsUnderwater = true;

    @Comment("When true, Frozen Zombies become normal zombies after spending some time exposed to sunlight, fire or water")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    public boolean enableFrozenZombieConvertingToNormalZombie = true;


    public static class UndeadWarriorAttributes{
        @Comment("Skeletons with enchanted bow always convert to undead warrior with bow")
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Category("mobattributes")
        public boolean alwaysKeepEnchantedBow = true;

        @Comment("List of weapons that Undead Warrior can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String undeadWarriorWeapons = "minecraft:stone_sword;0.50;minecraft:bow;0.50";

        @ConfigEntry.Category("mobattributes")
        public int undeadWarriorsMinTippedArrowsCount = 1;

        @ConfigEntry.Category("mobattributes")
        public int undeadWarriorsMaxTippedArrowsCount = 4;

        @ConfigEntry.Category("mobattributes")
        public int undeadWarriorsTippedArrowDropChancePerTippedArrowHeld = 3;

        @Comment("List of effects (with duration and amplifier) that Undead Warrior's arrow can have in the following pattern: 'StatusEffect;duration;amplifier;StatusEffect;duration;amplifier'")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String undeadWarriorsArrowEffect = "minecraft:weakness;400;1;minecraft:blindness;80;1;minecraft:instant_damage;1;1";

    }

    public static class ExecutionerAttibutes {

        @Comment("List of weapons that Executioner can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String executionerWeapons = "minecraft:stone_axe;1.0";

        @Comment("List of helmets that Executioner can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String executionerHelmets = "minecraft:iron_helmet;1.0";

        @Comment("List of chestplates that Executioner can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String executionerChestplates = "minecraft:iron_chestplate;0.50";

        @Comment("List of leggings that Executioner can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String executionerLeggings = "minecraft:iron_leggings;0.25";

        @Comment("List of boots that Executioner can spawn with in the following pattern: 'Item;Chance;Item;Chance' \n Sum of chances cannot be greater than 1.0")
        @ConfigEntry.Category("mobattributes")
        @ConfigEntry.Gui.Tooltip
        public String executionerBoots = "minecraft:iron_boots;0.04";

    }
}


