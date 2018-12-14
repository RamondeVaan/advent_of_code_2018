package nl.ramondevaan.adventofcode2018.day12;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PotParser {
    private static final String INITIAL_STATE_FORMAT = "initial state: ([#|.]+)";
    private static final String SPREAD_FORMAT = "([#|.]+) => ([#|.])";
    private static final Pattern INITIAL_STATE_PATTERN = Pattern.compile(INITIAL_STATE_FORMAT);
    private static final Pattern SPREAD_PATTERN = Pattern.compile(SPREAD_FORMAT);

    private Map<List<Boolean>, Boolean> patterns;
    private List<Boolean> initialState;

    public void parse(Stream<String> input) {
        List<String> inputStrings = input.collect(Collectors.toList());

        Matcher initialStateMatcher = INITIAL_STATE_PATTERN.matcher(inputStrings.get(0));
        if (!initialStateMatcher.matches()) {
            throw new IllegalArgumentException();
        }
        initialState = Arrays.stream(initialStateMatcher.group(1).split("(?!^)"))
                             .map(s -> s.equals("#"))
                             .collect(Collectors.toList());

        patterns = new HashMap<>();

        for (int i = 2; i < inputStrings.size(); i++) {
            Matcher m = SPREAD_PATTERN.matcher(inputStrings.get(i));
            if (!m.matches()) {
                throw new IllegalArgumentException();
            }
            patterns.put(Arrays.stream(m.group(1).split("(?!^)")).map(s -> s.equals("#"))
                               .collect(Collectors.toList()),
                         m.group(2).equals("#"));
        }
    }
}
