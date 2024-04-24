package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.world.World;

public class InfectedPiglinBruteEntity extends PiglinBruteEntity {

    public InfectedPiglinBruteEntity(EntityType<? extends PiglinBruteEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
    }

}
