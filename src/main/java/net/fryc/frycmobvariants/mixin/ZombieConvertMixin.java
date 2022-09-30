package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.gamerules.ModGameRules;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ZombieEntity.class)
abstract class ZombieConvertMixin extends HostileEntity {
    boolean canConvert = true;
    Random random = new Random();

    protected ZombieConvertMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts zombie to forgotten
    //only first mob tick (right after spawning) tries to convert it
    //baby zombies won't convert
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void convertToForgotten(CallbackInfo info) {
        if(!world.isClient){
            ZombieEntity zombie = ((ZombieEntity)(Object)this);
            if(zombie.getName().contains(Text.of("Zombie")) && !zombie.isBaby()){ //baby forgotten had the same hitboxes as adult forgotten (I didn't know how to fix it)
                int i = (int)zombie.getY();
                if(canConvert && i < world.getGameRules().getInt(ModGameRules.FORGOTTEN_SPAWN_LEVEL)){
                    if(random.nextInt(i, 100 + i) < world.getGameRules().getInt(ModGameRules.FORGOTTEN_SPAWN_LEVEL)){ // ~26% to convert on 0Y level (default)
                        zombie.convertTo(ModMobs.FORGOTTEN, true);
                    }
                    canConvert = false;
                }
            }
        }
    }
}
