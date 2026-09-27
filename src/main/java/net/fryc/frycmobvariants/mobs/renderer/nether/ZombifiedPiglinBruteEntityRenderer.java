package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombifiedPiglinRenderer;
import net.minecraft.client.renderer.entity.state.ZombifiedPiglinRenderState;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class ZombifiedPiglinBruteEntityRenderer extends ZombifiedPiglinRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/zombie/zombified_piglin_brute.png");


    public ZombifiedPiglinBruteEntityRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.ZOMBIFIED_PIGLIN, ModelLayers.ZOMBIFIED_PIGLIN, ModelLayers.ZOMBIFIED_PIGLIN_ARMOR, ModelLayers.ZOMBIFIED_PIGLIN_ARMOR);
    }


    public Identifier getTextureLocation(ZombifiedPiglinRenderState state) {
        return TEXTURE;
    }
}

/*
@Environment(EnvType.CLIENT)
public class ZombifiedPiglinBruteEntityRenderer extends BipedEntityRenderer<MobEntity, ZombifiedPiglinBruteEntityModel> {

    private static final Identifier TEXTURE =
            Identifier.of(MobVariants.MOD_ID, "textures/entity/zombie/zombified_piglin_brute.png");

    public ZombifiedPiglinBruteEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer mainLayer, EntityModelLayer innerArmorLayer, EntityModelLayer outerArmorLayer, boolean zombie) {
        super(ctx, getPiglinModel(ctx.getModelLoader(), mainLayer, zombie), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(ctx.getPart(innerArmorLayer)), new ArmorEntityModel(ctx.getPart(outerArmorLayer)), ctx.getModelManager()));
    }

    public ZombifiedPiglinBruteEntityRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIFIED_PIGLIN, EntityModelLayers.ZOMBIFIED_PIGLIN_INNER_ARMOR, EntityModelLayers.ZOMBIFIED_PIGLIN_OUTER_ARMOR, true);
    }

    public Identifier getTexture(MobEntity mobEntity) {
        return TEXTURE;
    }

    private static ZombifiedPiglinBruteEntityModel getPiglinModel(EntityModelLoader modelLoader, EntityModelLayer layer, boolean zombie) {
        ZombifiedPiglinBruteEntityModel piglinEntityModel = new ZombifiedPiglinBruteEntityModel(modelLoader.getModelPart(layer));
        if (zombie) {
            piglinEntityModel.rightEar.visible = false;
        }

        return piglinEntityModel;
    }

}

 */
