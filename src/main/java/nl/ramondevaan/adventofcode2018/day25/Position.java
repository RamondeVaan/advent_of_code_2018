package nl.ramondevaan.adventofcode2018.day25;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class Position {
  public final int x;
  public final int y;
  public final int z;
  public final int w;
}
