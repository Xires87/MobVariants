package net.fryc.frycmobvariants.mobs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModSpawnEggs {
    //cave variants
    public static final Item FORGOTTEN_SPAWN_EGG = new SpawnEggItem(ModMobs.FORGOTTEN, 3232308, 5651507, new Item.Settings());
    public static final Item UNDEAD_WARRIOR_SPAWN_EGG = new SpawnEggItem(ModMobs.UNDEAD_WARRIOR, 6387319, 4802889, new Item.Settings());
    public static final Item ARMORED_SPIDER_SPAWN_EGG = new SpawnEggItem(ModMobs.ARMORED_SPIDER, 1315860, 11013646, new Item.Settings());
    public static final Item CAVE_CREEPER_SPAWN_EGG = new SpawnEggItem(ModMobs.CAVE_CREEPER, 1315860, 3232308, new Item.Settings());

    //biome variants
    public static final Item EXPLORER_SPAWN_EGG = new SpawnEggItem(ModMobs.EXPLORER, 3232308, 894731, new Item.Settings());

    public static final Item FROZEN_ZOMBIE_SPAWN_EGG = new SpawnEggItem(ModMobs.FROZEN_ZOMBIE, 44975, 56063, new Item.Settings());
    public static final Item TROPICAL_SPIDER_SPAWN_EGG = new SpawnEggItem(ModMobs.TROPICAL_SPIDER, 894731, 11013646, new Item.Settings());

    //nether variants
    public static final Item EXECUTIONER_SPAWN_EGG = new SpawnEggItem(ModMobs.EXECUTIONER, 1315860, 4673845, new Item.Settings());
    public static final Item NIGHTMARE_SPAWN_EGG = new SpawnEggItem(ModMobs.NIGHTMARE, 16382451, 12369044, new Item.Settings());
    public static final Item INFECTED_PIGLIN_SPAWN_EGG = new SpawnEggItem(ModMobs.INFECTED_PIGLIN, 15373203, 16380836, new Item.Settings());
    public static final Item INFECTED_PIGLIN_BRUTE_SPAWN_EGG = new SpawnEggItem(ModMobs.INFECTED_PIGLIN_BRUTE, 15771042, 16380836, new Item.Settings());
    public static void registerSpawnEggs(){
        //cave variants
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "forgotten_spawn_egg"), FORGOTTEN_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "undead_warrior_spawn_egg"), UNDEAD_WARRIOR_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "armored_spider_spawn_egg"), ARMORED_SPIDER_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "cave_creeper_spawn_egg"), CAVE_CREEPER_SPAWN_EGG);

        //biome variants
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "explorer_spawn_egg"), EXPLORER_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "frozen_zombie_spawn_egg"), FROZEN_ZOMBIE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "tropical_spider_spawn_egg"), TROPICAL_SPIDER_SPAWN_EGG);

        //nether variants
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "executioner_spawn_egg"), EXECUTIONER_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "nightmare_spawn_egg"), NIGHTMARE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "infected_piglin_spawn_egg"), INFECTED_PIGLIN_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MobVariants.MOD_ID, "infected_piglin_brute_spawn_egg"), INFECTED_PIGLIN_BRUTE_SPAWN_EGG);

        //Item group
        ItemGroup MOB_VARIANTS = FabricItemGroup.builder(new Identifier(MobVariants.MOD_ID, "mob_variants_spawn_eggs"))
                .displayName(Text.literal("Mob Variants"))
                .icon(() -> new ItemStack(Items.SPIDER_SPAWN_EGG))
                .entries((enabledFeatures, entries, operatorEnabled) -> {
                    entries.add(ModSpawnEggs.FORGOTTEN_SPAWN_EGG);
                    entries.add(ModSpawnEggs.UNDEAD_WARRIOR_SPAWN_EGG);
                    entries.add(ModSpawnEggs.ARMORED_SPIDER_SPAWN_EGG);
                    entries.add(ModSpawnEggs.CAVE_CREEPER_SPAWN_EGG);
                    entries.add(ModSpawnEggs.EXPLORER_SPAWN_EGG);
                    entries.add(ModSpawnEggs.TROPICAL_SPIDER_SPAWN_EGG);
                    entries.add(ModSpawnEggs.FROZEN_ZOMBIE_SPAWN_EGG);
                    entries.add(ModSpawnEggs.EXECUTIONER_SPAWN_EGG);
                    entries.add(ModSpawnEggs.NIGHTMARE_SPAWN_EGG);
                    entries.add(ModSpawnEggs.INFECTED_PIGLIN_SPAWN_EGG);
                    entries.add(ModSpawnEggs.INFECTED_PIGLIN_BRUTE_SPAWN_EGG);
                })
                .build();
    }
}
