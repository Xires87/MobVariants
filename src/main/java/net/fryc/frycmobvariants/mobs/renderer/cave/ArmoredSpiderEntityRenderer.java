package net.fryc.frycmobvariants.mobs.renderer.cave;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.cave.ArmoredSpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ArmoredSpiderEntityRenderer extends SpiderRenderer<ArmoredSpiderEntity> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/spider/armored_spider.png");

    public ArmoredSpiderEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public Identifier getTextureLocation(final LivingEntityRenderState state) {
        return TEXTURE;
    }
}
