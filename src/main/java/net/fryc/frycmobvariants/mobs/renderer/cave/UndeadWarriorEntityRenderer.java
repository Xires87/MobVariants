package net.fryc.frycmobvariants.mobs.renderer.cave;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class UndeadWarriorEntityRenderer extends SkeletonRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(MobVariants.MOD_ID, "textures/entity/skeleton/undead_warrior.png");

    public UndeadWarriorEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    public Identifier getTextureLocation(SkeletonRenderState state) {
        return TEXTURE;
    }
}
