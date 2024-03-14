package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.util.CanConvert;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpiderEntity.class)
abstract class SpiderConvertMixin extends HostileEntity implements CanConvert {

    boolean canConvert = true;
    Random random = new Random();

    protected SpiderConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts spiders to armored spiders
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToSpiderVariant(CallbackInfo info) {
        SpiderEntity spider = ((SpiderEntity)(Object)this);
        if(!spider.getWorld().isClient){
            if(spider.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                MobConvertingHelper.tryToConvertSpider(spider, random);
                canConvert = false;
            }
        }
    }

    //reading canConvert from Nbt
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("MobVariantsCanConvert")){
            NbtCompound nbtCompound = nbt.getCompound("MobVariantsCanConvert");
            canConvert = nbtCompound.getBoolean("canConvert");
        }
    }

    //writing canConvert to Nbt
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
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
