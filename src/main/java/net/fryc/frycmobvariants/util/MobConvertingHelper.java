package net.fryc.frycmobvariants.util;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.fryc.frycmobvariants.mobs.biome.ToxicSlimeEntity;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.ExecutionerEntity;
import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.fryc.frycmobvariants.mobs.nether.SoulStealerEntity;
import net.fryc.frycmobvariants.tags.ModBiomeTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import oshi.util.tuples.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MobConvertingHelper {

    public static void tryToConvertZombie(ZombieEntity zombie, Random random){
        if(zombie.getClass() == ZombieEntity.class){
            int i = (int)zombie.getY();
            if(zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.FROZEN_ZOMBIE_SPAWN_BIOMES) && isAtProperYLevel(i, zombie)){
                if(shouldConvertToVariant(random, MobVariants.config.zombieToFrozenZombieConvertChance)) zombie.convertTo(ModMobs.FROZEN_ZOMBIE, true);
            }
            else if(zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.BLOATED_CORPSE_SPAWN_BIOMES) && isAtProperYLevel(i, zombie)){
                if(shouldConvertToVariant(random, MobVariants.config.zombieToBloatedCorpseConvertChance)) zombie.convertTo(ModMobs.BLOATED_CORPSE, true);
            }
            else if(zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.EXPLORER_SPAWN_BIOMES) && isAtProperYLevel(i, zombie)){
                if(shouldConvertToVariant(random, MobVariants.config.zombieToExplorerConvertChance)) zombie.convertTo(ModMobs.EXPLORER, true);
            }
            else if(shouldConvertToCaveVariant(random, i, MobVariants.config.fixedChanceToConvertZombieUnderSelectedYLevel, MobVariants.config.zombieToForgottenConvertLevelY)) {
                zombie.convertTo(ModMobs.FORGOTTEN, true);
            }
        }
        else if(zombie.getClass() == ZombifiedPiglinEntity.class){
            if(shouldConvertToVariant(random, MobVariants.config.zombifiedPiglinConvertChance)){
                zombie.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
                zombie.convertTo(ModMobs.ZOMBIFIED_PIGLIN_BRUTE, true);
            }
        }
    }

    public static void tryToConvertSkeleton(AbstractSkeletonEntity skeleton, Random random){
        if(skeleton.getClass() == SkeletonEntity.class){
            if(!skeleton.getWorld().getDimension().ultrawarm()){
                int i = (int)skeleton.getY();
                if(skeleton.getWorld().getBiome(skeleton.getBlockPos()).isIn(ModBiomeTags.CORSAIR_SPAWN_BIOMES) && isAtProperYLevel(i, skeleton)){
                    if(shouldConvertToVariant(random, MobVariants.config.skeletonToCorsairConvertChance)){
                        skeleton.equipStack(EquipmentSlot.MAINHAND, CorsairEntity.getCorsairSword());
                        skeleton.convertTo(ModMobs.CORSAIR, true);
                    }
                }
                else if(shouldConvertToCaveVariant(random, i, MobVariants.config.fixedChanceToConvertSkeletonUnderSelectedYLevel, MobVariants.config.skeletonToUndeadWarriorConvertLevelY)){
                    if(skeleton.getMainHandStack().hasEnchantments() && MobVariants.config.undeadWarriorAttributes.alwaysKeepEnchantedBow){
                        skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                    }
                    else{
                        skeleton.equipStack(EquipmentSlot.MAINHAND, UndeadWarriorEntity.getUndeadWarriorWeapon());
                        UndeadWarriorEntity uw = skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                        if(uw != null){
                            if(!(uw.getMainHandStack().getItem() instanceof RangedWeaponItem)){
                                uw.tippedArrowsAmount = -1;
                            }
                        }
                    }
                }
            }
            else {
                if(shouldConvertToVariant(random, MobVariants.config.skeletonToSoulStealerConvertChance)){
                    skeleton.equipStack(EquipmentSlot.MAINHAND, SoulStealerEntity.getSoulsStealerWeapon());
                    skeleton.convertTo(ModMobs.SOUL_STEALER, true);
                }
            }
        }
        else if(skeleton.getClass() == WitherSkeletonEntity.class){
            if(shouldConvertToVariant(random, MobVariants.config.witherSkeletonConvertChance)){
                ExecutionerEntity.initExecutionerEquipment(skeleton);
                skeleton.convertTo(ModMobs.EXECUTIONER, true);
            }
        }
    }

    public static void tryToConvertSpider(SpiderEntity spider, Random random){
        if(spider.getClass() == SpiderEntity.class){
            int i = (int)spider.getY();
            if(spider.getWorld().getBiome(spider.getBlockPos()).isIn(ModBiomeTags.TROPICAL_SPIDER_SPAWN_BIOMES) && isAtProperYLevel(i, spider)){
                if(shouldConvertToVariant(random, MobVariants.config.spiderToTropicalSpiderConvertChance)) spider.convertTo(ModMobs.TROPICAL_SPIDER, false);
            }
            else if(shouldConvertToCaveVariant(random, i, MobVariants.config.fixedChanceToConvertSpiderUnderSelectedYLevel, MobVariants.config.spiderToArmoredSpiderConvertLevelY)){
                spider.convertTo(ModMobs.ARMORED_SPIDER, false);
            }
        }
    }

    public static void tryToConvertSlime(SlimeEntity slime, Random random){
        int size = slime.getSize();
        if(slime.getClass() == SlimeEntity.class){
            int i = (int)slime.getY();
            if(slime.getWorld().getBiome(slime.getBlockPos()).isIn(ModBiomeTags.TOXIC_SLIME_SPAWN_BIOMES) && isAtProperYLevel(i, slime)){
                if(shouldConvertToVariant(random, MobVariants.config.slimeToToxicSlimeConvertChance)){
                    ToxicSlimeEntity toxicSlime = slime.convertTo(ModMobs.TOXIC_SLIME, false);
                    if(toxicSlime != null){
                        toxicSlime.setSize(size, true);
                    }
                }
            }
        }
        else if(slime.getClass() == MagmaCubeEntity.class){
            if(shouldConvertToVariant(random, MobVariants.config.magmaCubeToLavaSlimeConvertChance)){
                LavaSlimeEntity lavaSlime = slime.convertTo(ModMobs.LAVA_SLIME, false);
                if(lavaSlime != null){
                    lavaSlime.setSize(size, true);
                }
            }
        }
    }

    public static void tryToConvertGhast(GhastEntity ghast, Random random){
        if (ghast.getClass() == GhastEntity.class) {
            if (shouldConvertToVariant(random, MobVariants.config.ghastConvertChance)) {
                ghast.convertTo(ModMobs.NIGHTMARE, false);
            }
        }
    }

    public static void tryToConvertCreeper(CreeperEntity creeper, Random random){
        if(creeper.getClass() == CreeperEntity.class){
            int i = (int)creeper.getY();
            if(shouldConvertToCaveVariant(random, i, MobVariants.config.fixedChanceToConvertCreeperUnderSelectedYLevel, MobVariants.config.creeperToCaveCreeperConvertLevelY)){
                creeper.convertTo(ModMobs.CAVE_CREEPER, false);
            }
        }
    }

    public static void tryToConvertPiglin(AbstractPiglinEntity piglin, Random random){
        if(piglin.getClass() == PiglinEntity.class){
            if(shouldConvertToVariant(random, MobVariants.config.piglinConvertChance)){
                piglin.convertTo(ModMobs.INFECTED_PIGLIN, true);
            }
        }
        else if(piglin.getClass() == PiglinBruteEntity.class){
            if(shouldConvertToVariant(random, MobVariants.config.piglinBruteConvertChance)){
                piglin.convertTo(ModMobs.INFECTED_PIGLIN_BRUTE, true);
            }
        }
    }

    public static boolean isAtProperYLevel(int y, LivingEntity mob){
        return y > 48 || mob.getWorld().getBiome(mob.getBlockPos()).isIn(ModBiomeTags.UNDERGROUND_BIOMES);
    }
    
    public static boolean shouldConvertToVariant(Random random, int chance){
        return random.nextInt(0, 100) < chance;
    }

    public static boolean shouldConvertToCaveVariant(Random random, int y, int fixedChance, int convertLevelY) {
        if(y < convertLevelY){
            if(fixedChance > -1){
                return shouldConvertToVariant(random, fixedChance);
            }
            else return random.nextInt(y, 100 + y) < convertLevelY;
        }

        return false;
    }

    public static ItemStack getRandomItemStack(Map<Item, Pair<Float, Float>> map) {
        float chance = ThreadLocalRandom.current().nextFloat();
        Optional<Map.Entry<Item, Pair<Float, Float>>> optional = map.entrySet().stream().filter(entry -> {
            return chance >= entry.getValue().getA() && chance < entry.getValue().getB();
        }).findAny();

        return optional.isPresent() ? new ItemStack(optional.get().getKey()) : ItemStack.EMPTY;
    }
}
