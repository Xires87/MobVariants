package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public record MobConvertingOutcome(MobConversionEquipment conversionEquipment, EntityType<? extends MobEntity> entityType) {
}
