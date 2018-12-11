package nl.ramondevaan.adventofcode2018.day09;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class GameParser {
    private static final String GAME_FORMAT = "(\\d+) players; last marble is worth (\\d+) points";
    private static final Pattern GAME_PATTERN = Pattern.compile(GAME_FORMAT);

    private int numberOfPlayers;
    private int lastMarblePoints;

    public void parse(String s) {
        Matcher m = GAME_PATTERN.matcher(s);
        if (!m.matches()) {
            throw new IllegalArgumentException();
        }
        numberOfPlayers = Integer.parseInt(m.group(1));
        lastMarblePoints = Integer.parseInt(m.group(2));
    }
}
