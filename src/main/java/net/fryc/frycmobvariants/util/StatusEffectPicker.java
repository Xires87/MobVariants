package net.fryc.frycmobvariants.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

public enum StatusEffectPicker {
    WEAKNESS{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.WEAKNESS;
        }
    },
    INSTANT_DAMAGE{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.INSTANT_DAMAGE;
        }
    },
    SLOWNESS{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.SLOWNESS;
        }
    },
    DARKNESS{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.DARKNESS;
        }
    },
    BLINDNESS{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.BLINDNESS;
        }
    },
    NAUSEA{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.NAUSEA;
        }
    },
    HUNGER{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.HUNGER;
        }
    },
    POISON{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.POISON;
        }
    },
    WITHER{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.WITHER;
        }
    },
    GLOWING{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.GLOWING;
        }
    },
    LEVITATION{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.LEVITATION;
        }
    },
    UNLUCK{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.UNLUCK;
        }
    },
    SLOW_FALLING{
        @Override
        public StatusEffect getStatusEffect() {
            return StatusEffects.SLOW_FALLING;
        }
    };




    public abstract StatusEffect getStatusEffect();
}
