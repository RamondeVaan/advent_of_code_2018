package nl.ramondevaan.adventofcode2018.day23;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class NanobotParser implements Parser<String, Nanobot> {

  private final static char[] POS = new char[] {'p', 'o', 's', '=', '<'};
  private final static char[] RANGE = new char[] {'>', ',', ' ', 'r', '='};

  @Override
  public Nanobot parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(POS);
    final var x = parser.parseLong();
    parser.consume(',');
    final var y = parser.parseLong();
    parser.consume(',');
    final var z = parser.parseLong();
    parser.consume(RANGE);
    final var range = parser.parseLong();
    parser.verifyIsDone();

    return new Nanobot(new Position(x, y, z), range);
  }
}
