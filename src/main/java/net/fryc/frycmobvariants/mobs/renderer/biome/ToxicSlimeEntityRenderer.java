package net.fryc.frycmobvariants.mobs.renderer.biome;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class ToxicSlimeEntityRenderer extends SlimeRenderer {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/slime/toxic_slime.png");


    public ToxicSlimeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    public Identifier getTextureLocation(SlimeRenderState state) {
        return TEXTURE;
    }
}
