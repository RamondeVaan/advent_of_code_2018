package nl.ramondevaan.adventofcode2018.day19.instruction;

public class BanI extends Instruction {
  public BanI(final int a, final int b, final int c) {
    super(a, b, c);
  }

  @Override
  public void apply(final int[] reg) {
    reg[c] = reg[a] & b;
  }
}
