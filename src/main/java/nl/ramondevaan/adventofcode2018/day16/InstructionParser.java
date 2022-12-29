package nl.ramondevaan.adventofcode2018.day16;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class InstructionParser implements Parser<String, NumberedInstruction> {

  private final static char INSTRUCTION_NUMBER_SEPARATOR = ' ';
  @Override
  public NumberedInstruction parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var opcode = parser.parseInteger();
    parser.consume(INSTRUCTION_NUMBER_SEPARATOR);
    final var a = parser.parseInteger();
    parser.consume(INSTRUCTION_NUMBER_SEPARATOR);
    final var b = parser.parseInteger();
    parser.consume(INSTRUCTION_NUMBER_SEPARATOR);
    final var c = parser.parseInteger();
    parser.verifyIsDone();

    return new NumberedInstruction(opcode, a, b, c);
  }
}
