package nl.ramondevaan.adventofcode2018.day24;

import nl.ramondevaan.adventofcode2018.util.Parser;
import nl.ramondevaan.adventofcode2018.util.StringIteratorParser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GroupParser implements Parser<String, Group.GroupBuilder> {

  private final char[] UNITS = new char[]{' ', 'u', 'n', 'i', 't', 's', ' ', 'e', 'a', 'c', 'h', ' ',
    'w', 'i', 't', 'h', ' '};
  private final char[] HIT_POINTS = new char[]{' ', 'h', 'i', 't', ' ', 'p', 'o', 'i', 'n', 't', 's', ' '};
  private final char[] WEAK_TO = new char[]{'w', 'e', 'a', 'k', ' ', 't', 'o', ' '};
  private final char[] IMMUNE_TO = new char[]{'i', 'm', 'm', 'u', 'n', 'e', ' ', 't', 'o', ' '};
  private final char[] ATTACK = new char[]{'w', 'i', 't', 'h', ' ', 'a', 'n', ' ', 'a', 't', 't', 'a', 'c', 'k',
    ' ', 't', 'h', 'a', 't', ' ', 'd', 'o', 'e', 's', ' '};
  private final char[] INITIATIVE = new char[]{' ', 'd', 'a', 'm', 'a', 'g', 'e', ' ', 'a', 't', ' ',
    'i', 'n', 'i', 't', 'i', 'a', 't', 'i', 'v', 'e', ' '};
  private final char[] SEPARATOR = new char[]{',', ' '};
  private final char[] COLON_SEPARATOR = new char[]{';', ' '};
  private final char[] CLOSE_SEPARATOR = new char[]{')', ' '};

  @Override
  public Group.GroupBuilder parse(final String toParse) {
    final var builder = Group.builder();
    final var parser = new StringIteratorParser(toParse);

    builder.units(parser.parseInteger());
    parser.consume(UNITS);
    builder.hitPoints(parser.parseInteger());
    parser.consume(HIT_POINTS);

    Set<String> weakTo = null, immuneTo = null;

    if (parser.current() == '(') {
      parser.consume();
      do {
        switch (parser.current()) {
          case 'w' -> {
            parser.consume(WEAK_TO);
            weakTo = parseStrings(parser);
          }
          case 'i' -> {
            parser.consume(IMMUNE_TO);
            immuneTo = parseStrings(parser);
          }
        }
      } while (parser.tryConsume(COLON_SEPARATOR));
      parser.consume(CLOSE_SEPARATOR);
    }

    parser.consume(ATTACK);
    builder.damage(parser.parseInteger());
    parser.consume(' ');
    builder.damageType(parser.parseString());
    parser.consume(INITIATIVE);
    builder.initiative(parser.parseInteger());
    parser.verifyIsDone();

    builder.weakTo(weakTo == null ? Set.of() : Collections.unmodifiableSet(weakTo));
    builder.immuneTo(immuneTo == null ? Set.of() : Collections.unmodifiableSet(immuneTo));

    return builder;
  }

  private Set<String> parseStrings(final StringIteratorParser parser) {
    final var ret = new HashSet<String>();
    ret.add(parser.parseString());

    while (parser.current() == ',') {
      parser.consume(SEPARATOR);
      ret.add(parser.parseString());
    }

    return ret;
  }
}
