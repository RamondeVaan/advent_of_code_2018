package nl.ramondevaan.adventofcode2018.day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day09Test {

    private Day09 day09;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        GameParser gameParser = new GameParser();
        gameParser.parse(Files.readString(Path.of(getClass().getResource("/day09.txt").toURI())).trim());

        day09 = Day09.builder()
                     .numberOfPlayers(gameParser.getNumberOfPlayers())
                     .lastMarblePoints(gameParser.getLastMarblePoints())
                     .build();
    }

    @Test
    void puzzle1() {
        assertEquals(382055L, day09.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(3133277384L, day09.puzzle2());
    }
}
