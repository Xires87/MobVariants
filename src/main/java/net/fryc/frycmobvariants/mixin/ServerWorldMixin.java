package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.util.mixin_interfaces.BlockRemovalCountdown;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntityGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
abstract class ServerWorldMixin extends Level implements WorldGenLevel, ServerEntityGetter, BlockRemovalCountdown {

    @Unique
    HashMap<BlockPos, Integer> lavaSetByLavaSlimesPositions = new HashMap<>();

    @Unique
    private int serverWorldTicks = 0;

    protected ServerWorldMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }


    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("TAIL"))
    private void lavaLeftByLavaSlimeRemovalCountdown(BooleanSupplier haveTime, CallbackInfo info) {
        ++this.serverWorldTicks;
        if(!this.lavaSetByLavaSlimesPositions.isEmpty()){
            Iterator<Map.Entry<BlockPos, Integer>> iterator = this.lavaSetByLavaSlimesPositions.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<BlockPos, Integer> entry = iterator.next();
                if(entry.getValue() <= this.serverWorldTicks){
                    removeLavaLeftByLavaSlime(entry.getKey());
                    iterator.remove();
                }
            }
        }
        else this.serverWorldTicks = 0;
    }

    @Unique
    private void removeLavaLeftByLavaSlime(BlockPos pos){
        ServerLevel dys = ((ServerLevel)(Object)this);
        if(dys.getBlockState(pos).getBlock() == Blocks.LAVA){
            dys.setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
        }
    }

    public void startLavaRemovalCountdown(BlockPos lavaPos, int ticksToRemove){
        lavaSetByLavaSlimesPositions.put(lavaPos, this.serverWorldTicks + ticksToRemove);
    }
}
