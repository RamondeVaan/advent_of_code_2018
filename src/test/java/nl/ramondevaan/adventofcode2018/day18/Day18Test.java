package nl.ramondevaan.adventofcode2018.day18;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

  static Day18 day18;

  @BeforeAll
  static void setUp() throws URISyntaxException, IOException {
    Path path = Path.of(Objects.requireNonNull(Day18Test.class.getResource("/day18.txt")).toURI());
    List<String> lines = Files.readAllLines(path);
    day18 = new Day18(lines);
  }

  @Test
  void puzzle1() {
    assertEquals(605154L, day18.solve1());
  }

  @Test
  void puzzle2() {
    assertEquals(200364L, day18.solve2());
  }

}
