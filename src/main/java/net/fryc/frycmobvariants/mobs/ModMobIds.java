package net.fryc.frycmobvariants.mobs;

import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class ModMobIds {

    public static ResourceKey<EntityType<?>> FORGOTTEN = create("forgotten");
    public static ResourceKey<EntityType<?>> UNDEAD_WARRIOR = create("undead_warrior");
    public static ResourceKey<EntityType<?>> ARMORED_SPIDER = create("armored_spider");
    public static ResourceKey<EntityType<?>> CAVE_CREEPER = create("cave_creeper");

    public static ResourceKey<EntityType<?>> EXPLORER = create("explorer");
    public static ResourceKey<EntityType<?>> BLOATED_CORPSE = create("bloated_corpse");
    public static ResourceKey<EntityType<?>> FROZEN_ZOMBIE = create("frozen_zombie");
    public static ResourceKey<EntityType<?>> TROPICAL_SPIDER = create("tropical_spider");
    public static ResourceKey<EntityType<?>> CORSAIR = create("corsair");
    public static ResourceKey<EntityType<?>> TOXIC_SLIME = create("toxic_slime");

    public static ResourceKey<EntityType<?>> EXECUTIONER = create("executioner");
    public static ResourceKey<EntityType<?>> NIGHTMARE = create("nightmare");
    public static ResourceKey<EntityType<?>> INFECTED_PIGLIN = create("infected_piglin");
    public static ResourceKey<EntityType<?>> INFECTED_PIGLIN_BRUTE = create("infected_piglin_brute");
    public static ResourceKey<EntityType<?>> ZOMBIFIED_PIGLIN_BRUTE = create("zombified_piglin_brute");
    public static ResourceKey<EntityType<?>> SOUL_STEALER = create("soul_stealer");
    public static ResourceKey<EntityType<?>> LAVA_SLIME = create("lava_slime");


    private static ResourceKey<EntityType<?>> create(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, name));
    }
}
