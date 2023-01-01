package nl.ramondevaan.adventofcode2018.day22;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class DepthParser implements Parser<String, Integer> {

  private final static char[] DEPTH = new char[] {'d', 'e', 'p', 't', 'h', ':', ' '};

  @Override
  public Integer parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    parser.consume(DEPTH);
    final var depth = parser.parseInteger();
    parser.verifyIsDone();

    return depth;
  }
}
