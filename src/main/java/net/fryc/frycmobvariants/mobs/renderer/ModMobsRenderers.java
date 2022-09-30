package net.fryc.frycmobvariants.mobs.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fryc.frycmobvariants.mobs.ModMobs;

public class ModMobsRenderers {

    public static void registerMobRenderers(){
        EntityRendererRegistry.register(ModMobs.FORGOTTEN, (context) -> {
            return new ForgottenEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.UNDEAD_WARRIOR, (context) -> {
            return new UndeadWarriorEntityRenderer(context);
        });

        EntityRendererRegistry.register(ModMobs.ARMORED_SPIDER, (context) -> {
            return new ArmoredSpiderEntityRenderer(context);
        });

    }
}
