package net.fryc.frycmobvariants.mobs.renderer.cave;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ForgottenEntityRenderer extends ZombieRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/zombie/forgotten.png");
    private static final Identifier BABY_TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/zombie/forgotten_baby.png");


    public ForgottenEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    public Identifier getTextureLocation(ZombieRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }
}
