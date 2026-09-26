package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShipwreckPieces.ShipwreckPiece.class)
abstract class ShipwreckGeneratorMixin {

    //spawns corsairs on shipwrecks
    @Inject(method = "handleDataMarker(Ljava/lang/String;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)V", at = @At("TAIL"))
    private void spawnCorsairs(String markerId, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB, CallbackInfo ci) {
        BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        while(!level.getBlockState(cPos).is(Blocks.WATER) && !level.getBlockState(cPos).is(Blocks.AIR)) cPos.move(Direction.UP, 2);
        CorsairEntity corsairEntity = (CorsairEntity) ModMobs.CORSAIR.create(level.getLevel(), EntitySpawnReason.STRUCTURE);
        if (corsairEntity != null) {
            corsairEntity.setPersistenceRequired();
            corsairEntity.refreshDimensions();
            corsairEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(cPos), EntitySpawnReason.STRUCTURE, null);
            level.addFreshEntityWithPassengers(corsairEntity);
            if (cPos.getY() > level.getSeaLevel()) {
                level.setBlockAndUpdate(cPos, Blocks.AIR.defaultBlockState());
            } else {
                level.setBlockAndUpdate(cPos, Blocks.WATER.defaultBlockState());
            }
        }
    }
}
