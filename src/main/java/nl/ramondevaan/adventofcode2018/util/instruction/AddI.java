package nl.ramondevaan.adventofcode2018.util.instruction;

public class AddI extends Instruction {
  public AddI(final int a, final int b, final int c) {
    super(a, b, c);
  }

  @Override
  public void apply(final int[] reg) {
    reg[c] = reg[a] + b;
  }
}
