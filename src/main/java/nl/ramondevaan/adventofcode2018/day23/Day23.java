package nl.ramondevaan.adventofcode2018.day23;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Day23 {

  private final List<Nanobot> nanobots;

  public Day23(final List<String> lines) {
    final var parser = new NanobotParser();
    this.nanobots = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var largestRange = nanobots.stream().max(Comparator.comparingLong(Nanobot::getRange)).orElseThrow();
    return nanobots.stream().filter(largestRange::isInRange).count();
  }

  public long solve2() {
    final var encompassAll = encompassAll(nanobots);
    final var queue = new PriorityQueue<>(100_000,
      Comparator.<CubeIntersections>comparingInt(c -> c.intersections).reversed()
        .thenComparingLong(c -> c.cube.manhattanDistanceToOrigin));
    queue.add(new CubeIntersections(encompassAll, nanobots.size()));

    CubeIntersections cubeIntersections;
    while ((cubeIntersections = queue.poll()) != null) {
      if (cubeIntersections.cube.size == 1) {
        return cubeIntersections.cube.manhattanDistanceToOrigin;
      }
      for (final Cube split : cubeIntersections.cube.split()) {
        queue.add(cubeIntersections(split, nanobots));
      }
    }

    throw new IllegalStateException();
  }

  private static CubeIntersections cubeIntersections(final Cube cube, final List<Nanobot> nanobots) {
    var intersections = 0;

    for (final var nanobot : nanobots) {
      if (cube.intersects(nanobot)) {
        intersections++;
      }
    }

    return new CubeIntersections(cube, intersections);
  }

  private static Cube encompassAll(final List<Nanobot> nanobots) {
    long xMin = Long.MAX_VALUE, xMax = Long.MIN_VALUE;
    long yMin = Long.MAX_VALUE, yMax = Long.MIN_VALUE;
    long zMin = Long.MAX_VALUE, zMax = Long.MIN_VALUE;

    for (final var nanobot : nanobots) {
      xMin = Math.min(xMin, nanobot.position.x);
      xMax = Math.max(xMax, nanobot.position.x);
      yMin = Math.min(yMin, nanobot.position.y);
      yMax = Math.max(yMax, nanobot.position.y);
      zMin = Math.min(zMin, nanobot.position.z);
      zMax = Math.max(zMax, nanobot.position.z);
    }

    final var xSize = xMax - xMin + 1;
    final var ySize = yMax - yMin + 1;
    final var zSize = zMax - zMin + 1;

    return new Cube(xMin, yMin, zMin, xSize, ySize, zSize);
  }
}
