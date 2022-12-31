package nl.ramondevaan.adventofcode2018.util.instruction;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class InstructionParser implements Parser<String, Instruction> {
  @Override
  public Instruction parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    final var opcode = parser.parseString();
    parser.consume(' ');
    final var a = parser.parseInteger();
    parser.consume(' ');
    final var b = parser.parseInteger();
    parser.consume(' ');
    final var c = parser.parseInteger();
    parser.verifyIsDone();

    return switch (opcode) {
      case "addr" -> new AddR(a, b, c);
      case "addi" -> new AddI(a, b, c);
      case "mulr" -> new MulR(a, b, c);
      case "muli" -> new MulI(a, b, c);
      case "banr" -> new BanR(a, b, c);
      case "bani" -> new BanI(a, b, c);
      case "borr" -> new BorR(a, b, c);
      case "bori" -> new BorI(a, b, c);
      case "setr" -> new SetR(a, b, c);
      case "seti" -> new SetI(a, b, c);
      case "gtir" -> new GtIR(a, b, c);
      case "gtri" -> new GtRI(a, b, c);
      case "gtrr" -> new GtRR(a, b, c);
      case "eqir" -> new EqIR(a, b, c);
      case "eqri" -> new EqRI(a, b, c);
      case "eqrr" -> new EqRR(a, b, c);
      default -> throw new IllegalArgumentException();
    };
  }
}
