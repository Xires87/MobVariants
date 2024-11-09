package net.fryc.frycmobvariants.mobs.cave;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmoredSpiderEntity extends SpiderEntity {
    public ArmoredSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints += 1;
    }

    public static DefaultAttributeContainer.Builder createArmoredSpiderAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000000192092896).add(EntityAttributes.GENERIC_ARMOR, 12).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1f);
    }

    //armored spiders deflect arrows
    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getSource() instanceof ArrowEntity arrow){
            if(arrow.getPierceLevel() < 1){
                arrow.deflect(ProjectileDeflection.SIMPLE, this, source.getAttacker(), false);
                return false;
            }
        }

        return super.damage(source, amount);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.20F, 0.50F);
    }
}
