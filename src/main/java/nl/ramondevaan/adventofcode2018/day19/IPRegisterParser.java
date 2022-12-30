package nl.ramondevaan.adventofcode2018.day19;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class IPRegisterParser implements Parser<String, Integer> {

  private final static char[] IP = new char[] {'#', 'i', 'p', ' '};

  @Override
  public Integer parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(IP);
    final var ret = parser.parseInteger();
    parser.verifyIsDone();
    return ret;
  }
}
