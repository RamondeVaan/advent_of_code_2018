package nl.ramondevaan.adventofcode2018.day16;

@FunctionalInterface
public interface Instruction {
  Registers apply(final NumberedInstruction inst, final Registers reg);
}
