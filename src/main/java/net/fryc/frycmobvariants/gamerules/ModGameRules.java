package net.fryc.frycmobvariants.gamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {

    public static GameRules.Key<GameRules.IntRule> FORGOTTEN_SPAWN_LEVEL;
    public static GameRules.Key<GameRules.IntRule> UNDEAD_WARRIOR_SPAWN_LEVEL;
    public static GameRules.Key<GameRules.IntRule> ARMORED_SPIDER_SPAWN_LEVEL;

    public static void registerGameRules(){
        FORGOTTEN_SPAWN_LEVEL = GameRuleRegistry.register("zombieToForgottenConvertLevelY", GameRules.Category.SPAWNING, GameRuleFactory.createIntRule(26, -64, 300));
        UNDEAD_WARRIOR_SPAWN_LEVEL = GameRuleRegistry.register("skeletonToUndeadWarriorConvertLevelY", GameRules.Category.SPAWNING, GameRuleFactory.createIntRule(26, -64, 300));
        ARMORED_SPIDER_SPAWN_LEVEL = GameRuleRegistry.register("spiderToBlackSpiderConvertLevelY", GameRules.Category.SPAWNING, GameRuleFactory.createIntRule(26, -64, 300));
    }

}
