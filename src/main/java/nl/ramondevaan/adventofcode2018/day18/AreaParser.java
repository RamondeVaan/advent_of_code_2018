package nl.ramondevaan.adventofcode2018.day18;

import nl.ramondevaan.adventofcode2018.util.IntMap;
import nl.ramondevaan.adventofcode2018.util.Parser;

import java.util.List;

public class AreaParser implements Parser<List<String>, IntMap> {
  @Override
  public IntMap parse(final List<String> toParse) {
    final int rows = toParse.size();
    final int columns = rows == 0 ? 0 : toParse.get(0).length();

    final var builder = IntMap.builder(rows, columns);

    for (int row = 0; row < rows; row++) {
      final var chars = toParse.get(row).toCharArray();
      for (int column = 0; column < columns; column++) {
        switch (chars[column]) {
          case '|' -> builder.set(row, column, Acre.TREE);
          case '#' -> builder.set(row, column, Acre.LUMBERYARD);
          case '.' -> {}
          default -> throw new IllegalArgumentException();
        }
      }
    }

    return builder.build();
  }
}
