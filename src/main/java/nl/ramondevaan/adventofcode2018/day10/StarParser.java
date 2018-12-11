package nl.ramondevaan.adventofcode2018.day10;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarParser {
    private static final String STAR_FORMAT = "position=<\\s*(-?\\d+), \\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>";
    private static final Pattern STAR_PATTERN = Pattern.compile(STAR_FORMAT);

    public List<Star> parse(Stream<String> input) {
        return input.map(STAR_PATTERN::matcher)
                    .peek(Matcher::matches)
                    .map(Matcher::toMatchResult)
                    .map(m -> new Star(new Vector(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))),
                                       new Vector(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)))))
                    .collect(Collectors.toList());
    }
}
