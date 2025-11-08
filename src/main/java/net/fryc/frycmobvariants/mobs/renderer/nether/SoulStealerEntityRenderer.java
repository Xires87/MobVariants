package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.nether.SoulStealerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.SkeletonOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SoulStealerEntityRenderer extends SkeletonEntityRenderer<SoulStealerEntity> {
    private static final Identifier TEXTURE =
            Identifier.of(MobVariants.MOD_ID, "textures/entity/skeleton/soul_stealer.png");
    private static final Identifier OVERLAY =
            Identifier.of(MobVariants.MOD_ID, "textures/entity/skeleton/soul_stealer_overlay.png");

    public SoulStealerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.STRAY, EntityModelLayers.STRAY_INNER_ARMOR, EntityModelLayers.STRAY_OUTER_ARMOR);
        this.addFeature(new SkeletonOverlayFeatureRenderer(this, context.getModelLoader(), EntityModelLayers.STRAY_OUTER, OVERLAY));
        //this.addFeature(new SoulStealerOverlayFeatureRenderer(this, context.getModelLoader()));
    }

    public Identifier getTexture(SoulStealerEntity soulStealer) {
        return TEXTURE;
    }
}
