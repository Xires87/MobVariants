package net.fryc.frycmobvariants.util;

import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;

public enum DifficultyPicker {

    NONE{
        @Override
        public boolean hasCorrectDifficulty(Level level) {
            return false;
        }
    },
    PEACEFUL{
        @Override
        public boolean hasCorrectDifficulty(Level level) {
            return true;
        }
    },
    EASY{
        @Override
        public boolean hasCorrectDifficulty(Level level) {
            return level.getDifficulty() != Difficulty.PEACEFUL;
        }
    },
    NORMAL{
        @Override
        public boolean hasCorrectDifficulty(Level level) {
            return level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD;
        }
    },
    HARD{
        @Override
        public boolean hasCorrectDifficulty(Level level) {
            return level.getDifficulty() == Difficulty.HARD;
        }
    };


    public abstract boolean hasCorrectDifficulty(Level level);
}
