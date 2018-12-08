package nl.ramondevaan.adventofcode2018.day04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day04Test {

    private Day04 day04;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        GuardParser guardParser = new GuardParser();

        day04 = Day04.builder()
                     .guards(guardParser.parse(
                         Files.lines(Path.of(getClass().getResource("/day04.txt").toURI()))))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(87681, day04.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(136461, day04.puzzle2());
    }
}
