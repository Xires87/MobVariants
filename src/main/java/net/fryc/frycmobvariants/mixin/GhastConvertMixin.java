package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(GhastEntity.class)
abstract class GhastConvertMixin extends FlyingEntity implements Monster {
    boolean canConvert = true;
    Random random = new Random();

    protected GhastConvertMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts ghasts to nightmare
    public void tick() {
        super.tick();
        GhastEntity ghast = ((GhastEntity) (Object) this);
        if (!ghast.getWorld().isClient) {
            if(ghast.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if (canConvert) {
                if (ghast.getClass() == GhastEntity.class) {
                    if (random.nextInt(0, 100) <= MobVariants.config.ghastConvertChance) {
                        ghast.convertTo(ModMobs.NIGHTMARE, false);
                    }
                    canConvert = false;
                }
            }
        }
    }

    //reading canConvert from Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/GhastEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("MobVariantsCanConvert")){
            NbtCompound nbtCompound = nbt.getCompound("MobVariantsCanConvert");
            canConvert = nbtCompound.getBoolean("canConvert");
        }
    }

    //writing canConvert to Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/GhastEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void writeCanConvertToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(!canConvert){
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("canConvert", canConvert);
            nbt.put("MobVariantsCanConvert", nbtCompound);
        }
    }
}
