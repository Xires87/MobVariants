package net.fryc.frycmobvariants.mobs.renderer.biome;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryc.frycmobvariants.MobVariants;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BloatedCorpseEntityRenderer extends ZombieEntityRenderer {
    private static final Identifier TEXTURE =
            Identifier.of(MobVariants.MOD_ID, "textures/entity/zombie/bloated_corpse.png");

    public BloatedCorpseEntityRenderer(EntityRendererFactory.Context context) {
        super(context,EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }



    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }
}
