package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SoulStealerEntityRenderer extends SkeletonEntityRenderer {
    private static final Identifier TEXTURE =
            new Identifier(MobVariants.MOD_ID, "textures/entity/skeleton/soul_stealer.png");

    public SoulStealerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.STRAY, EntityModelLayers.STRAY_INNER_ARMOR, EntityModelLayers.STRAY_OUTER_ARMOR);
        this.addFeature(new SoulStealerOverlayFeatureRenderer(this, context.getModelLoader()));
        //this.addFeature(new SoulStealerOverlayFeatureRenderer(this, context.getModelLoader()));
    }

    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }
}
