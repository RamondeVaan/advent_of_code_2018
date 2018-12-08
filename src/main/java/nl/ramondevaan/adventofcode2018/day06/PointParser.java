package nl.ramondevaan.adventofcode2018.day06;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PointParser {
    private static final String POINT_FORMAT = "(\\d+), (\\d+)";
    private static final Pattern POINT_PATTERN = Pattern.compile(POINT_FORMAT);

    public List<Point> parse(Stream<String> input) {
        return input.map(POINT_PATTERN::matcher)
                    .peek(Matcher::matches)
                    .map(Matcher::toMatchResult)
                    .map(m -> new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))))
                    .collect(Collectors.toList());
    }
}
