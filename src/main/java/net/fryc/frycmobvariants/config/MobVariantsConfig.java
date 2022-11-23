package net.fryc.frycmobvariants.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frycmobvariants")
public class MobVariantsConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int zombieToForgottenConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int skeletonToUndeadWarriorConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int spiderToBlackSpiderConvertLevelY = 26;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 300, min = -64)
    public int creeperToCaveCreeperConvertLevelY = 26;
}
