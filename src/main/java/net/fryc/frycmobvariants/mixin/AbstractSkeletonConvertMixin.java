package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.util.MobConvertingHelper;
import net.fryc.frycmobvariants.util.mixin_interfaces.CanConvert;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractSkeleton.class)
abstract class AbstractSkeletonConvertMixin extends Monster implements RangedAttackMob, CanConvert {

    @Unique
    private int ticksUntilWaterConversion = 300;
    @Unique
    private int inWaterTime;
    @Unique
    private boolean isConvertingToCorsair = false;

    protected AbstractSkeletonConvertMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }


    @SuppressWarnings("unchecked")
    public void tick() {
        super.tick();
        AbstractSkeleton skeleton = ((AbstractSkeleton)(Object)this);
        if(!skeleton.level().isClientSide()){
            //converting to corsair underwater
            if(MobVariants.config.convertSkeletonsToCorsairsUnderwater){
                if ((MobConvertingHelper.SKELETON_UNDERWATER_CONVERSION_AVAILABLE.contains((EntityType<? extends AbstractSkeleton>) skeleton.getType())) && skeleton.isAlive() && !skeleton.isNoAi()) {
                    if (this.isConvertingToCorsair) {
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
                                this.isConvertingToCorsair = true;
                            }
                        } else {
                            inWaterTime = -1;
                        }
                    }
                }
            }
        }
    }

}
