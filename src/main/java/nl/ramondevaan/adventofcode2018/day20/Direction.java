package nl.ramondevaan.adventofcode2018.day20;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1);

  public final int rowOffset;
  public final int columnOffset;
}
