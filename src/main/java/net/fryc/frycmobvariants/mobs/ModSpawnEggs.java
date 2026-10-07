package net.fryc.frycmobvariants.mobs;


import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;

import java.util.function.Function;

public class ModSpawnEggs {

    public static final ResourceKey<CreativeModeTab> MOB_VARIANTS_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "mob_variants_spawn_eggs")
    );


    //cave variants
    public static final ResourceKey<Item> FORGOTTEN_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.FORGOTTEN);
    public static final ResourceKey<Item> UNDEAD_WARRIOR_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.UNDEAD_WARRIOR);
    public static final ResourceKey<Item> ARMORED_SPIDER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.ARMORED_SPIDER);
    public static final ResourceKey<Item> CAVE_CREEPER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.CAVE_CREEPER);

    //biome variants
    public static final ResourceKey<Item> EXPLORER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.EXPLORER);
    public static final ResourceKey<Item> BLOATED_CORPSE_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.BLOATED_CORPSE);
    public static final ResourceKey<Item> FROZEN_ZOMBIE_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.FROZEN_ZOMBIE);
    public static final ResourceKey<Item> TROPICAL_SPIDER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.TROPICAL_SPIDER);
    public static final ResourceKey<Item> CORSAIR_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.CORSAIR);
    public static final ResourceKey<Item> TOXIC_SLIME_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.TOXIC_SLIME);

    //nether variants
    public static final ResourceKey<Item> EXECUTIONER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.EXECUTIONER);
    public static final ResourceKey<Item> NIGHTMARE_SPAWN_EGG_ID =createSpawnEgg(ModMobIds.NIGHTMARE);
    public static final ResourceKey<Item> INFECTED_PIGLIN_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.INFECTED_PIGLIN);
    public static final ResourceKey<Item> INFECTED_PIGLIN_BRUTE_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.INFECTED_PIGLIN_BRUTE);
    public static final ResourceKey<Item> ZOMBIFIED_PIGLIN_BRUTE_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.ZOMBIFIED_PIGLIN_BRUTE);
    public static final ResourceKey<Item> SOUL_STEALER_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.SOUL_STEALER);
    public static final ResourceKey<Item> LAVA_SLIME_SPAWN_EGG_ID = createSpawnEgg(ModMobIds.LAVA_SLIME);
    
    
    // ------------------------
    

    //cave variants
    public static final Item FORGOTTEN_SPAWN_EGG = registerSpawnEgg(FORGOTTEN_SPAWN_EGG_ID, ModMobs.FORGOTTEN);
    public static final Item UNDEAD_WARRIOR_SPAWN_EGG = registerSpawnEgg(UNDEAD_WARRIOR_SPAWN_EGG_ID, ModMobs.UNDEAD_WARRIOR);
    public static final Item ARMORED_SPIDER_SPAWN_EGG = registerSpawnEgg(ARMORED_SPIDER_SPAWN_EGG_ID, ModMobs.ARMORED_SPIDER);
    public static final Item CAVE_CREEPER_SPAWN_EGG = registerSpawnEgg(CAVE_CREEPER_SPAWN_EGG_ID, ModMobs.CAVE_CREEPER);

    //biome variants
    public static final Item EXPLORER_SPAWN_EGG = registerSpawnEgg(EXPLORER_SPAWN_EGG_ID, ModMobs.EXPLORER);
    public static final Item BLOATED_CORPSE_SPAWN_EGG = registerSpawnEgg(BLOATED_CORPSE_SPAWN_EGG_ID, ModMobs.BLOATED_CORPSE);
    public static final Item FROZEN_ZOMBIE_SPAWN_EGG = registerSpawnEgg(FROZEN_ZOMBIE_SPAWN_EGG_ID, ModMobs.FROZEN_ZOMBIE);
    public static final Item TROPICAL_SPIDER_SPAWN_EGG = registerSpawnEgg(TROPICAL_SPIDER_SPAWN_EGG_ID, ModMobs.TROPICAL_SPIDER);
    public static final Item CORSAIR_SPAWN_EGG = registerSpawnEgg(CORSAIR_SPAWN_EGG_ID, ModMobs.CORSAIR);
    public static final Item TOXIC_SLIME_SPAWN_EGG = registerSpawnEgg(TOXIC_SLIME_SPAWN_EGG_ID, ModMobs.TOXIC_SLIME);

    //nether variants
    public static final Item EXECUTIONER_SPAWN_EGG = registerSpawnEgg(EXECUTIONER_SPAWN_EGG_ID, ModMobs.EXECUTIONER);
    public static final Item NIGHTMARE_SPAWN_EGG = registerSpawnEgg(NIGHTMARE_SPAWN_EGG_ID, ModMobs.NIGHTMARE);
    public static final Item INFECTED_PIGLIN_SPAWN_EGG = registerSpawnEgg(INFECTED_PIGLIN_SPAWN_EGG_ID, ModMobs.INFECTED_PIGLIN);
    public static final Item INFECTED_PIGLIN_BRUTE_SPAWN_EGG = registerSpawnEgg(INFECTED_PIGLIN_BRUTE_SPAWN_EGG_ID, ModMobs.INFECTED_PIGLIN_BRUTE);
    public static final Item ZOMBIFIED_PIGLIN_BRUTE_SPAWN_EGG = registerSpawnEgg(ZOMBIFIED_PIGLIN_BRUTE_SPAWN_EGG_ID, ModMobs.ZOMBIFIED_PIGLIN_BRUTE);
    public static final Item SOUL_STEALER_SPAWN_EGG = registerSpawnEgg(SOUL_STEALER_SPAWN_EGG_ID, ModMobs.SOUL_STEALER);
    public static final Item LAVA_SLIME_SPAWN_EGG = registerSpawnEgg(LAVA_SLIME_SPAWN_EGG_ID, ModMobs.LAVA_SLIME);

    public static final CreativeModeTab MOB_VARIANTS_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(CAVE_CREEPER_SPAWN_EGG))
            .title(Component.translatable("creativeTab.frycmobvariants.mob_variants_spawn_eggs"))
            .displayItems((params, output) -> {
                output.accept(ModSpawnEggs.FORGOTTEN_SPAWN_EGG);
                output.accept(ModSpawnEggs.UNDEAD_WARRIOR_SPAWN_EGG);
                output.accept(ModSpawnEggs.ARMORED_SPIDER_SPAWN_EGG);
                output.accept(ModSpawnEggs.CAVE_CREEPER_SPAWN_EGG);
                output.accept(ModSpawnEggs.EXPLORER_SPAWN_EGG);
                output.accept(ModSpawnEggs.BLOATED_CORPSE_SPAWN_EGG);
                output.accept(ModSpawnEggs.TROPICAL_SPIDER_SPAWN_EGG);
                output.accept(ModSpawnEggs.FROZEN_ZOMBIE_SPAWN_EGG);
                output.accept(ModSpawnEggs.CORSAIR_SPAWN_EGG);
                output.accept(ModSpawnEggs.TOXIC_SLIME_SPAWN_EGG);
                output.accept(ModSpawnEggs.EXECUTIONER_SPAWN_EGG);
                output.accept(ModSpawnEggs.NIGHTMARE_SPAWN_EGG);
                output.accept(ModSpawnEggs.INFECTED_PIGLIN_SPAWN_EGG);
                output.accept(ModSpawnEggs.INFECTED_PIGLIN_BRUTE_SPAWN_EGG);
                output.accept(ModSpawnEggs.ZOMBIFIED_PIGLIN_BRUTE_SPAWN_EGG);
                output.accept(ModSpawnEggs.SOUL_STEALER_SPAWN_EGG);
                output.accept(ModSpawnEggs.LAVA_SLIME_SPAWN_EGG);
            })
            .build();



    private static Item registerSpawnEgg(ResourceKey<Item> id, EntityType<?> type) {
        return registerItem(id, SpawnEggItem::new, (new Item.Properties()).spawnEgg(type));
    }

    private static Item registerItem(ResourceKey<Item> id, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        Item item = (Item)itemFactory.apply(properties.setId(id));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return (Item) Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    private static ResourceKey<Item> createSpawnEgg(ResourceKey<EntityType<?>> entity) {
        return entity.dependent(Registries.ITEM, "_spawn_egg");
    }


    public static void registerSpawnEggs(){
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOB_VARIANTS_CREATIVE_TAB_KEY, MOB_VARIANTS_CREATIVE_TAB);
    }
}
