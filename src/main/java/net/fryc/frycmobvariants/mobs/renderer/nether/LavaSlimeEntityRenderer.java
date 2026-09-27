package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.nether.LavaSlimeEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.entity.AbstractCubeMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class LavaSlimeEntityRenderer extends AbstractCubeMobRenderer<LavaSlimeEntity, SlimeRenderState, SlimeModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/slime/lava_slime.png");


    public LavaSlimeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)));
        this.addLayer(new SlimeOuterLayer(this, context.getModelSet()));
    }


    public Identifier getTextureLocation(SlimeRenderState state) {
        return TEXTURE;
    }

    @Override
    public SlimeRenderState createRenderState() {
        return null;
    }
}
