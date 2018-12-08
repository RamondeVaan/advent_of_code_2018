package nl.ramondevaan.adventofcode2018.day05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

    private Day05 day05;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day05 = Day05.builder()
                     .input(Files.readString(Path.of(getClass().getResource("/day05.txt").toURI())).trim())
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(9822, day05.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(5726, day05.puzzle2());
    }
}
