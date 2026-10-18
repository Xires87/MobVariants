package net.fryc.frycmobvariants.mobs.nether;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;

public class InfectedPiglinEntity extends Piglin {


    public InfectedPiglinEntity(EntityType<? extends AbstractPiglin> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
    }

    public static boolean checkInfectedPiglinSpawnRules(final EntityType<InfectedPiglinEntity> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }
}
