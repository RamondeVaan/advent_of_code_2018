package nl.ramondevaan.adventofcode2018.day01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

class Day01Test {

    private Day01 day01;

    @BeforeEach
    void init() throws URISyntaxException, IOException {
        day01 = Day01.builder()
                     .start(0)
                     .input(Files.lines(Path.of(getClass().getResource("/day01.txt").toURI()))
                                 .mapToInt(Integer::parseInt).toArray())
                     .build();
    }

    @Test
    void puzzle1() {
        Assertions.assertEquals(569, day01.puzzle1());
    }

    @Test
    void puzzle2() {
        Assertions.assertEquals(77666, day01.puzzle2());
    }
}
