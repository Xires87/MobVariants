package net.fryc.frycmobvariants.mobs.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.renderer.biome.*;
import net.fryc.frycmobvariants.mobs.renderer.cave.ArmoredSpiderEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.CaveCreeperEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.ForgottenEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.cave.UndeadWarriorEntityRenderer;
import net.fryc.frycmobvariants.mobs.renderer.nether.*;
import net.minecraft.client.renderer.entity.EntityRenderers;

@Environment(EnvType.CLIENT)
public class ModMobsRenderers {

    public static void registerMobRenderers(){
        //cave variants
        EntityRenderers.register(ModMobs.FORGOTTEN, ForgottenEntityRenderer::new);
        EntityRenderers.register(ModMobs.UNDEAD_WARRIOR, UndeadWarriorEntityRenderer::new);
        EntityRenderers.register(ModMobs.ARMORED_SPIDER, ArmoredSpiderEntityRenderer::new);
        EntityRenderers.register(ModMobs.CAVE_CREEPER, CaveCreeperEntityRenderer::new);

        //biome variants
        EntityRenderers.register(ModMobs.EXPLORER, ExplorerEntityRenderer::new);
        EntityRenderers.register(ModMobs.BLOATED_CORPSE, BloatedCorpseEntityRenderer::new);
        EntityRenderers.register(ModMobs.FROZEN_ZOMBIE, FrozenZombieEntityRenderer::new);
        EntityRenderers.register(ModMobs.TROPICAL_SPIDER, TropicalSpiderEntityRenderer::new);
        EntityRenderers.register(ModMobs.CORSAIR, CorsairEntityRenderer::new);
        EntityRenderers.register(ModMobs.TOXIC_SLIME, ToxicSlimeEntityRenderer::new);

        //nether variants
        EntityRenderers.register(ModMobs.EXECUTIONER, ExecutionerEntityRenderer::new);
        EntityRenderers.register(ModMobs.NIGHTMARE, NightmareEntityRenderer::new);
        EntityRenderers.register(ModMobs.INFECTED_PIGLIN, InfectedPiglinEntityRenderer::new);
        EntityRenderers.register(ModMobs.INFECTED_PIGLIN_BRUTE, InfectedPiglinBruteEntityRenderer::new);
        EntityRenderers.register(ModMobs.ZOMBIFIED_PIGLIN_BRUTE, ZombifiedPiglinBruteEntityRenderer::new);
        EntityRenderers.register(ModMobs.SOUL_STEALER, SoulStealerEntityRenderer::new);
        EntityRenderers.register(ModMobs.LAVA_SLIME, LavaSlimeEntityRenderer::new);
    }
}
