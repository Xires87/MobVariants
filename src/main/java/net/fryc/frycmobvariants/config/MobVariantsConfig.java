package net.fryc.frycmobvariants.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frycmobvariants")
public class MobVariantsConfig implements ConfigData {

    //cave
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int zombieToForgottenConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int skeletonToUndeadWarriorConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int spiderToBlackSpiderConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("cave")
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int creeperToCaveCreeperConvertLevelY = 26;

    //nether
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int witherSkeletonConvertChance = 11;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int ghastConvertChance = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int piglinConvertChance = 29;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("nether")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int piglinBruteConvertChance = 39;


    //biomes
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int zombieToExplorerConvertChance = 75;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int zombieToFrozenZombieConvertChance = 80;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int spiderToTropicalSpiderConvertChance = 75;

    //mob attributes
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int undeadWarriorSpawnWithBowChance = 50;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    public int undeadWarriorsWeaknessDuration = 300;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("mobattributes")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int nightmareShootSingleFireballChance = 50;
}
