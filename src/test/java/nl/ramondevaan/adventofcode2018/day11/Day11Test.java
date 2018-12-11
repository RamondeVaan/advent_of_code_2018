package nl.ramondevaan.adventofcode2018.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    private Day11 day11;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day11 = Day11.builder()
                     .gridSerialNumber(Integer.parseInt(Files.readString(Path.of(getClass().getResource("/day11.txt")
                                                                                           .toURI())).trim()))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals("216,12", day11.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals("236,175,11", day11.puzzle2());
    }
}
