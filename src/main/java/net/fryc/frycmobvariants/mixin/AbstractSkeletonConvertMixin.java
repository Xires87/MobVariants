package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.fryc.frycmobvariants.mobs.cave.UndeadWarriorEntity;
import net.fryc.frycmobvariants.mobs.nether.ExecutionerEntity;
import net.fryc.frycmobvariants.mobs.nether.SoulStealerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
abstract class AbstractSkeletonConvertMixin extends HostileEntity implements RangedAttackMob {

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
        if(!world.isClient){
            AbstractSkeletonEntity skeleton = ((AbstractSkeletonEntity)(Object)this);
            if(skeleton.hasStatusEffect(StatusEffects.NAUSEA)) canConvert = false;
            if(canConvert){
                if(skeleton.getClass() == SkeletonEntity.class){
                    if(!skeleton.getWorld().getDimension().ultrawarm()){
                        int i = (int)skeleton.getY();
                        if(i < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){
                            boolean bl = false;
                            if(MobVariants.config.fixedChanceToConvertSkeletonUnderSelectedYLevel > -1){
                                if(random.nextInt(0,100) <= MobVariants.config.fixedChanceToConvertSkeletonUnderSelectedYLevel){
                                    bl = true;
                                }
                            }
                            else if(random.nextInt(i, 100 + i) < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){ // ~26% to convert on 0Y level (default)
                                bl = true;
                            }

                            if(bl){
                                if(skeleton.getMainHandStack().hasEnchantments()){ //skeletons with enchantments on bow always convert to undead warriors with bow
                                    skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                                }
                                else{
                                    if(MobVariants.config.undeadWarriorSpawnWithBowChance <= random.nextInt(0, 100)){ //50% to give skeleton a sword
                                        skeleton.equipStack(EquipmentSlot.MAINHAND, UndeadWarriorEntity.getUndeadWarriorSword());
                                    }
                                    skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                                }
                            }
                        }
                    }
                    else {
                        if(random.nextInt(0, 100) <= MobVariants.config.skeletonToSoulStealerConvertChance){
                            skeleton.equipStack(EquipmentSlot.MAINHAND, SoulStealerEntity.getSoulsStealerHoe());
                            skeleton.convertTo(ModMobs.SOUL_STEALER, true);
                        }
                    }

                    canConvert = false;
                }
                else if(skeleton.getClass() == WitherSkeletonEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.witherSkeletonConvertChance){
                        skeleton.equipStack(EquipmentSlot.MAINHAND, ExecutionerEntity.getExecutionerAxe());
                        skeleton.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                        int equipChance = random.nextInt(0, 100);
                        if(equipChance > 45){
                            if(equipChance <= 49) skeleton.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
                            else if(equipChance <= 78) skeleton.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
                            else skeleton.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
                        }
                        skeleton.convertTo(ModMobs.EXECUTIONER, true);
                    }
                    canConvert = false;
                }
            }

            //converting to corsair underwater
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

    static {
        CONVERTING_IN_WATER = DataTracker.registerData(AbstractSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
