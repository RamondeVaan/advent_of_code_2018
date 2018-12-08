package nl.ramondevaan.adventofcode2018.day04;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Builder
public class Day04 {
    private final List<Guard> guards;

    public int puzzle1() {
        Guard mostAsleep = guards.stream().max(Comparator.comparingInt(
            g -> g.getShifts()
                  .stream()
                  .mapToInt(s -> s.getAwake().stream().mapToInt(b -> b ? 0 : 1).sum())
                  .sum()
        )).orElseThrow();

        return mostAsleep.getId() * bestMinute(mostAsleep).getLeft();
    }

    public int puzzle2() {
        Pair<Guard, Pair<Integer, Integer>> p = guards
            .stream().map(g -> Pair.of(g, bestMinute(g)))
            .max(Comparator.comparingInt(s -> s.getRight().getRight()))
            .orElseThrow();

        return p.getLeft().getId() * p.getRight().getLeft();
    }

    private Pair<Integer, Integer> bestMinute(Guard g) {
        return IntStream.range(0, 60).boxed()
                        .map(i -> Pair.of(i, minuteScore(g, i)))
                        .max(Comparator.comparingInt(Pair::getRight))
                        .orElseThrow();
    }

    private int minuteScore(Guard g, int minute) {
        return g.getShifts()
                .stream()
                .mapToInt(s -> s.getAwake().get(minute) ? 0 : 1)
                .sum();
    }
}
