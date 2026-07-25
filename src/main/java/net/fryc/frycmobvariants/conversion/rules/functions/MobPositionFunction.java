package net.fryc.frycmobvariants.conversion.rules.functions;

import com.google.gson.JsonObject;
import net.fryc.frycmobvariants.util.FrycJsonHelper;
import net.fryc.frycmobvariants.util.NumberComparator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;

public record MobPositionFunction(Optional<Pair<NumberComparator, Double>> y, Optional<Pair<NumberComparator, Double>> x, Optional<Pair<NumberComparator, Double>> z) implements BiPredicate<MobEntity, Random> {

    public static final String ID = "mob_position";

    @Override
    public boolean test(MobEntity mob, Random random) {
        return (this.y().isEmpty() || this.y().stream().anyMatch(yVal -> yVal.getA().compare(mob.getY(), yVal.getB()))) &&
                (this.x().isEmpty() || this.x().stream().anyMatch(xVal -> xVal.getA().compare(mob.getX(), xVal.getB()))) &&
                (this.z().isEmpty() || this.z().stream().anyMatch(zVal -> zVal.getA().compare(mob.getZ(), zVal.getB())));
    }

    public static MobPositionFunction fromJson(JsonObject jsonObject) {
        return new MobPositionFunction(
                Optional.ofNullable(getCoordinate(JsonHelper.getObject(jsonObject, "y", null))),
                Optional.ofNullable(getCoordinate(JsonHelper.getObject(jsonObject, "x", null))),
                Optional.ofNullable(getCoordinate(JsonHelper.getObject(jsonObject, "z", null)))
        );
    }

    private static @Nullable Pair<NumberComparator, Double> getCoordinate(@Nullable JsonObject object) {
        try {
            return new Pair<>(FrycJsonHelper.getNumberComparator(object, "comparator", NumberComparator.EQUAL), FrycJsonHelper.getValue(object));
        } catch (Exception ignored) { }

        return null;
    }
}
