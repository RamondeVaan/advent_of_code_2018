package nl.ramondevaan.adventofcode2018.day22;

import nl.ramondevaan.adventofcode2018.util.IntMap;

import java.util.*;

import static nl.ramondevaan.adventofcode2018.day22.Tool.*;
import static nl.ramondevaan.adventofcode2018.day22.Type.*;

public class Day22 {

  private final int depth;
  private final int targetRow;
  private final int targetColumn;

  public Day22(final List<String> lines) {
    final var depthParser = new DepthParser();
    final var targetParser = new TargetParser();
    this.depth = depthParser.parse(lines.get(0));
    final var target = targetParser.parse(lines.get(1));
    this.targetRow = target.row;
    this.targetColumn = target.column;
  }

  public long solve1() {
    final var erosionLevel = erosionLevel(targetRow + 1, targetColumn + 1, targetRow, targetColumn, depth);
    final var type = type(erosionLevel);
    return riskLevel(type);
  }

  public long solve2() {
    final var erosionLevel = erosionLevel(targetRow + 50, targetColumn + 50, targetRow, targetColumn, depth);
    final var type = type(erosionLevel);
    final var shortestPath = shortestPath(type, targetRow, targetColumn, TORCH);
    return shortestPath.distance;
  }

  private static Path shortestPath(final IntMap map, final int toRow, final int toColumn, final int toTool) {
    final var otherTool = otherTool();
    final int[][][] shortestPath = initializeShortestPath(map);
    final var queue = new PriorityQueue<RegionToolDist>(map.rows() * map.columns() * 3,
      Comparator.comparingInt(value -> value.distance));
    queue.add(new RegionToolDist(0, 0, TORCH, 0, null));

    RegionToolDist regionTool;
    while ((regionTool = queue.poll()) != null) {
      final var current = shortestPath[regionTool.row][regionTool.column][regionTool.tool];
      if (current <= regionTool.distance) {
        continue;
      }
      shortestPath[regionTool.row][regionTool.column][regionTool.tool] = regionTool.distance;
      if (regionTool.row == toRow && regionTool.column == toColumn && regionTool.tool == toTool) {
        return toPath(regionTool);
      }
      queue.addAll(neighbors(map, regionTool, otherTool));
    }

    throw new IllegalArgumentException("End is not reachable");
  }

  private static Path toPath(final RegionToolDist end) {
    final var coordinates = new ArrayList<RegionTool>();

    var current = end;

    do {
      coordinates.add(new RegionTool(current.row, current.column, current.tool, current.distance));
    } while ((current = current.last) != null);

    Collections.reverse(coordinates);

    return new Path(end.distance, List.copyOf(coordinates));
  }

  private static List<RegionToolDist> neighbors(final IntMap map, final RegionToolDist regionTool,
                                                final int[][] otherTool) {
    final List<RegionToolDist> ret = new ArrayList<>(5);
    final var distancePlusOne = regionTool.distance + 1;
    int newRow, newColumn;

    if (regionTool.row > 0 && map.valueAt(newRow = regionTool.row - 1, regionTool.column) != regionTool.tool) {
      ret.add(new RegionToolDist(newRow, regionTool.column, regionTool.tool, distancePlusOne, regionTool));
    }
    if (regionTool.column > 0 && map.valueAt(regionTool.row, newColumn = regionTool.column - 1) != regionTool.tool) {
      ret.add(new RegionToolDist(regionTool.row, newColumn, regionTool.tool, distancePlusOne, regionTool));
    }
    if (regionTool.row < map.rows() - 1 && map.valueAt(newRow = regionTool.row + 1, regionTool.column) != regionTool.tool) {
      ret.add(new RegionToolDist(newRow, regionTool.column, regionTool.tool, distancePlusOne, regionTool));
    }
    if (regionTool.column < map.columns() - 1 && map.valueAt(regionTool.row, newColumn = regionTool.column + 1) != regionTool.tool) {
      ret.add(new RegionToolDist(regionTool.row, newColumn, regionTool.tool, distancePlusOne, regionTool));
    }
    final var currentType = map.valueAt(regionTool.row, regionTool.column);
    final var newTool = otherTool[currentType][regionTool.tool];


    final var distance = regionTool.distance + 7;
    ret.add(new RegionToolDist(regionTool.row, regionTool.column, newTool, distance, regionTool));

    return ret;
  }

  private static int[][] otherTool() {
    final var ret = new int[3][3];

    ret[ROCKY][NEITHER] = -1;
    ret[ROCKY][CLIMBING_GEAR] = TORCH;
    ret[ROCKY][TORCH] = CLIMBING_GEAR;
    ret[WET][NEITHER] = CLIMBING_GEAR;
    ret[WET][CLIMBING_GEAR] = NEITHER;
    ret[WET][TORCH] = -1;
    ret[NARROW][NEITHER] = TORCH;
    ret[NARROW][CLIMBING_GEAR] = -1;
    ret[NARROW][TORCH] = NEITHER;

    return ret;
  }

  private static int[][][] initializeShortestPath(final IntMap map) {
    final int[][][] shortestPath = new int[map.rows()][map.columns()][3];

    for (final int[][] ints : shortestPath) {
      for (final int[] anInt : ints) {
        Arrays.fill(anInt, Integer.MAX_VALUE);
      }
    }

    return shortestPath;
  }

  private static IntMap erosionLevel(final int rows, final int columns, final int targetRow, final int targetColumn,
                                     final int depth) {
    final var builder = IntMap.builder(rows, columns);
    builder.set(0, 0, depth % 20183);

    for (int column = 1; column < columns; column++) {
      builder.set(0, column, (column * 16807 + depth) % 20183);
    }
    for (int row = 1; row < rows; row++) {
      builder.set(row, 0, (row * 48271 + depth) % 20183);
    }
    for (int column = 1, lastColumn = 0; column < targetColumn; column++, lastColumn++) {
      for (int row = 1, lastRow = 0; row < rows; row++, lastRow++) {
        builder.set(row, column, (builder.get(lastRow, column) *
          builder.get(row, lastColumn) + depth) % 20183);
      }
    }
    for (int row = 1, lastRow = 0, lastColumn = targetColumn - 1; row < targetRow; row++, lastRow++) {
      builder.set(row, targetColumn, (builder.get(lastRow, targetColumn) *
        builder.get(row, lastColumn) + depth) % 20183);
    }
    builder.set(targetRow, targetColumn, depth % 20183);
    for (int row = targetRow + 1, lastRow = targetRow, lastColumn = targetColumn - 1; row < rows; row++, lastRow++) {
      builder.set(row, targetColumn, (builder.get(lastRow, targetColumn) *
        builder.get(row, lastColumn) + depth) % 20183);
    }
    for (int column = targetColumn + 1, lastColumn = targetColumn; column < columns; column++, lastColumn++) {
      for (int row = 1, lastRow = 0; row < rows; row++, lastRow++) {
        builder.set(row, column, (builder.get(lastRow, column) *
          builder.get(row, lastColumn) + depth) % 20183);
      }
    }

    return builder.build();
  }

  private static IntMap type(final IntMap erosionLevel) {
    final var builder = erosionLevel.toBuilder();

    for (int row = 0; row < builder.rows; row++) {
      for (int column = 0; column < builder.columns; column++) {
        final var type = Math.toIntExact((builder.get(row, column)) % 3);
        builder.set(row, column, type);
      }
    }

    return builder.build();
  }

  private static int riskLevel(final IntMap map) {
    var sum = 0;

    for (int row = 0; row < map.rows(); row++) {
      for (int column = 0; column < map.columns(); column++) {
        sum += map.valueAt(row, column);
      }
    }

    sum -= map.valueAt(map.rows() - 1, map.columns() - 1);

    return sum;
  }
}
