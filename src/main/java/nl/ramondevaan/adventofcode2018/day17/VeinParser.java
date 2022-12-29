package nl.ramondevaan.adventofcode2018.day17;

import nl.ramondevaan.adventofcode2018.util.Coordinate;
import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class VeinParser implements Parser<String, Vein> {

  private final static char[] SEPARATOR = new char[] {',', ' '};
  private final static char[] DOTS = new char[] {'.', '.'};

  @Override
  public Vein parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final boolean startsWithX = parser.current() == 'x';
    parser.consume();
    parser.consume('=');
    final var first = parser.parseInteger();
    parser.consume(SEPARATOR);
    parser.consume();
    parser.consume('=');
    final var second = parser.parseInteger();
    parser.consume(DOTS);
    final var third = parser.parseInteger();
    parser.verifyIsDone();

    if (startsWithX) {
      return new Vein(new Coordinate(second, first), new Coordinate(third, first));
    }

    return new Vein(new Coordinate(first, second), new Coordinate(first, third));
  }
}
