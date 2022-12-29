package nl.ramondevaan.adventofcode2018.util;

public interface Parser<T, U> {
    U parse(T toParse);
}
