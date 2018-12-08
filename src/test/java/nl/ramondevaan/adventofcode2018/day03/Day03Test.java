package nl.ramondevaan.adventofcode2018.day03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day03Test {
    private Day03 day03;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        ClaimParser claimParser = new ClaimParser();

        day03 = Day03.builder()
                     .claims(Files.lines(Path.of(getClass().getResource("/day03.txt").toURI()))
                                  .map(claimParser::parse)
                                  .collect(Collectors.toList()))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(120419, day03.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(445, day03.puzzle2());
    }
}
