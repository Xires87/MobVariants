package net.fryc.frycmobvariants.mobs.renderer.nether;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LavaSlimeEntityRenderer extends SlimeEntityRenderer {

    private static final Identifier TEXTURE = Identifier.of(MobVariants.MOD_ID, "textures/entity/slime/lava_slime.png");

    public LavaSlimeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(SlimeEntity slimeEntity) {
        return TEXTURE;
    }
}
