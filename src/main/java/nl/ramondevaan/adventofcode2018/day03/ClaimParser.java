package nl.ramondevaan.adventofcode2018.day03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClaimParser {
    private static final String FORMAT = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)";
    private static final Pattern PATTERN = Pattern.compile(FORMAT);

    public Claim parse(String s) {
        Matcher m = PATTERN.matcher(s);

        if (!m.matches()) {
            throw new IllegalArgumentException();
        }

        return new Claim(
            Integer.parseInt(m.group(1)),
            Integer.parseInt(m.group(2)),
            Integer.parseInt(m.group(3)),
            Integer.parseInt(m.group(4)),
            Integer.parseInt(m.group(5))
        );
    }
}
