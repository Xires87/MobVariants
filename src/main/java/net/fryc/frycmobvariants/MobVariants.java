package net.fryc.frycmobvariants;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fryc.frycmobvariants.commands.TryToConvertCommand;
import net.fryc.frycmobvariants.config.MobVariantsConfig;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.ModSpawnEggs;
import net.fryc.frycmobvariants.util.MobEquipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobVariants implements ModInitializer {
	public static final String MOD_ID = "frycmobvariants";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static MobVariantsConfig config;


	@Override
	public void onInitialize() {
		AutoConfig.register(MobVariantsConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(MobVariantsConfig.class).getConfig();

		ModMobs.registerModMobs();
		ModSpawnEggs.registerSpawnEggs();

		CommandRegistrationCallback.EVENT.register(TryToConvertCommand::register);

		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
			MobEquipment.initializePossibleEquipment();
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			MobEquipment.initializePossibleEquipment();
		});
	}
}
