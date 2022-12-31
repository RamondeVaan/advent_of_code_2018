package nl.ramondevaan.adventofcode2018.day20;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.adventofcode2018.util.IntMap;

@RequiredArgsConstructor
public class Map {
  public final static int ROOM = 1;
  public final static int DOOR = 2;

  public final int startRow;
  public final int startColumn;
  public final IntMap map;
}
