package net.fryc.frycmobvariants.util.mixin_interfaces;

import net.minecraft.core.BlockPos;

public interface BlockRemovalCountdown {

    //for ServerWorld

    void startLavaRemovalCountdown(BlockPos lavaPos, int ticksToRemove);
}
