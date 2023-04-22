package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.biome.CorsairEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShipwreckGenerator.Piece.class)
abstract class ShipwreckGeneratorMixin {

    //spawns corsairs on shipwrecks
    @Inject(method = "handleMetadata(Ljava/lang/String;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/random/Random;" +
            "Lnet/minecraft/util/math/BlockBox;)V", at = @At("TAIL"))
    private void spawnCorsairs(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox, CallbackInfo ci) {
        BlockPos cPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        if(!world.getBlockState(cPos).isOf(Blocks.WATER) && !world.getBlockState(cPos).isOf(Blocks.AIR)) cPos = new BlockPos(pos.getX(), pos.getY() + 5, pos.getZ());
        CorsairEntity corsairEntity = (CorsairEntity) ModMobs.CORSAIR.create(world.toServerWorld());
        if (corsairEntity != null) {
            corsairEntity.setPersistent();
            corsairEntity.refreshPositionAndAngles(cPos, 0.0F, 0.0F);
            corsairEntity.initialize(world, world.getLocalDifficulty(cPos), SpawnReason.STRUCTURE, (EntityData)null, (NbtCompound)null);
            world.spawnEntityAndPassengers(corsairEntity);
            if (cPos.getY() > world.getSeaLevel()) {
                world.setBlockState(cPos, Blocks.AIR.getDefaultState(), 2);
            } else {
                world.setBlockState(cPos, Blocks.WATER.getDefaultState(), 2);
            }
        }
    }
}
