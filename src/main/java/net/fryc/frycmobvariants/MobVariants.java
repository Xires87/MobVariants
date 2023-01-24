package net.fryc.frycmobvariants;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fryc.frycmobvariants.config.MobVariantsConfig;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.ModSpawnEggs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobVariants implements ModInitializer {
	public static final String MOD_ID = "frycmobvariants";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static MobVariantsConfig config;


	@Override
	public void onInitialize() {
		AutoConfig.register(MobVariantsConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(MobVariantsConfig.class).getConfig();

		ModMobs.registerModMobs();
		ModSpawnEggs.registerSpawnEggs();
//todo lang, loot tables and frozen zombie
	}
}
