package nl.ramondevaan.adventofcode2018.day12;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
public class Day12 {
    private final Map<List<Boolean>, Boolean> patterns;
    private final List<Boolean> initialState;

    public long puzzle1() {
        return valueAtGeneration(20);
    }

    public long puzzle2() {
        return valueAtGeneration(50000000000L);
    }

    private long valueAtGeneration(long generation) {
        List<Pots> states = initPotStates();
        int index;

        while (states.size() <= generation) {
            Pots p = nextGeneration(states.get(states.size() - 1));
            index = IntStream.range(0, states.size())
                             .filter(i -> states.get(i).getPots().equals(p.getPots()))
                             .findFirst()
                             .orElse(-1);

            if (index >= 0) {
                return calculateCycleValue(states, p, index, generation);
            }
            states.add(p);
        }

        Pots lastPots = states.get(states.size() - 1);
        return getValue(lastPots.getPots(), lastPots.getOffset());
    }

    private long calculateCycleValue(List<Pots> states, Pots p, int match, long generation) {
        long repeat = Math.subtractExact(states.size(), match);

        long sub = generation - states.size() + 1;
        long div = sub / repeat;
        int remainder = (int) (sub % repeat);

        long offsetDiffBetweenMatch = Math.subtractExact(p.getOffset(), states.get(match).getOffset());
        long offsetDiffBetweenRemainder = Math.subtractExact(states.get(match + remainder).getOffset(),
                                                             states.get(match).getOffset());
        long offset = offsetDiffBetweenMatch * div + offsetDiffBetweenRemainder + states.get(match).getOffset();
        return getValue(states.get(match + remainder).getPots(), offset);
    }

    private long getValue(List<Boolean> pots, long offset) {
        return IntStream.range(0, pots.size())
                        .filter(pots::get)
                        .mapToLong(i -> Math.subtractExact(i, offset))
                        .sum();
    }

    private List<Pots> initPotStates() {
        List<Pots> states = new ArrayList<>();
        states.add(new Pots(0, initialState));
        return states;
    }

    private Pots nextGeneration(Pots currentGeneration) {
        return newPots(IntStream.range(-2, currentGeneration.getPots().size() + 1)
                                .mapToObj(i -> getNextValue(currentGeneration.getPots(),
                                                            i))
                                .collect(Collectors.toList()),
                       currentGeneration.getOffset());
    }

    private Pots newPots(List<Boolean> cur, long oldOffset) {
        int firstTrue = cur.indexOf(true);
        return new Pots(oldOffset + 2 - firstTrue, cur.subList(firstTrue, cur.lastIndexOf(true) + 1));
    }

    private boolean valueAt(List<Boolean> cur, int index) {
        if (index < 0 || index >= cur.size()) {
            return false;
        }

        return cur.get(index);
    }

    private boolean getNextValue(List<Boolean> cur, int index) {
        return patterns.get(IntStream.rangeClosed(index - 2, index + 2)
                                     .mapToObj(i -> valueAt(cur, i))
                                     .collect(Collectors.toList()));
    }
}
