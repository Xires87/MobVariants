package net.fryc.frycmobvariants.util;

import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public enum DifficultyPicker {

    NONE{
        @Override
        public boolean hasCorrectDifficulty(World world) {
            return false;
        }
    },
    PEACEFUL{
        @Override
        public boolean hasCorrectDifficulty(World world) {
            return true;
        }
    },
    EASY{
        @Override
        public boolean hasCorrectDifficulty(World world) {
            return world.getDifficulty() != Difficulty.PEACEFUL;
        }
    },
    NORMAL{
        @Override
        public boolean hasCorrectDifficulty(World world) {
            return world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD;
        }
    },
    HARD{
        @Override
        public boolean hasCorrectDifficulty(World world) {
            return world.getDifficulty() == Difficulty.HARD;
        }
    };


    public abstract boolean hasCorrectDifficulty(World world);
}
