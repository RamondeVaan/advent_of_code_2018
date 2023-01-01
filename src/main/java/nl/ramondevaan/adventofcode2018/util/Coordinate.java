package nl.ramondevaan.adventofcode2018.util;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Coordinate {
  public final int row;
  public final int column;

  public Coordinate() {
    this(0, 0);
  }

  public int row() {
    return row;
  }

  public int column() {
    return column;
  }

  public Stream<Coordinate> directNeighbors() {
    return Stream.of(
      new Coordinate(row + 1, column),
      new Coordinate(row, column + 1),
      new Coordinate(row - 1, column),
      new Coordinate(row, column - 1)
    );
  }

  public Stream<Coordinate> allNeighbors() {
    return Stream.of(
      new Coordinate(row + 1, column),
      new Coordinate(row, column + 1),
      new Coordinate(row - 1, column),
      new Coordinate(row, column - 1),
      new Coordinate(row - 1, column - 1),
      new Coordinate(row + 1, column - 1),
      new Coordinate(row - 1, column + 1),
      new Coordinate(row + 1, column + 1)
    );
  }

  public static Coordinate of(int row, int column) {
    return new Coordinate(row, column);
  }
}
