package nl.ramondevaan.adventofcode2018.day17;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Water {
  public int water;
  public int fallingWater;

  public void add(final Water water) {
    this.water += water.water;
    this.fallingWater += water.fallingWater;
  }
}
