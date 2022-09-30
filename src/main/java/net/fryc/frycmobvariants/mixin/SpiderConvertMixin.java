package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.gamerules.ModGameRules;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
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
    public void convertToArmoredSpider(CallbackInfo info) {
        if(!world.isClient){
            SpiderEntity spider = ((SpiderEntity)(Object)this);
            if(spider.getName().contains(Text.of("Spider"))){
                int i = (int)spider.getY();
                if(canConvert && i < world.getGameRules().getInt(ModGameRules.ARMORED_SPIDER_SPAWN_LEVEL)){
                    if(random.nextInt(i, 100 + i) < world.getGameRules().getInt(ModGameRules.ARMORED_SPIDER_SPAWN_LEVEL)){ // ~26% to convert on 0Y level (default)
                        spider.convertTo(ModMobs.ARMORED_SPIDER, false);
                    }
                    canConvert = false;
                }
            }
        }
    }


}
