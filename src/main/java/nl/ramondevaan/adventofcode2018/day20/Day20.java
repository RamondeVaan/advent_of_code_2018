package nl.ramondevaan.adventofcode2018.day20;

import nl.ramondevaan.adventofcode2018.util.Coordinate;
import nl.ramondevaan.adventofcode2018.util.IntMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Day20 {

  private final int startRow;
  private final int startColumn;
  private final IntMap map;

  public Day20(final List<String> lines) {
    final var parser = new MapParser();
    final var map = parser.parse(lines.get(0));
    this.startRow = map.startRow;
    this.startColumn = map.startColumn;
    this.map = map.map;
  }

  public long solve1() {
    final var distanceMap = computeDistanceMap(map);

    var max = Integer.MIN_VALUE;

    for (int row = 0; row < distanceMap.rows(); row++) {
      for (int column = 0; column < distanceMap.columns(); column++) {
        if (map.valueAt(row, column) == Map.ROOM) {
          max = Math.max(max, distanceMap.valueAt(row, column));
        }
      }
    }

    return max;
  }

  public long solve2() {
    final var distanceMap = computeDistanceMap(map);
    var count = 0;

    for (int row = 0; row < distanceMap.rows(); row++) {
      for (int column = 0; column < distanceMap.columns(); column++) {
        if (map.valueAt(row, column) == Map.ROOM && distanceMap.valueAt(row, column) >= 1_000) {
          count++;
        }
      }
    }

    return count;
  }

  private IntMap computeDistanceMap(final IntMap map) {
    final var distanceMap = IntMap.builder(map.rows(), map.columns());
    distanceMap.fill(Integer.MAX_VALUE);
    final var queue = new ArrayDeque<Coordinate>(map.size());
    distanceMap.set(startRow, startColumn, 0);
    queue.add(new Coordinate(startRow, startColumn));

    Coordinate coordinate;
    while ((coordinate = queue.poll()) != null) {
      final var nextDistance = distanceMap.get(coordinate.row, coordinate.column) + 1;
      for (final var neighbor : neighbors(map, coordinate)) {
        if (distanceMap.get(neighbor.row, neighbor.column) == Integer.MAX_VALUE) {
          distanceMap.set(neighbor.row, neighbor.column, nextDistance);
          queue.add(neighbor);
        }
      }
    }

    return distanceMap.build();
  }

  private List<Coordinate> neighbors(final IntMap map, final Coordinate coord) {
    final var neighbors = new ArrayList<Coordinate>(4);

    for (final var dir : Direction.values()) {
      if (map.valueAt(coord.row + dir.rowOffset, coord.column + dir.columnOffset) == Map.DOOR) {
        neighbors.add(new Coordinate(coord.row + 2 * dir.rowOffset, coord.column + 2 * dir.columnOffset));
      }
    }

    return neighbors;
  }
}
