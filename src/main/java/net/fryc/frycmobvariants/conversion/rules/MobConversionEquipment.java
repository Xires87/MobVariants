package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

import java.util.List;

public record MobConversionEquipment(boolean keepEquipment, boolean initEquipment, List<MobConvertItem> customEquipment) {

    public record MobConvertItem(EquipmentSlot slot, Item item, double chance) { }
}

