package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.util.CanConvert;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.nbt.NbtCompound;
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
                if(piglin.getClass() == PiglinEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.piglinConvertChance){
                        piglin.convertTo(ModMobs.INFECTED_PIGLIN, true);
                    }
                    canConvert = false;
                }
                else if(piglin.getClass() == PiglinBruteEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.piglinBruteConvertChance){
                        piglin.convertTo(ModMobs.INFECTED_PIGLIN_BRUTE, true);
                    }
                    canConvert = false;
                }
            }
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
