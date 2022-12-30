package nl.ramondevaan.adventofcode2018.day18;

import nl.ramondevaan.adventofcode2018.util.IntMap;

import java.util.ArrayList;
import java.util.List;

public class Day18 {
  private final IntMap map;

  public Day18(final List<String> lines) {
    final var parser = new AreaParser();
    this.map = parser.parse(lines);
  }

  public long solve1() {
    return solve(10);
  }

  public long solve2() {
    return solve(1_000_000_000);
  }

  private long solve(final int minutes) {
    final var previous = new ArrayList<IntMap>(1000);
    previous.add(map);
    var current = map;
    int index;

    for (int minute = 0; minute < minutes; minute++) {
      current = next(current);
      if ((index = previous.indexOf(current)) != -1) {
        final var cycleLength = previous.size() - index;
        final int remaining = minutes - minute - 1;
        final var resultIndex = index + (remaining % cycleLength);
        current = previous.get(resultIndex);
        break;
      }
      previous.add(current);
    }

    var trees = 0L;
    var lumberyards = 0L;

    for (int row = 0; row < current.rows(); row++) {
      for (int column = 0; column < current.columns(); column++) {
        switch (current.valueAt(row, column)) {
          case Acre.TREE -> trees++;
          case Acre.LUMBERYARD ->  lumberyards++;
        }
      }
    }

    return trees * lumberyards;
  }

  private static IntMap next(final IntMap map) {
    final var builder = IntMap.builder(map.rows(), map.columns());

    for (int row = 0; row < map.rows(); row++) {
      for (int column = 0; column < map.columns(); column++) {
        final var oldValue = map.valueAt(row, column);
        final var neighbors = neighbors(map, row, column);
        final var trees = count(neighbors, Acre.TREE);
        final var lumberyard = count(neighbors, Acre.LUMBERYARD);
        final var newValue = switch (oldValue) {
          case Acre.OPEN_GROUND -> trees >= 3 ? Acre.TREE : Acre.OPEN_GROUND;
          case Acre.TREE -> lumberyard >= 3 ? Acre.LUMBERYARD : Acre.TREE;
          case Acre.LUMBERYARD -> lumberyard >= 1 && trees >= 1 ? Acre.LUMBERYARD : Acre.OPEN_GROUND;
          default -> throw new IllegalArgumentException();
        };
        builder.set(row, column, newValue);
      }
    }

    return builder.build();
  }

  private static int[] neighbors(final IntMap map, final int row, final int column) {
    return new int[] {
      valueAt(map, row - 1, column - 1),
      valueAt(map, row, column - 1),
      valueAt(map, row + 1, column - 1),
      valueAt(map, row - 1, column),
      valueAt(map, row + 1, column),
      valueAt(map, row - 1, column + 1),
      valueAt(map, row, column + 1),
      valueAt(map, row + 1, column + 1),
    };
  }

  private static int valueAt(final IntMap map, final int row, final int column) {
    if (0 <= row && row < map.rows() && 0 <= column && column < map.columns()) {
      return map.valueAt(row, column);
    }

    return -1;
  }

  private static int count(final int[] values, final int search) {
    int count = 0;

    for (final var value : values) {
      if (value == search) {
        count++;
      }
    }

    return count;
  }
}
