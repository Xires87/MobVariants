package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.biome.FrozenZombieEntity;
import net.fryc.frycmobvariants.tags.ModBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ZombieEntity.class)
abstract class ZombieConvertMixin extends HostileEntity {
    boolean canConvert = true;
    Random random = new Random();

    private int inPowderSnowTime = 0;
    private int convertToFrozenZombieTime = 300;

    protected ZombieConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts zombie to forgotten or explorer or frozen zombie
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToZombieVariant(CallbackInfo info) {
        if(!world.isClient){
            ZombieEntity zombie = ((ZombieEntity)(Object)this);
            if(zombie.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                if(zombie.getClass() == ZombieEntity.class){
                    int i = (int)zombie.getY();
                    if(zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.SNOWY_BIOMES) && i > 48){
                        if(random.nextInt(0, 100) <= MobVariants.config.zombieToFrozenZombieConvertChance) zombie.convertTo(ModMobs.FROZEN_ZOMBIE, true);
                    }
                    else if((zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.EXPLORER_SPAWN_BIOMES) && i > 38) || zombie.getWorld().getBiome(zombie.getBlockPos()).matchesKey(BiomeKeys.LUSH_CAVES)){
                        if(random.nextInt(0, 100) <= MobVariants.config.zombieToExplorerConvertChance) zombie.convertTo(ModMobs.EXPLORER, true);
                    }
                    else {
                        if(i < MobVariants.config.zombieToForgottenConvertLevelY){
                            boolean bl = false;
                            if(MobVariants.config.fixedChanceToConvertZombieUnderSelectedYLevel > -1){
                                if(random.nextInt(0,100) <= MobVariants.config.fixedChanceToConvertZombieUnderSelectedYLevel){
                                    bl = true;
                                }
                            }
                            else if(random.nextInt(i, 100 + i) < MobVariants.config.zombieToForgottenConvertLevelY){
                                bl = true;
                            }

                            if(bl){
                                zombie.convertTo(ModMobs.FORGOTTEN, true);
                            }
                        }
                    }
                    canConvert = false;
                }
            }

            if(zombie.isAlive() && !zombie.isAiDisabled() && !zombie.canFreeze() && zombie.getClass() != FrozenZombieEntity.class){
                if (zombie.inPowderSnow) {
                    if (inPowderSnowTime >= 140) {
                        --convertToFrozenZombieTime;
                        if (convertToFrozenZombieTime < 0) {
                            zombie.playSound(SoundEvents.ENTITY_PLAYER_HURT_FREEZE, 1.0F, 0.4F);
                            zombie.convertTo(ModMobs.FROZEN_ZOMBIE, true);
                        }
                    } else {
                        ++inPowderSnowTime;
                        if (inPowderSnowTime >= 140) {
                            convertToFrozenZombieTime = 300;
                        }
                    }
                } else {
                    inPowderSnowTime = -1;
                }
            }

        }
    }

    //reading canConvert from Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/ZombieEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("MobVariantsCanConvert")){
            NbtCompound nbtCompound = nbt.getCompound("MobVariantsCanConvert");
            canConvert = nbtCompound.getBoolean("canConvert");
        }
    }

    //writing canConvert to Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/ZombieEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void writeCanConvertToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(!canConvert){
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("canConvert", canConvert);
            nbt.put("MobVariantsCanConvert", nbtCompound);
        }
    }

}
