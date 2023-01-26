package net.fryc.frycmobvariants.mobs.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.renderer.biome.ExplorerEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.biome.FrozenZombieEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.biome.TropicalSpiderEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.ArmoredSpiderEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.CaveCreeperEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.ForgottenEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.UndeadWarriorEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.ExecutionerEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.InfectedPiglinBruteEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.InfectedPiglinEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.NightmareEntityRenderer;

public class ModMobsRenderers {

    public static void registerMobRenderers(){
        //cave variants
        EntityRendererRegistry.register(ModMobs.FORGOTTEN, (context) -> {
            return new ForgottenEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.UNDEAD_WARRIOR, (context) -> {
            return new UndeadWarriorEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.ARMORED_SPIDER, (context) -> {
            return new ArmoredSpiderEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.CAVE_CREEPER, (context) -> {
            return new CaveCreeperEntityRenderer(context);
        });

        //biome variants
        EntityRendererRegistry.register(ModMobs.EXPLORER, (context) -> {
            return new ExplorerEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.FROZEN_ZOMBIE, (context) -> {
            return new FrozenZombieEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.TROPICAL_SPIDER, (context) -> {
            return new TropicalSpiderEntityRenderer(context);
        });


        //nether variants
        EntityRendererRegistry.register(ModMobs.EXECUTIONER, (context) -> {
            return new ExecutionerEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.NIGHTMARE, (context) -> {
            return new NightmareEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.INFECTED_PIGLIN, (context) -> {
            return new InfectedPiglinEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.INFECTED_PIGLIN_BRUTE, (context) -> {
            return new InfectedPiglinBruteEntityRenderer(context);
        });
    }
}
