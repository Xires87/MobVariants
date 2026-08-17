package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public record MobConvertingOutcome(MobConversionEquipment conversionEquipment, EntityType<? extends Entity> entityType) {
}
