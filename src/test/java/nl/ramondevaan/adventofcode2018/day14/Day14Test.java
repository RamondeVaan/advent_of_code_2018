package nl.ramondevaan.adventofcode2018.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {

    private Day14 day14;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day14 = Day14.builder()
                     .numberOfRecipes(Integer.parseInt(Files.readString(Path.of(getClass().getResource("/day14.txt")
                                                                                          .toURI())).trim()))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals("7121102535", day14.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(20236441, day14.puzzle2());
    }
}
