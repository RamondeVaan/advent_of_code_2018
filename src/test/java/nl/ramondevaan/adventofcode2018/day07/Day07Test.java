package nl.ramondevaan.adventofcode2018.day07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    private Day07 day07;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        TaskParser taskParser = new TaskParser();
        day07 = Day07.builder()
                     .tasks(taskParser.parse(Files.lines(Path.of(getClass().getResource("/day07.txt").toURI()))))
                     .numberOfWorkers(15)
                     .extraTime(60)
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals("BCADPVTJFZNRWXHEKSQLUYGMIO", day07.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(973, day07.puzzle2());
    }
}
