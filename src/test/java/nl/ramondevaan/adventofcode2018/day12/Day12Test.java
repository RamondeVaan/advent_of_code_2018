package nl.ramondevaan.adventofcode2018.day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    private Day12 day12;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        PotParser potParser = new PotParser();
        potParser.parse(Files.lines(Path.of(getClass().getResource("/day12.txt").toURI())));

        day12 = Day12.builder()
                     .initialState(potParser.getInitialState())
                     .patterns(potParser.getPatterns())
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(3494, day12.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(2850000002454L, day12.puzzle2());
    }
}
