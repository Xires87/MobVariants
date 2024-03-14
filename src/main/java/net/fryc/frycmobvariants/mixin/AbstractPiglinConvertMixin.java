package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.nether.ZombifiedPiglinBruteEntity;
import net.fryc.frycmobvariants.util.CanConvert;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractPiglinEntity.class)
abstract class AbstractPiglinConvertMixin extends HostileEntity implements CanConvert {

    boolean canConvert = true;
    Random random = new Random();

    protected AbstractPiglinConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts piglin to infected piglin
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "mobTick()V")
    public void convertToPiglinVariant(CallbackInfo info) {
        AbstractPiglinEntity piglin = ((AbstractPiglinEntity)(Object)this);
        if(!piglin.getWorld().isClient){
            if(piglin.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                MobConvertingHelper.tryToConvertPiglin(piglin, random);
                canConvert = false;
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "zombify(Lnet/minecraft/server/world/ServerWorld;)V", cancellable = true)
    private void turnPiglinBrutesIntoZombifiedPiglinBrutes(ServerWorld world, CallbackInfo info) {
        AbstractPiglinEntity dys = ((AbstractPiglinEntity)(Object)this);
        if(dys instanceof PiglinBruteEntity){
            ZombifiedPiglinBruteEntity zombifiedPiglinEntity = (ZombifiedPiglinBruteEntity)dys.convertTo(ModMobs.ZOMBIFIED_PIGLIN_BRUTE, true);
            if (zombifiedPiglinEntity != null) {
                zombifiedPiglinEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
            }
            info.cancel();
        }
    }


    //reading canConvert from Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/AbstractPiglinEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("MobVariantsCanConvert")){
            NbtCompound nbtCompound = nbt.getCompound("MobVariantsCanConvert");
            canConvert = nbtCompound.getBoolean("canConvert");
        }
    }

    //writing canConvert to Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/AbstractPiglinEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void writeCanConvertToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(!canConvert){
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("canConvert", canConvert);
            nbt.put("MobVariantsCanConvert", nbtCompound);
        }
    }

    public void setCanConvertToTrue(){
        canConvert = true;
    }

    public void setCanConvertToFalse(){
        canConvert = false;
    }
}
