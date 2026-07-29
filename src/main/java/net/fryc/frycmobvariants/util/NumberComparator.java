package net.fryc.frycmobvariants.util;

import java.util.Locale;

public enum NumberComparator {
    LOWER_THAN {
        @Override
        public <T extends Number> boolean compare(T first, T second) {
            return first.doubleValue() < second.doubleValue();
        }
    },
    HIGHER_THAN {
        @Override
        public <T extends Number> boolean compare(T first, T second) {
            return first.doubleValue() > second.doubleValue();
        }
    },
    EQUAL {
        @Override
        public <T extends Number> boolean compare(T first, T second) {
            return first.doubleValue() == second.doubleValue();
        }
    };

    public abstract <T extends Number> boolean compare(T first, T second);

    public static NumberComparator getByName(String name) {
        return switch(name.toUpperCase(Locale.ROOT)) {
            case "LOWER_THAN" -> NumberComparator.LOWER_THAN;
            case "HIGHER_THAN" -> NumberComparator.HIGHER_THAN;
            case "EQUAL" -> NumberComparator.EQUAL;
            default -> throw new IllegalStateException("Unexpected value: '" + name + "'. Must be either 'LOWER_THAN', 'HIGHER_THAN' or 'EQUAL'");
        };
    }
}
