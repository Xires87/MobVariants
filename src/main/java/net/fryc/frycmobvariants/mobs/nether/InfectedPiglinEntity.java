package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;

public class InfectedPiglinEntity extends Piglin {


    public InfectedPiglinEntity(EntityType<? extends AbstractPiglin> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
    }
}
