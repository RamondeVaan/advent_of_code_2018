package nl.ramondevaan.adventofcode2018.day24;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.adventofcode2018.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ArmyParser implements Parser<List<String>, Army> {

  private final GroupParser parser;

  @Override
  public Army parse(final List<String> toParse) {
    final var firstLine = toParse.get(0);
    if (!firstLine.endsWith(":")) {
      throw new IllegalArgumentException();
    }
    final var name = firstLine.substring(0, firstLine.length() - 1);
    final var groups = new ArrayList<Group>(toParse.size() - 1);
    for (int i = 1; i < toParse.size(); i++) {
      groups.add(parser.parse(toParse.get(i)).id(i).armyName(name).build());
    }

    return new Army(name, Collections.unmodifiableList(groups));
  }
}
