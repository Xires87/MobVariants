package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.world.World;

public class InfectedPiglinEntity extends PiglinEntity {

    public InfectedPiglinEntity(EntityType<? extends PiglinEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
    }

}
