package nl.ramondevaan.adventofcode2018.day24;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "name")
@Builder
public class Army {
  public final String name;
  public final List<Group> groups;

  public MutableArmy asMutableArmy(int boost) {
    return new MutableArmy(name, groups.stream()
      .map(group -> group.asMutableGroup(boost))
      .collect(toCollection(ArrayList::new)));
  }
}
