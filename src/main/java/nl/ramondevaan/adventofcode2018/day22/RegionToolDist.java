package nl.ramondevaan.adventofcode2018.day22;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class RegionToolDist {
  public final int row;
  public final int column;
  public final int tool;
  public final int distance;
  public final RegionToolDist last;

}
