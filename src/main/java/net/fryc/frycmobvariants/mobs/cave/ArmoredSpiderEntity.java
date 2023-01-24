package net.fryc.frycmobvariants.mobs.cave;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ArmoredSpiderEntity extends SpiderEntity {
    public ArmoredSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createArmoredSpiderAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000000192092896).add(EntityAttributes.GENERIC_ARMOR, 12).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1f);
    }

    //armored spiders take 40% less damage from bows
    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getSource() instanceof ArrowEntity && source.getAttacker() instanceof LivingEntity entity && (entity.getMainHandStack().isOf(Items.BOW) ||
                (entity.getOffHandStack().isOf(Items.BOW) && !entity.getMainHandStack().isOf(Items.CROSSBOW)))){
            amount*=0.60;
            super.damage(source, amount);
        }
        else{
            super.damage(source, amount);
        }
        return true;
    }
}
