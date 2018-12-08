package nl.ramondevaan.adventofcode2018.day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Day08Test {

    private Day08 day08;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        NodeParser nodeParser = new NodeParser();
        day08 = Day08.builder()
                     .rootNode(nodeParser.parse(Files.readString(Path.of(getClass().getResource("/day08.txt").toURI()))))
                     .build();

    }

    @Test
    void puzzle1() {
        assertEquals(45210, day08.puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(22793, day08.puzzle2());
    }
}
