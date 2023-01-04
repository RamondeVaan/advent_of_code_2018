package nl.ramondevaan.adventofcode2018.day24;

import lombok.ToString;

import java.util.Set;

@ToString(of = {"armyName", "id"})
public class MutableGroup {
  public final String armyName;
  public final int id;
  public final int hitPoints;
  public final int initiative;
  public final Set<String> weakTo;
  public final Set<String> immuneTo;
  public final String damageType;
  public final int damage;
  public int units;
  public int effectivePower;

  public MutableGroup(final String armyName, final int id, final int units, final int hitPoints, final int initiative,
                      final Set<String> weakTo, final Set<String> immuneTo, final String damageType, final int damage) {
    this.armyName = armyName;
    this.id = id;
    this.units = units;
    this.hitPoints = hitPoints;
    this.initiative = initiative;
    this.weakTo = weakTo;
    this.immuneTo = immuneTo;
    this.damageType = damageType;
    this.damage = damage;
    this.effectivePower = damage * units;
  }
}
