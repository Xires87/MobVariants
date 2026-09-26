package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.world.entity.ConversionTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractSkeleton.class)
abstract class AbstractSkeletonConvertMixin extends Monster implements RangedAttackMob, CanConvert {

    @Unique
    private ConversionTracker<AbstractSkeleton> drowningTracker;

    @Unique
    private int ticksUntilWaterConversion;
    @Unique
    private int inWaterTime;

    protected AbstractSkeletonConvertMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    // TODO naprawic przemienianie w korsarzy
/*
    public void tick() {
        super.tick();
        AbstractSkeleton skeleton = ((AbstractSkeleton)(Object)this);
        if(!skeleton.level().isClientSide()){
            //converting to corsair underwater
            if(MobVariants.config.convertSkeletonsToCorsairsUnderwater){
                if ((MobConvertingHelper.SKELETON_UNDERWATER_CONVERSION_AVAILABLE.contains(skeleton.getType())) && skeleton.isAlive() && !skeleton.isAiDisabled()) {
                    if (skeleton.getEntityData().get(CONVERTING_IN_WATER)) {
                        --ticksUntilWaterConversion;
                        if (ticksUntilWaterConversion < 0) {
                            skeleton.playSound(SoundEvents.AMBIENT_UNDERWATER_EXIT);
                            //skeleton.playSoundIfNotSilent(SoundEvents.AMBIENT_UNDERWATER_EXIT);
                            if(skeleton.getMainHandItem().getItem() instanceof BowItem) skeleton.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                            skeleton.convertTo(ModMobs.CORSAIR, ConversionParams.single(skeleton, true, true), corsair -> {});
                        }
                    } else {
                        if (skeleton.isUnderWater()) {
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


    @Unique
    private void setTicksUntilWaterConversion(int ticksUntilConversion) {
        ticksUntilWaterConversion = ticksUntilConversion;
        ((AbstractSkeletonEntity)(Object)this).getDataTracker().set(CONVERTING_IN_WATER, true);
    }

    //init data tracker
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CONVERTING_IN_WATER, false);
    }

    static {
        CONVERTING_IN_WATER = DataTracker.registerData(AbstractSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

 */
}
