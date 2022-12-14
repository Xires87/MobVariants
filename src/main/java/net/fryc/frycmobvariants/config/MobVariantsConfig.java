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


    //biomes
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int zombieToExplorerConvertChance = 75;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("biome")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int spiderToTropicalSpiderConvertChance = 75;
}
