package net.fryc.frycmobvariants.mixin;

import net.fryc.frycmobvariants.MobVariants;
import net.fryc.frycmobvariants.mobs.ModMobs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(GhastEntity.class)
abstract class GhastConvertMixin extends FlyingEntity implements Monster {
    boolean canConvert = true;
    Random random = new Random();

    protected GhastConvertMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    //converts ghasts to nightmare
    public void tick() {
        super.tick();
        if (!world.isClient) {
            if (canConvert) {
                GhastEntity ghast = ((GhastEntity) (Object) this);
                if (ghast.getName().contains(Text.of("Ghast"))) {
                    if (random.nextInt(0, 100) <= MobVariants.config.ghastConvertChance) {
                        ghast.convertTo(ModMobs.NIGHTMARE, false);
                    }
                    canConvert = false;
                }
            }
        }
    }
}
