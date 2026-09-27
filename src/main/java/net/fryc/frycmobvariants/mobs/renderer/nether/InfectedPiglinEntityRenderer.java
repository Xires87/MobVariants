package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class InfectedPiglinEntityRenderer extends PiglinRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/piglin/infected_piglin.png");
    private static final Identifier BABY_TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/piglin/infected_piglin_baby.png");


    public InfectedPiglinEntityRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.PIGLIN, ModelLayers.PIGLIN_BABY, ModelLayers.PIGLIN_ARMOR, ModelLayers.PIGLIN_BABY_ARMOR);
    }


    public Identifier getTextureLocation(PiglinRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }
}
