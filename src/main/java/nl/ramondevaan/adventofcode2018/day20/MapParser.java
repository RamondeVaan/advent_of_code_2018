package nl.ramondevaan.adventofcode2018.day20;

import nl.ramondevaan.adventofcode2018.util.Coordinate;
import nl.ramondevaan.adventofcode2018.util.IntMap;
import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

import java.util.*;

public class MapParser implements Parser<String, Map> {
  private final static int ROW = 500;
  private final static int COLUMN = 500;
  private final static int ROWS = ROW * 2;
  private final static int COLUMNS = COLUMN * 2;

  @Override
  public Map parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    final var builder = IntMap.builder(ROWS, COLUMNS);
    parse(builder, parser);
    parser.verifyIsDone();

    final var bounds = findBounds(builder);
    final var map = resize(builder, bounds);
    final var startRow = ROW - bounds.rowMin;
    final var startColumn = COLUMN - bounds.columnMin;

    return new Map(startRow, startColumn, map);
  }

  private void parse(final IntMap.Builder map, final StringIteratorParser parser) {
    final var from = Set.of(new Coordinate(ROW, COLUMN));
    map.set(ROW, COLUMN, Map.ROOM);
    parser.consume('^');

    var last = handleDirections(map, from, parser);

    while (true) {
      final var c = parser.current();
      parser.consume();
      switch (c) {
        case '(' -> last = handleDirections(map, parse(map, last, parser), parser);
        case '|' -> last = handleDirections(map, from, parser);
        case '$' -> {
          return;
        }
        default -> throw new IllegalArgumentException();
      }
    }
  }

  private Set<Coordinate> parse(final IntMap.Builder map, final Set<Coordinate> from,
                                final StringIteratorParser parser) {
    var last = handleDirections(map, from, parser);
    final var ret = new HashSet<>(last);

    while (true) {
      final var c = parser.current();
      parser.consume();
      switch (c) {
        case '(' -> ret.addAll(last = handleDirections(map, parse(map, last, parser), parser));
        case '|' -> ret.addAll(last = handleDirections(map, from, parser));
        case ')' -> {
          return Collections.unmodifiableSet(ret);
        }
      }
    }
  }

  private Set<Coordinate> handleDirections(final IntMap.Builder map, final Set<Coordinate> from,
                                           final StringIteratorParser parser) {
    final var chars = parser.parseString().toCharArray();
    final var ret = new ArrayList<>(from);

    for (final var c : chars) {
      int rowOffset = 0, columnOffset = 0;

      switch (c) {
        case 'N' -> rowOffset = -1;
        case 'E' -> columnOffset = 1;
        case 'S' -> rowOffset = 1;
        case 'W' -> columnOffset = -1;
        default -> throw new IllegalArgumentException();
      }

      for (int i = 0; i < ret.size(); i++) {
        final var old = ret.get(i);
        map.set(old.row + rowOffset, old.column + columnOffset, Map.DOOR);
        final var newCoord = new Coordinate(old.row + 2 * rowOffset, old.column + 2 * columnOffset);
        map.set(newCoord.row, newCoord.column, Map.ROOM);
        ret.set(i, newCoord);
      }
    }

    return Set.copyOf(ret);
  }

  private Bounds findBounds(final IntMap.Builder builder) {
    int rowMin = Integer.MAX_VALUE, rowMax = Integer.MIN_VALUE;
    int columnMin = Integer.MAX_VALUE, columnMax = Integer.MIN_VALUE;

    for (int row = 0; row < builder.rows; row++) {
      for (int column = 0; column < builder.columns; column++) {
        if (builder.get(row, column) != 0) {
          rowMin = Math.min(rowMin, row);
          rowMax = Math.max(rowMax, row);
          columnMin = Math.min(columnMin, column);
          columnMax = Math.max(columnMax, column);
        }
      }
    }

    rowMax++;
    columnMax++;
    columnMin--;
    rowMin--;

    return new Bounds(rowMin, rowMax, columnMin, columnMax);
  }

  private IntMap resize(final IntMap.Builder builder, final Bounds bounds) {
    final var rows = bounds.rowMax - bounds.rowMin + 1;
    final var columns = bounds.columnMax - bounds.columnMin + 1;

    final var newBuilder = IntMap.builder(rows, columns);


    for (int row = 0, oldRow = bounds.rowMin; row < rows; row++, oldRow++) {
      for (int column = 0, oldColumn = bounds.columnMin; column < columns; column++, oldColumn++) {
        newBuilder.set(row, column, builder.get(oldRow, oldColumn));
      }
    }

    return newBuilder.build();
  }
}
