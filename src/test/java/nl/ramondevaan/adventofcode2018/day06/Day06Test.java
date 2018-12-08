package nl.ramondevaan.adventofcode2018.day06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

    private Day06 day06;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        PointParser pointParser = new PointParser();

        day06 = Day06.builder()
                     .points(pointParser.parse(
                         Files.lines(Path.of(getClass().getResource("/day06.txt").toURI()))))
                     .maxDist(10000)
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(3569, day06.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(48978, day06.puzzle2());
    }
}
