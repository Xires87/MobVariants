package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.biome.FrozenZombieEntity;
import net.fryc.frycmobvariants.tags.ModBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
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

    //converts zombie to forgotten or explorer
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
                        if(random.nextInt(0, 100) <= MobVariants.config.zombieToFrozenZombieConvertChance) zombie.convertTo(ModMobs.FROZEN_ZOMBIE, true); // ~80% chance to convert in snow biomes (default)
                    }
                    else if(zombie.getWorld().getBiome(zombie.getBlockPos()).isIn(ModBiomeTags.EXPLORER_SPAWN_BIOMES) && i > 38){
                        if(random.nextInt(0, 100) <= MobVariants.config.zombieToExplorerConvertChance) zombie.convertTo(ModMobs.EXPLORER, true); // ~75% chance to convert in jungle, swamp or lush cave (default)
                    }
                    else {
                        if(i < MobVariants.config.zombieToForgottenConvertLevelY){
                            if(random.nextInt(i, 100 + i) < MobVariants.config.zombieToForgottenConvertLevelY){ // ~26% to convert on 0Y level (default)
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

}
