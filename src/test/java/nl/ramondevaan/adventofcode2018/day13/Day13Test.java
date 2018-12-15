package nl.ramondevaan.adventofcode2018.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    private Day13 day13;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        TrackParser trackParser = new TrackParser();
        trackParser.parse(Files.lines(Path.of(getClass().getResource("/day13.txt").toURI())));

        day13 = Day13.builder().initialCarts(trackParser.getCarts()).paths(trackParser.getPaths()).build();
    }

    @Test
    void puzzle1() {
        assertEquals("26,92", day13.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals("86,18", day13.puzzle2());
    }
}
