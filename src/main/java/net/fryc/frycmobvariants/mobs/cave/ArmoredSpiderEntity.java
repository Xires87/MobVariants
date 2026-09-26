package net.fryc.frycmobvariants.mobs.cave;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ArmoredSpiderEntity extends Spider {


    public ArmoredSpiderEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
        this.xpReward += 1;
    }

    public static AttributeSupplier.Builder createArmoredSpiderAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 18.0).add(Attributes.MOVEMENT_SPEED, 0.30000000192092896).add(Attributes.ARMOR, 12).add(Attributes.KNOCKBACK_RESISTANCE, 0.1f);
    }

    //armored spiders deflect arrows
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if(source.getDirectEntity() instanceof Arrow arrow){
            if(arrow.getPierceLevel() < 1){
                arrow.deflect(ProjectileDeflection.MOMENTUM_DEFLECT, source.getEntity(), EntityReference.of(this), false, 0.3);
                return false;
            }
        }

        return super.hurtServer(level, source, damage);
    }

    protected void playStepSound(final BlockPos pos, final BlockState blockState) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.20F, 0.50F);
    }
}
