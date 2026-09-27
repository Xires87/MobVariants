package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;

public class InfectedPiglinBruteEntity extends PiglinBrute {

    public InfectedPiglinBruteEntity(EntityType<? extends PiglinBrute> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
    }
}
