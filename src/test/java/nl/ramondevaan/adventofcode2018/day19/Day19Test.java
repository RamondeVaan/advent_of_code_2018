package nl.ramondevaan.adventofcode2018.day19;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {

  static Day19 day19;

  @BeforeAll
  static void setUp() throws URISyntaxException, IOException {
    Path path = Path.of(Objects.requireNonNull(Day19Test.class.getResource("/day19.txt")).toURI());
    List<String> lines = Files.readAllLines(path);
    day19 = new Day19(lines);
  }

  @Test
  void puzzle1() {
    assertEquals(3224L, day19.solve1());
  }

  @Test
  void puzzle2() {
    assertEquals(32188416L, day19.solve2());
  }

}
