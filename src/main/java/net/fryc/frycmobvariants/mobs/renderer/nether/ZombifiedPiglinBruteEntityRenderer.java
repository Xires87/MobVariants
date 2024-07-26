package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.models.ZombifiedPiglinBruteEntityModel;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

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
