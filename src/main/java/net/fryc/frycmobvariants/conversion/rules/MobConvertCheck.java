package net.fryc.frycmobvariants.conversion.rules;

import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@FunctionalInterface
public interface MobConvertCheck {
    @Nullable MobConvertingOutcome test(Mob mob, Random random);
}
