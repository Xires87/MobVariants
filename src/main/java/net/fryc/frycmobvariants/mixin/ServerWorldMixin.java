package net.fryc.frycmobvariants.mixin;

import com.google.common.collect.Maps;
import net.fryc.frycmobvariants.util.BlockRemovalCountdown;
import net.minecraft.block.Blocks;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin extends World implements StructureWorldAccess, BlockRemovalCountdown {

    HashMap<BlockPos, Integer> lavaSetByLavaSlimesPositions = Maps.newHashMap();

    private int serverWorldTicks = 0;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("TAIL"))
    private void lavaLeftByLavaSlimeRemovalCountdown(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        ++serverWorldTicks;
        if(!lavaSetByLavaSlimesPositions.isEmpty()){
            Iterator<Map.Entry<BlockPos, Integer>> iterator = lavaSetByLavaSlimesPositions.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<BlockPos, Integer> entry = iterator.next();
                if(entry.getValue() <= serverWorldTicks){
                    removeLavaLeftByLavaSlime(entry.getKey());
                    iterator.remove();
                }
            }
        }
        else serverWorldTicks = 0;
    }

    private void removeLavaLeftByLavaSlime(BlockPos pos){
        ServerWorld dys = ((ServerWorld)(Object)this);
        if(dys.getBlockState(pos).getBlock() == Blocks.LAVA){
            dys.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
        }

    }

    public void startLavaRemovalCountdown(BlockPos lavaPos, int ticksToRemove){
        lavaSetByLavaSlimesPositions.put(lavaPos, serverWorldTicks + ticksToRemove);
    }
}
