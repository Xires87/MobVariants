package net.fryc.frycmobvariants.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public enum StatusEffectPicker {
    WEAKNESS{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.WEAKNESS;
        }
    },
    INSTANT_DAMAGE{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.INSTANT_DAMAGE;
        }
    },
    SLOWNESS{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.SLOWNESS;
        }
    },
    DARKNESS{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.DARKNESS;
        }
    },
    BLINDNESS{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.BLINDNESS;
        }
    },
    NAUSEA{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.NAUSEA;
        }
    },
    HUNGER{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.HUNGER;
        }
    },
    POISON{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.POISON;
        }
    },
    WITHER{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.WITHER;
        }
    },
    GLOWING{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.GLOWING;
        }
    },
    LEVITATION{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.LEVITATION;
        }
    },
    UNLUCK{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.UNLUCK;
        }
    },
    SLOW_FALLING{
        @Override
        public RegistryEntry<StatusEffect> getStatusEffect() {
            return StatusEffects.SLOW_FALLING;
        }
    };




    public abstract RegistryEntry<StatusEffect> getStatusEffect();
}
