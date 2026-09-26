package net.fryc.frycmobvariants.mobs.biome;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.Level;

public class TropicalSpiderEntity extends Spider {

    private static final int EASY_POISON_DURATION = 30;
    private static final int NORMAL_POISON_DURATION = 60;
    private static final int HARD_POISON_DURATION = 120;

    public TropicalSpiderEntity(EntityType<? extends Spider> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createTropicalSpiderAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0).add(Attributes.MOVEMENT_SPEED, 0.30000001192092896).add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (super.doHurtTarget(level, target)) {
            if (target instanceof LivingEntity) {
                ((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.POISON, getPoisonDuration(level.getDifficulty()), 1), this);
            }

            return true;
        }

        return false;
    }

    private static int getPoisonDuration(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> EASY_POISON_DURATION;
            case NORMAL -> NORMAL_POISON_DURATION;
            case HARD -> HARD_POISON_DURATION;
            default -> 0;
        };
    }
}
