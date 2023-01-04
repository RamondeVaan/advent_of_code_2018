package nl.ramondevaan.adventofcode2018.day24;

import nl.ramondevaan.adventofcode2018.util.BlankStringPartitioner;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class Day24 {

  private final List<Army> armies;

  public Day24(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var partitions = partitioner.partition(lines);
    final var groupParser = new GroupParser();
    final var armyParser = new ArmyParser(groupParser);

    this.armies = partitions.stream().map(armyParser::parse).toList();
  }

  public long solve1() {
    return score(solve(0).orElseThrow());
  }

  public long solve2() {
    for (int boost = 1; true; boost++) {
      final var result = solve(boost);
      if (result.filter(winner -> winner.name.equals("Immune System")).isPresent()) {
        return score(result.get());
      }
    }
  }

  private int score(final MutableArmy winner) {
    return winner.groups.stream().mapToInt(group -> group.units).sum();
  }

  private Optional<MutableArmy> solve(final int boost) {
    var mutableArmies = mutableArmies(boost);

    do {
      if (fight(mutableArmies) == 0) {
        return Optional.empty();
      }
    } while (mutableArmies.stream().filter(army -> !army.groups.isEmpty()).count() > 1);

    return Optional.of(mutableArmies.stream().filter(army -> !army.groups.isEmpty()).findFirst().orElseThrow());
  }

  private List<MutableArmy> mutableArmies(int boost) {
    return armies.stream()
      .map(army -> army.name.equals("Immune System") ? army.asMutableArmy(boost) : army.asMutableArmy(0))
      .toList();
  }

  private static int fight(final List<MutableArmy> armies) {
    final var attacks = target(armies);
    return attack(armies, attacks);
  }

  private static List<Attack> target(final List<MutableArmy> armies) {
    final var attacks = new ArrayList<Attack>();
    final var targeted = new HashSet<MutableGroup>();

    for (final var army : armies) {
      final var otherArmies = otherArmies(armies, army);
      final var otherGroups = otherArmies.stream().flatMap(otherArmy -> otherArmy.groups.stream())
        .filter(not(targeted::contains)).collect(Collectors.toCollection(ArrayList::new));
      army.groups.stream()
        .sorted(Comparator.<MutableGroup>comparingInt(group -> group.effectivePower)
          .thenComparingInt(group -> group.initiative).reversed())
        .forEachOrdered(group -> target(group, otherGroups).ifPresent(attack -> {
          targeted.add(attack.defender);
          otherGroups.remove(attack.defender);
          attacks.add(attack);
        }));
    }

    attacks.sort(Comparator.<Attack>comparingInt(attack -> attack.attacker.initiative).reversed());

    return Collections.unmodifiableList(attacks);
  }

  private static Optional<Attack> target(final MutableGroup attacker, final List<MutableGroup> defenders) {
    return defenders.stream()
      .filter(defender -> !defender.immuneTo.contains(attacker.damageType))
      .max(Comparator.<MutableGroup>comparingInt(defender -> computeDamage(attacker, defender))
        .thenComparingInt(defender -> defender.effectivePower)
        .thenComparingInt(defender -> defender.initiative))
      .map(defender -> new Attack(attacker, defender));
  }

  private static int computeDamage(final MutableGroup attacker, final MutableGroup defender) {
    if (defender.immuneTo.contains(attacker.damageType)) {
      return 0;
    }
    if (defender.weakTo.contains(attacker.damageType)) {
      return attacker.effectivePower * 2;
    }

    return attacker.effectivePower;
  }

  private static List<MutableArmy> otherArmies(final List<MutableArmy> armies, final MutableArmy army) {
    final var ret = new ArrayList<>(armies);
    ret.removeIf(someArmy -> someArmy.equals(army));
    return Collections.unmodifiableList(ret);
  }

  private static int attack(final List<MutableArmy> armies, final List<Attack> attacks) {
    int killed = attacks.stream().filter(attack -> attack.attacker.units > 0)
      .mapToInt(attack -> damage(attack.defender, computeDamage(attack.attacker, attack.defender)))
      .sum();

    armies.forEach(army -> army.groups.removeIf(group -> group.units <= 0));
    return killed;
  }

  private static int damage(final MutableGroup group, final int damage) {
    int deaths = Math.min(damage / group.hitPoints, group.units);
    group.units -= deaths;
    group.effectivePower = group.units * group.damage;
    return deaths;
  }
}
