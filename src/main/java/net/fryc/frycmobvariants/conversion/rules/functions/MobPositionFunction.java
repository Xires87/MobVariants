package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.fryc.frycmobvariants.util.NumberComparator;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;

public record MobPositionFunction(Optional<Pair<NumberComparator, Double>> y, Optional<Pair<NumberComparator, Double>> x, Optional<Pair<NumberComparator, Double>> z) implements BiPredicate<Mob, Random> {

    public static final String ID = "mob_position";

    @Override
    public boolean test(Mob mob, Random random) {
        return (this.y().isEmpty() || this.y().stream().anyMatch(yVal -> yVal.getA().compare(mob.getY(), yVal.getB()))) &&
                (this.x().isEmpty() || this.x().stream().anyMatch(xVal -> xVal.getA().compare(mob.getX(), xVal.getB()))) &&
                (this.z().isEmpty() || this.z().stream().anyMatch(zVal -> zVal.getA().compare(mob.getZ(), zVal.getB())));
    }

    public static MobPositionFunction fromJson(JsonObject jsonObject) {
        return new MobPositionFunction(
                Optional.ofNullable(getCoordinate(GsonHelper.getAsJsonObject(jsonObject, "y", null))),
                Optional.ofNullable(getCoordinate(GsonHelper.getAsJsonObject(jsonObject, "x", null))),
                Optional.ofNullable(getCoordinate(GsonHelper.getAsJsonObject(jsonObject, "z", null)))
        );
    }

    private static @Nullable Pair<NumberComparator, Double> getCoordinate(@Nullable JsonObject object) {
        try {
            return new Pair<>(FrycJsonHelper.getNumberComparator(object, "comparator", NumberComparator.EQUAL), FrycJsonHelper.getValue(object));
        } catch (Exception ignored) { }

        return null;
    }
}
