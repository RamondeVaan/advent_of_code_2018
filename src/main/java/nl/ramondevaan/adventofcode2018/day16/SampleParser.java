package nl.ramondevaan.adventofcode2018.day16;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SampleParser implements Parser<List<String>, Sample> {
  private final static char[] BEFORE = "Before: [".toCharArray();
  private final static char[] AFTER = "After:  [".toCharArray();
  private final static char[] REGISTER_NUMBER_SEPARATOR = ", ".toCharArray();
  private final static char CLOSE_REGISTERS = ']';
  private final InstructionParser instructionParser;

  @Override
  public Sample parse(final List<String> toParse) {
    return new Sample(
      parseBefore(toParse.get(0)),
      parserAfter(toParse.get(2)),
      instructionParser.parse(toParse.get(1))
    );
  }

  private Registers parseBefore(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(BEFORE);
    return new Registers(parseRegisters(parser));
  }

  private Registers parserAfter(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(AFTER);
    return new Registers(parseRegisters(parser));
  }

  private List<Integer> parseRegisters(final StringIteratorParser parser) {
    final var ret = new ArrayList<Integer>();

    do {
      ret.add(parser.parseInteger());
    } while (parser.tryConsume(REGISTER_NUMBER_SEPARATOR));

    parser.consume(CLOSE_REGISTERS);
    parser.verifyIsDone();

    return Collections.unmodifiableList(ret);
  }
}
