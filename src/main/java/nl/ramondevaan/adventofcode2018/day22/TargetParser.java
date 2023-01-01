package nl.ramondevaan.adventofcode2018.day22;

import nl.ramondevaan.adventofcode2018.util.Coordinate;
import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class TargetParser implements Parser<String, Coordinate> {

  private final static char[] TARGET = new char[] {'t', 'a', 'r', 'g', 'e', 't', ':', ' '};

  @Override
  public Coordinate parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    parser.consume(TARGET);
    final var column = parser.parseInteger();
    parser.consume(',');
    final var row = parser.parseInteger();
    parser.verifyIsDone();

    return new Coordinate(row, column);
  }
}
