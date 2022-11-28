package net.fryc.frycmobvariants.mobs.eggs;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSpawnEggs {
    //cave variants
    public static final Item FORGOTTEN_SPAWN_EGG = new SpawnEggItem(ModMobs.FORGOTTEN, 3232308, 5651507, new Item.Settings().group(ItemGroup.MISC));
    public static final Item UNDEAD_WARRIOR_SPAWN_EGG = new SpawnEggItem(ModMobs.UNDEAD_WARRIOR, 6387319, 4802889, new Item.Settings().group(ItemGroup.MISC));
    public static final Item ARMORED_SPIDER_SPAWN_EGG = new SpawnEggItem(ModMobs.ARMORED_SPIDER, 1315860, 11013646, new Item.Settings().group(ItemGroup.MISC));
    public static final Item CAVE_CREEPER_SPAWN_EGG = new SpawnEggItem(ModMobs.CAVE_CREEPER, 1315860, 3232308, new Item.Settings().group(ItemGroup.MISC));

    //biome variants
    public static final Item EXPLORER_SPAWN_EGG = new SpawnEggItem(ModMobs.EXPLORER, 3232308, 894731, new Item.Settings().group(ItemGroup.MISC));
    public static final Item TROPICAL_SPIDER_SPAWN_EGG = new SpawnEggItem(ModMobs.TROPICAL_SPIDER, 894731, 11013646, new Item.Settings().group(ItemGroup.MISC));

    //nether variants
    public static final Item EXECUTIONER_SPAWN_EGG = new SpawnEggItem(ModMobs.EXECUTIONER, 1315860, 4673845, new Item.Settings().group(ItemGroup.MISC));
    public static final Item NIGHTMARE_SPAWN_EGG = new SpawnEggItem(ModMobs.NIGHTMARE, 16382451, 12369044, new Item.Settings().group(ItemGroup.MISC));
    public static void registerSpawnEggs(){
        //cave variants
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "forgotten_spawn_egg"), FORGOTTEN_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "undead_warrior_spawn_egg"), UNDEAD_WARRIOR_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "armored_spider_spawn_egg"), ARMORED_SPIDER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "cave_creeper_spawn_egg"), CAVE_CREEPER_SPAWN_EGG);

        //biome variants
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "explorer_spawn_egg"), EXPLORER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "tropical_spider_spawn_egg"), TROPICAL_SPIDER_SPAWN_EGG);

        //nether variants
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "executioner_spawn_egg"), EXECUTIONER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MobVariants.MOD_ID, "nightmare_spawn_egg"), NIGHTMARE_SPAWN_EGG);
    }
}
