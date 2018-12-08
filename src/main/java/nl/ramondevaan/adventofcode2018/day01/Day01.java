package nl.ramondevaan.adventofcode2018.day01;

import lombok.Builder;

import java.util.Arrays;
import java.util.HashSet;

@Builder
public class Day01 {
    private final int start;
    private final int[] input;

    public int puzzle1() {
        return Arrays.stream(input).sum() + start;
    }

    public int puzzle2() {
        final HashSet<Integer> s = new HashSet<>();
        s.add(start);
        int k = start;

        for (int i = 0; true; i = (i + 1) % input.length) {
            k += input[i];
            if (s.contains(k)) {
                return k;
            }
            s.add(k);
        }
    }
}
