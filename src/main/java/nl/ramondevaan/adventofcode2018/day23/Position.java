package nl.ramondevaan.adventofcode2018.day23;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Position {
  public final long x;
  public final long y;
  public final long z;

  public long manhattanDistance(final Position to) {
    return Math.abs(to.x - x) + Math.abs(to.y - y) + Math.abs(to.z - z);
  }
}
