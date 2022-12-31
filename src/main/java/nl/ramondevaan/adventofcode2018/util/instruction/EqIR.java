package nl.ramondevaan.adventofcode2018.util.instruction;

public class EqIR extends Instruction {
  public EqIR(final int a, final int b, final int c) {
    super(a, b, c);
  }

  @Override
  public void apply(final int[] reg) {
    reg[c] = a == reg[b] ? 1 : 0;
  }
}
