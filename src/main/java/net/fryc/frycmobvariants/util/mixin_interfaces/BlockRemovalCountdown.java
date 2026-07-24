package net.fryc.frycmobvariants.util.mixin_interfaces;

import net.minecraft.util.math.BlockPos;

public interface BlockRemovalCountdown {

    //for ServerWorld

    void startLavaRemovalCountdown(BlockPos lavaPos, int ticksToRemove);
}
