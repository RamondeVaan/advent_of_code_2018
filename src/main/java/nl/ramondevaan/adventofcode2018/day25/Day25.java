package nl.ramondevaan.adventofcode2018.day25;

import java.util.List;

public class Day25 {

  private final List<Position> positions;

  public Day25(final List<String> lines) {
    final var parser = new PositionParser();
    this.positions = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var sizeMinus1 = positions.size() - 1;
    final var parents = new Integer[positions.size()];
    var constellations = positions.size();

    for (int i = 0; i < sizeMinus1; i++) {
      for (int j = i + 1; j < positions.size(); j++) {
        if (manhattanDistance(positions.get(i), positions.get(j)) <= 3) {
          final var iParent = getParent(parents, i);
          final var jParent = getParent(parents, j);

          if (iParent != jParent) {
            parents[jParent] = iParent;
            constellations--;
          }
        }
      }
    }

    return constellations;
  }

  private int getParent(final Integer[] parents, int i) {
    var parent = parents[i];
    if (parent == null) {
      return i;
    }
    parent = getParent(parents, parent);
    parents[i] = parent;
    return parent;
  }

  private int manhattanDistance(final Position p1, final Position p2) {
    return Math.abs(p2.x - p1.x) +
      Math.abs(p2.y - p1.y) +
      Math.abs(p2.z - p1.z) +
      Math.abs(p2.w - p1.w);
  }
}
