package net.fryc.frycmobvariants.mobs.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.renderer.biome.*;
import net.fryc.frycmobvariants.mobs.renderer.cave.ArmoredSpiderEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.CaveCreeperEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.ForgottenEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.UndeadWarriorEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.*;

@Environment(EnvType.CLIENT)
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
        EntityRendererRegistry.register(ModMobs.CORSAIR, (context) -> {
            return new CorsairEntityRenderer(context);
        });
        EntityRendererRegistry.register(ModMobs.TOXIC_SLIME, (context) -> {
            return new ToxicSlimeEntityRenderer(context);
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

        EntityRendererRegistry.register(ModMobs.SOUL_STEALER, (context) -> {
            return new SoulStealerEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.LAVA_SLIME, (context) -> {
            return new LavaSlimeEntityRenderer(context);
        });
    }
}
