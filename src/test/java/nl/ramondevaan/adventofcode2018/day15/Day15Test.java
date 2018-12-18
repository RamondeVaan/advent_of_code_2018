package nl.ramondevaan.adventofcode2018.day15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {

    private Day15 day15;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        GameParser gameParser = new GameParser();
        gameParser.parse(Files.lines(Path.of(getClass().getResource("/day15.txt").toURI())));

        day15 = Day15.builder().field(gameParser.getField()).initialUnits(gameParser.getUnits()).build();
    }

    @Test
    void puzzle1() {
        assertEquals(264384, day15.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(67022, day15.puzzle2());
    }
}
