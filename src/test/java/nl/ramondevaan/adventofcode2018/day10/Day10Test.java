package nl.ramondevaan.adventofcode2018.day10;

import nl.ramondevaan.adventofcode2018.util.LetterParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    private Day10 day10;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        final var parser = new LetterParser();
        final var letters = parser.parse(Day10.class.getResource("/day_10_letters.txt"));
        StarParser starParser = new StarParser();
        day10 = Day10.builder()
                     .stars(starParser.parse(Files.lines(Path.of(getClass().getResource("/day10.txt").toURI()))))
            .lettersParser(new LettersParser(letters))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals("HRPHBRKG", day10.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(10355, day10.puzzle2());
    }
}
