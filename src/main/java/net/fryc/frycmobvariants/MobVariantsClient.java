package net.fryc.frycmobvariants;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycmobvariants.mobs.renderer.ModMobsRenderers;

public class MobVariantsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMobsRenderers.registerMobRenderers();

    }
}
