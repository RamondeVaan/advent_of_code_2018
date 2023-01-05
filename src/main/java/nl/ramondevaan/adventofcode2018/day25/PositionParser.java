package nl.ramondevaan.adventofcode2018.day25;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

public class PositionParser implements Parser<String, Position> {
  @Override
  public Position parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var builder = Position.builder();

    builder.x(parser.parseInteger());
    parser.consume(',');
    builder.y(parser.parseInteger());
    parser.consume(',');
    builder.z(parser.parseInteger());
    parser.consume(',');
    builder.w(parser.parseInteger());
    parser.verifyIsDone();

    return builder.build();
  }
}
