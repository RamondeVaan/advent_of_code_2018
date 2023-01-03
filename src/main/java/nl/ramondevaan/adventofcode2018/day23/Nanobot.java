package nl.ramondevaan.adventofcode2018.day23;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Nanobot {
  public final Position position;
  public final long range;

  public long manhattanDistance(final Nanobot to) {
    return position.manhattanDistance(to.position);
  }

  public boolean isInRange(final Nanobot nanobot) {
    return manhattanDistance(nanobot) <= range;
  }
}
