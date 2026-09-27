package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ExecutionerEntityRenderer extends WitherSkeletonRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/skeleton/executioner.png");


    public ExecutionerEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public Identifier getTextureLocation(final SkeletonRenderState state) {
        return TEXTURE;
    }

    public SkeletonRenderState createRenderState() {
        SkeletonRenderState state = new SkeletonRenderState();
        state.scale *= 1.3F;

        return state;
    }
}
