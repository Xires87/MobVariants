package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
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
                    int i = (int)skeleton.getY();
                    if(i < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){
                        if(random.nextInt(i, 100 + i) < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){ // ~26% to convert on 0Y level (default)
                            if(skeleton.getMainHandStack().hasEnchantments()){ //skeletons with enchantments on bow always convert to undead warriors with bow
                                skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                            }
                            else{
                                if(MobVariants.config.undeadWarriorSpawnWithBowChance <= random.nextInt(0, 100)){ //50% to give skeleton a sword
                                    skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                                }
                                skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                            }
                        }
                    }
                    canConvert = false;
                }
                else if(skeleton.getClass() == WitherSkeletonEntity.class){
                    if(random.nextInt(0, 100) <= MobVariants.config.witherSkeletonConvertChance){
                        skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
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
        }
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
}
