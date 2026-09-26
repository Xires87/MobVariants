package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public record MobConvertingOutcome(MobConversionEquipment conversionEquipment, EntityType<? extends Entity> entityType) {
}
