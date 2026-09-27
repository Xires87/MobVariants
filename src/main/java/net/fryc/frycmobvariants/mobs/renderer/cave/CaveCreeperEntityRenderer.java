package net.fryc.frycmobvariants.mobs.renderer.cave;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class CaveCreeperEntityRenderer extends CreeperRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/creeper/cave_creeper.png");


    public CaveCreeperEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    public Identifier getTextureLocation(final CreeperRenderState state) {
        return TEXTURE;
    }
}
