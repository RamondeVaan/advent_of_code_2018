package nl.ramondevaan.adventofcode2018.day17;

import com.google.common.primitives.Ints;
import nl.ramondevaan.adventofcode2018.util.Coordinate;
import nl.ramondevaan.adventofcode2018.util.MutableIntMap;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class Day17 {

  private final static int EMPTY = 0;
  private final static int SAND = 1;
  private final static int WATER = 2;
  private final static int FALLING_WATER = WATER | (WATER << 1);
  private final List<Vein> veins;

  public Day17(final List<String> lines) {
    final var parser = new VeinParser();
    this.veins = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var water = solve();
    return water.water + water.fallingWater;
  }

  public long solve2() {
    return solve().water;
  }

  private Water solve() {
    final var mapAndMin = toMap(veins);
    final var map = mapAndMin.right;
    final var rowOffset = mapAndMin.left.row;
    final var columnOffset = mapAndMin.left.column;

    final var sourceRow = Math.max(-1, -rowOffset);
    final var sourceColumn = 500 - columnOffset;

    return dropWater(map, sourceRow, sourceColumn);
  }

  private static Water dropWater(final MutableIntMap map, final int row, final int column) {
    var down = down(map, row, column);
    var water = new Water(0, down[0]);
    int nextRow = down[1];
    int nextValue = down[2];

    if (nextValue == FALLING_WATER || nextRow == map.rows() || water.fallingWater == 0) {
      return water;
    }

    int lastRow, previous;
    Edge left;
    Edge right;

    for (previous = nextRow, lastRow = nextRow - 1; true; lastRow--, previous--) {
      if (lastRow == row) {
        return water;
      }
      while ((left = findEdge(map, lastRow, previous, column, true)).dropWater) {
        water.add(dropWater(map, lastRow, left.column));
      }
      while ((right = findEdge(map, lastRow, previous, column, false)).dropWater) {
        water.add(dropWater(map, lastRow, right.column));
      }
      if (left.continueUp && right.continueUp) {
        water.water += right.column - left.column + 1;
        water.fallingWater--;
        set(map, lastRow, left.column, right.column, WATER);
        continue;
      }
      water.fallingWater += right.column - left.column;
      set(map, lastRow, left.column, right.column, FALLING_WATER);
      return water;
    }
  }

  private static void set(final MutableIntMap map, final int row, int columnFrom, final int columnTo, final int value) {
    for (int column = columnFrom; column <= columnTo; column++) {
      map.setValueAt(row, column, value);
    }
  }

  private static int[] down(final MutableIntMap map, final int row, final int column) {
    var water = 0;
    int nextValue;
    int nextRow = row + 1;

    for (; nextRow < map.rows(); nextRow++) {
      if ((nextValue = map.valueAt(nextRow, column)) != EMPTY) {
        return new int[]{water, nextRow, nextValue};
      }
      map.setValueAt(nextRow, column, FALLING_WATER);
      water++;
    }

    return new int[] {water, nextRow, EMPTY};
  }

  private static Edge findEdge(final MutableIntMap map, final int row, final int rowBelow, int column, boolean left) {
    final var offset = left ? -1 : 1;

    for (column += offset; true; ) {
      final var neighbor = map.valueAt(row, column);
      if (neighbor == SAND) {
        return new Edge(false, true, column - offset);
      } else if (neighbor == FALLING_WATER) {
        return new Edge(false, false, column);
      }
      final var below = map.valueAt(rowBelow, column);
      if (below == EMPTY) {
        return new Edge(true, false, column);
      } else if (below == FALLING_WATER) {
        return new Edge(false, false, column);
      }
      column += offset;
    }
  }

  private static ImmutablePair<Coordinate, MutableIntMap> toMap(final List<Vein> veins) {
    int rowMin = Integer.MAX_VALUE, rowMax = Integer.MIN_VALUE;
    int columnMin = Integer.MAX_VALUE, columnMax = Integer.MIN_VALUE;

    for (final var vein : veins) {
      rowMin = Ints.min(vein.from.row, vein.to.row, rowMin);
      rowMax = Ints.max(vein.from.row, vein.to.row, rowMax);
      columnMin = Ints.min(vein.from.column, vein.to.column, columnMin);
      columnMax = Ints.max(vein.from.column, vein.to.column, columnMax);
    }
    columnMin--;
    columnMax++;

    final var rows = rowMax - rowMin + 1;
    final var columns = columnMax - columnMin + 1;

    final var map = new MutableIntMap(rows, columns);

    for (final var vein : veins) {
      int fromRow = vein.from.row - rowMin;
      int toRow = vein.to.row - rowMin;
      int fromColumn = vein.from.column - columnMin;
      int toColumn = vein.to.column - columnMin;
      int rowDiff = Integer.signum(toRow - fromRow);
      int columnDiff = Integer.signum(toColumn - fromColumn);
      for (int row = fromRow, column = fromColumn; row <= toRow && column <= toColumn; row += rowDiff, column += columnDiff) {
        map.setValueAt(row, column, SAND);
      }
    }

    return new ImmutablePair<>(new Coordinate(rowMin, columnMin), map);
  }
}
