package nl.ramondevaan.adventofcode2018.day23;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23Test {

  static Day23 day23;

  @BeforeAll
  static void setUp() throws URISyntaxException, IOException {
    Path path = Path.of(Objects.requireNonNull(Day23Test.class.getResource("/day23.txt")).toURI());
    List<String> lines = Files.readAllLines(path);
    day23 = new Day23(lines);
  }

  @Test
  void puzzle1() {
    assertEquals(442L, day23.solve1());
  }

  @Test
  void puzzle2() {
    assertEquals(100985898L, day23.solve2());
  }

}
