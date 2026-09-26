package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.List;

public record MobConversionEquipment(boolean keepEquipment, boolean initEquipment, List<MobConvertItem> customEquipment) {

    public record MobConvertItem(EquipmentSlot slot, Item item, double chance) { }
}

