package net.fryc.frycmobvariants.mobs.renderer.nether;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class LavaSlimeEntityRenderer extends AbstractCubeMobRenderer<LavaSlimeEntity, SlimeRenderState, SlimeModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/slime/lava_slime.png");


    public LavaSlimeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)));
        this.addLayer(new LavaSlimeOuterLayer(this, context.getModelSet()));
    }


    public Identifier getTextureLocation(SlimeRenderState state) {
        return TEXTURE;
    }

    @Override
    public SlimeRenderState createRenderState() {
        return new SlimeRenderState();
    }


    @Environment(EnvType.CLIENT)
    public static class LavaSlimeOuterLayer extends SlimeOuterLayer {

        private final SlimeModel model;

        public LavaSlimeOuterLayer(RenderLayerParent<SlimeRenderState, SlimeModel> renderer, EntityModelSet modelSet) {
            super(renderer, modelSet);
            this.model = new SlimeModel(modelSet.bakeLayer(ModelLayers.SLIME_OUTER));
        }

        public void submit(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SlimeRenderState state, final float yRot, final float xRot) {
            boolean appearsGlowingWithInvisibility = state.appearsGlowing() && state.isInvisible;
            if (!state.isInvisible || appearsGlowingWithInvisibility) {
                int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
                if (appearsGlowingWithInvisibility) {
                    submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.outline(LavaSlimeEntityRenderer.TEXTURE), lightCoords, overlayCoords, state.outlineColor);
                } else {
                    submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.entityTranslucent(LavaSlimeEntityRenderer.TEXTURE), lightCoords, overlayCoords, state.outlineColor);
                }

            }
        }
    }
}
