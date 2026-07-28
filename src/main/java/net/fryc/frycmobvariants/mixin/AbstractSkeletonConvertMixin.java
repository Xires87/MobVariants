package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractSkeletonEntity.class)
abstract class AbstractSkeletonConvertMixin extends HostileEntity implements RangedAttackMob, CanConvert {


    @Unique
    private static final TrackedData<Boolean> CONVERTING_IN_WATER;
    @Unique
    private int ticksUntilWaterConversion;
    @Unique
    private int inWaterTime;

    protected AbstractSkeletonConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();
        AbstractSkeletonEntity skeleton = ((AbstractSkeletonEntity)(Object)this);
        if(!skeleton.getWorld().isClient){
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
}
