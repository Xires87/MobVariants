package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.util.CanConvert;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractSkeletonEntity.class)
abstract class AbstractSkeletonConvertMixin extends HostileEntity implements RangedAttackMob, CanConvert {

    boolean canConvert = true;
    Random random = new Random();

    private static final TrackedData<Boolean> CONVERTING_IN_WATER;
    private int ticksUntilWaterConversion;
    private int inWaterTime;

    protected AbstractSkeletonConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts skeletons to undead warriors
    //converts wither skeletons to executioners
    //only first mob tick (right after spawning) tries to convert it
    public void tick() {
        super.tick();
        AbstractSkeletonEntity skeleton = ((AbstractSkeletonEntity)(Object)this);
        if(!skeleton.getWorld().isClient){
            if(skeleton.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                MobConvertingHelper.tryToConvertSkeleton(skeleton ,random);
                canConvert = false;
            }

            //converting to corsair underwater
            if(MobVariants.config.convertSkeletonsToCorsairsUnderwater){
                if ((skeleton.getClass() == SkeletonEntity.class || skeleton.getClass() == UndeadWarriorEntity.class) && skeleton.isAlive() && !skeleton.isAiDisabled()) {
                    if (skeleton.getDataTracker().get(CONVERTING_IN_WATER)) {
                        --ticksUntilWaterConversion;
                        if (ticksUntilWaterConversion < 0) {
                            skeleton.playSoundIfNotSilent(SoundEvents.AMBIENT_UNDERWATER_EXIT);
                            if(skeleton.getMainHandStack().getItem() instanceof BowItem) skeleton.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                            skeleton.convertTo(ModMobs.CORSAIR, true);
                        }
                    } else {
                        if (skeleton.isSubmergedIn(FluidTags.WATER)) {
                            ++inWaterTime;
                            if (inWaterTime >= 600) {
                                setTicksUntilWaterConversion(300);
                            }
                        } else {
                            inWaterTime = -1;
                        }
                    }
                }
            }
        }
    }


    private void setTicksUntilWaterConversion(int ticksUntilConversion) {
        ticksUntilWaterConversion = ticksUntilConversion;
        ((AbstractSkeletonEntity)(Object)this).getDataTracker().set(CONVERTING_IN_WATER, true);
    }

    //init data tracker
    protected void initDataTracker() {
        super.initDataTracker();
        ((AbstractSkeletonEntity)(Object)this).getDataTracker().startTracking(CONVERTING_IN_WATER, false);
    }



    //reading canConvert from Nbt
    @Inject(method = "Lnet/minecraft/entity/mob/AbstractSkeletonEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readCanConvertFromNbt(NbtCompound nbt, CallbackInfo ci) {
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

    static {
        CONVERTING_IN_WATER = DataTracker.registerData(AbstractSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
