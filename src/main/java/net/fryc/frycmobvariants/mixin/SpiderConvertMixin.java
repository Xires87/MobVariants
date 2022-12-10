package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpiderEntity.class)
abstract class SpiderConvertMixin extends HostileEntity {

    boolean canConvert = true;
    Random random = new Random();

    protected SpiderConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts spiders to armored spiders
    //only first mob tick (right after spawning) tries to convert it
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToSpiderVariant(CallbackInfo info) {
        if(!world.isClient){
            if(canConvert){
                SpiderEntity spider = ((SpiderEntity)(Object)this);
                if(spider.getName().contains(Text.of("Spider"))){
                    int i = (int)spider.getY();
                    if(i > 40 && spider.getWorld().getBiome(spider.getBlockPos()).isIn(BiomeTags.IS_JUNGLE)){
                        if(random.nextInt(0, 100) <= MobVariants.config.spiderToTropicalSpiderConvertChance) spider.convertTo(ModMobs.TROPICAL_SPIDER, false); // ~80% chance to convert in jungle (default)
                    }
                    else {
                        if(i < MobVariants.config.spiderToBlackSpiderConvertLevelY){
                            if(random.nextInt(i, 100 + i) < MobVariants.config.spiderToBlackSpiderConvertLevelY){ // ~26% to convert on 0Y level (default)
                                spider.convertTo(ModMobs.ARMORED_SPIDER, false);
                            }
                        }
                    }
                    canConvert = false;
                }
            }
        }
    }


}
