package nl.ramondevaan.adventofcode2018.day02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {

    private Day02 day02;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day02 = Day02.builder()
                     .ids(Files.lines(Path.of(getClass().getResource("/day02.txt").toURI()))
                               .collect(Collectors.toList()))
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(7410, day02.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals("cnjxoritzhvbosyewrmqhgkul", day02.puzzle2());
    }
}
