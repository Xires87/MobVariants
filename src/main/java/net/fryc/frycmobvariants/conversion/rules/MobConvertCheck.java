package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@FunctionalInterface
public interface MobConvertCheck {
    @Nullable MobConvertingOutcome test(MobEntity mob, Random random);
}
