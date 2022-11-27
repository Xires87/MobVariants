package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(WitherSkeletonEntity.class)
abstract class WitherSkeletonConvertMixin extends AbstractSkeletonEntity {
    boolean canConvert = true;
    Random random = new Random();

    protected WitherSkeletonConvertMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }


    //converts wither skeletons to executioners
    //only first mob tick (right after spawning) tries to convert it
    public void tick(){
        super.tick();
        if(!world.isClient){
            if(canConvert){
                WitherSkeletonEntity skeleton = ((WitherSkeletonEntity)(Object)this);
                if(skeleton.getName().contains(Text.of("Wither Skeleton"))){
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
}
