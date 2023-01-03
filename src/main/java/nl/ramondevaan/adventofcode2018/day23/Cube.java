package nl.ramondevaan.adventofcode2018.day23;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString(of = {"x", "y", "z", "xSize", "ySize", "zSize"})
public class Cube {
  public final long x;
  public final long y;
  public final long z;
  public final long manhattanDistanceToOrigin;
  public final long xMax;
  public final long yMax;
  public final long zMax;
  public final long xSize;
  public final long ySize;
  public final long zSize;
  public final long size;

  public Cube(final long x, final long y, final long z, final long xSize, final long ySize, final long zSize) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.manhattanDistanceToOrigin = x + y + z;
    this.xMax = x + xSize - 1;
    this.yMax = y + ySize - 1;
    this.zMax = z + zSize - 1;
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    this.size = xSize * ySize * zSize;
  }

  public Cube[] split() {
    final var xSizeDiv2 = xSize / 2;
    final var ySizeDiv2 = ySize / 2;
    final var zSizeDiv2 = zSize / 2;
    final var xSizeRemaining = xSize - xSizeDiv2;
    final var ySizeRemaining = ySize - ySizeDiv2;
    final var zSizeRemaining = zSize - zSizeDiv2;
    final var xHalfway = x + xSizeDiv2;
    final var yHalfway = y + ySizeDiv2;
    final var zHalfway = z + zSizeDiv2;

    if (xSize == 1) {
      if (ySize == 1) {
        if (zSize == 1) {
          return new Cube[]{this};
        }
        return new Cube[]{
          new Cube(x, y, z, xSize, ySize, zSizeDiv2),
          new Cube(x, y, zHalfway, xSize, ySize, zSizeRemaining)
        };
      }
      return new Cube[]{
        new Cube(x, y, z, xSize, ySizeDiv2, zSizeDiv2),
        new Cube(x, y, zHalfway, xSize, ySizeDiv2, zSizeRemaining),
        new Cube(x, yHalfway, z, xSize, ySizeRemaining, zSizeDiv2),
        new Cube(x, yHalfway, zHalfway, xSize, ySizeRemaining, zSizeRemaining)
      };
    }
    if (y == 1) {
      if (z == 1) {
        return new Cube[]{
          new Cube(x, y, z, xSizeDiv2, ySize, zSize),
          new Cube(xHalfway, y, z, xSizeRemaining, ySize, zSize)
        };
      }
      return new Cube[]{
        new Cube(x, y, z, xSizeDiv2, ySize, zSizeDiv2),
        new Cube(x, y, zHalfway, xSizeDiv2, ySize, zSizeRemaining),
        new Cube(xHalfway, y, z, xSizeRemaining, ySize, zSizeDiv2),
        new Cube(xHalfway, y, zHalfway, xSizeRemaining, ySize, zSizeRemaining)
      };
    }
    if (z == 1) {
      return new Cube[]{
        new Cube(x, y, z, xSizeDiv2, ySizeDiv2, zSize),
        new Cube(x, yHalfway, z, xSizeDiv2, ySizeRemaining, zSize),
        new Cube(xHalfway, y, z, xSizeRemaining, ySizeDiv2, zSize),
        new Cube(xHalfway, yHalfway, z, xSizeRemaining, ySizeRemaining, zSize)
      };
    }
    return new Cube[]{
      new Cube(x, y, z, xSizeDiv2, ySizeDiv2, zSizeDiv2),
      new Cube(x, y, zHalfway, xSizeDiv2, ySizeDiv2, zSizeRemaining),
      new Cube(x, yHalfway, z, xSizeDiv2, ySizeRemaining, zSizeDiv2),
      new Cube(x, yHalfway, zHalfway, xSizeDiv2, ySizeRemaining, zSizeRemaining),
      new Cube(xHalfway, y, z, xSizeRemaining, ySizeDiv2, zSizeDiv2),
      new Cube(xHalfway, y, zHalfway, xSizeRemaining, ySizeDiv2, zSizeRemaining),
      new Cube(xHalfway, yHalfway, z, xSizeRemaining, ySizeRemaining, zSizeDiv2),
      new Cube(xHalfway, yHalfway, zHalfway, xSizeRemaining, ySizeRemaining, zSizeRemaining)
    };
  }

  public boolean intersects(final Nanobot nanobot) {
    return manhattanDistance(nanobot) <= nanobot.range;
  }

  public long manhattanDistance(final Nanobot nanobot) {
    final var xDist = nanobot.position.x < x ? x - nanobot.position.x :
      nanobot.position.x > xMax ? nanobot.position.x - xMax : 0;
    final var yDist = nanobot.position.y < y ? y - nanobot.position.y :
      nanobot.position.y > yMax ? nanobot.position.y - yMax : 0;
    final var zDist = nanobot.position.z < z ? z - nanobot.position.z :
      nanobot.position.z > zMax ? nanobot.position.z - zMax : 0;

    return xDist + yDist + zDist;
  }
}
