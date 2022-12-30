package nl.ramondevaan.adventofcode2018.day19.instruction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Instruction {
  public final int a;
  public final int b;
  public final int c;

  public abstract void apply(final int[] reg);
}
