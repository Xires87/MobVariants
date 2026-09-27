package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.nether.SoulStealerEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SoulStealerEntityRenderer extends AbstractSkeletonRenderer<SoulStealerEntity, SkeletonRenderState> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/skeleton/soul_stealer.png");
    private static final Identifier OVERLAY =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/skeleton/soul_stealer_overlay.png");


    public SoulStealerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.STRAY, ModelLayers.STRAY_ARMOR);
        this.addLayer(new SkeletonClothingLayer(this, context.getModelSet(), ModelLayers.STRAY_OUTER_LAYER, OVERLAY));
    }


    @Override
    public Identifier getTextureLocation(SkeletonRenderState state) {
        return TEXTURE;
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }
}
