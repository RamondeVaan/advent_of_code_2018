package nl.ramondevaan.adventofcode2018.day02;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
public class Day02 {
    private final List<String> ids;

    public long puzzle1() {
        long numHas2 = ids.stream().filter(s -> hasNSameLetters(s, 2)).count();
        long numHas3 = ids.stream().filter(s -> hasNSameLetters(s, 3)).count();

        return numHas2 * numHas3;
    }

    public String puzzle2() {
        return IntStream.range(0, ids.size()).boxed()
                        .flatMap(i -> IntStream.range(i + 1, ids.size()).mapToObj(j -> Pair.of(ids.get(i), ids.get(j))))
                        .map(p -> matchingLetters(p.getLeft(), p.getRight()))
                        .max(Comparator.comparingInt(String::length)).orElseThrow();
    }

    private static boolean hasNSameLetters(String s, int n) {
        return Arrays.stream(s.split("(?!^)"))
                     .collect(Collectors.groupingBy(j -> j, Collectors.counting()))
                     .values()
                     .stream()
                     .anyMatch(k -> k == n);
    }

    private static String matchingLetters(String s1, String s2) {
        return IntStream.range(0, s1.length()).filter(i -> s1.charAt(i) == s2.charAt(i))
                        .mapToObj(i -> s1.substring(i, i + 1)).collect(Collectors.joining());
    }
}
