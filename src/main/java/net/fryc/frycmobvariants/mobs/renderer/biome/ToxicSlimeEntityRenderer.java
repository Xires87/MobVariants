package net.fryc.frycmobvariants.mobs.renderer.biome;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ToxicSlimeEntityRenderer extends SlimeEntityRenderer {

    private static final Identifier TEXTURE = new Identifier(MobVariants.MOD_ID, "textures/entity/slime/toxic_slime.png");

    public ToxicSlimeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(SlimeEntity slimeEntity) {
        return TEXTURE;
    }


}
