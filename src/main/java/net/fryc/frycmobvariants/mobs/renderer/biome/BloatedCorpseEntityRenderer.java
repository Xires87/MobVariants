package net.fryc.frycmobvariants.mobs.renderer.biome;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class BloatedCorpseEntityRenderer extends ZombieRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/zombie/bloated_corpse.png");
    private static final Identifier BABY_TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/zombie/bloated_corpse_baby.png");


    public BloatedCorpseEntityRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }


    public Identifier getTextureLocation(ZombieRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }
}
