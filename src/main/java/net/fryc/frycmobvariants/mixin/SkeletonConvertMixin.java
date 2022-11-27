package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SkeletonEntity.class)
abstract class SkeletonConvertMixin extends AbstractSkeletonEntity {
    boolean canConvert = true;
    Random random = new Random();

    protected SkeletonConvertMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts skeletons to undead warriors
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToUndeadWarrior(CallbackInfo info) {
        if(!world.isClient){
            if(canConvert){
                SkeletonEntity skeleton = ((SkeletonEntity)(Object)this);
                if(skeleton.getName().contains(Text.of("Skeleton"))){
                    int i = (int)skeleton.getY();
                    if(i < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){
                        if(random.nextInt(i, 100 + i) < MobVariants.config.skeletonToUndeadWarriorConvertLevelY){ // ~26% to convert on 0Y level (default)
                            if(skeleton.getMainHandStack().hasEnchantments()){ //skeletons with enchantments on bow always convert to undead warriors with bow
                                skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                            }
                            else{
                                if(random.nextBoolean()){ //50% to give skeleton a sword
                                    skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                                }
                                skeleton.convertTo(ModMobs.UNDEAD_WARRIOR, true);
                            }
                        }
                    }
                    canConvert = false;
                }
            }
        }
    }
}
