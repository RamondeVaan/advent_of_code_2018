package nl.ramondevaan.adventofcode2018.util;

@FunctionalInterface
public interface LongBiPredicate {
    boolean test(long left, long right);
}
