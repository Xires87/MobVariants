package net.fryc.frycmobvariants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryc.frycmobvariants.gamerules.ModGameRules;
import net.fryc.frycmobvariants.mobs.ForgottenEntity;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.eggs.ModSpawnEggs;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobVariants implements ModInitializer {
	public static final String MOD_ID = "frycmobvariants";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ModMobs.registerModMobs();
		ModSpawnEggs.registerSpawnEggs();

		ModGameRules.registerGameRules();
	}
}
