package nl.ramondevaan.adventofcode2018.day24;

import lombok.ToString;

import java.util.Set;

@ToString(of = {"armyName", "id"})
public class Group {
  public final String armyName;
  public final int id;
  public final int units;
  public final int hitPoints;
  public final int initiative;
  public final Set<String> weakTo;
  public final Set<String> immuneTo;
  public final String damageType;
  public final int damage;
  public final int effectivePower;

  public Group(final String armyName, final int id, final int units, final int hitPoints, final int initiative,
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

  public MutableGroup asMutableGroup(final int boost) {
    return new MutableGroup(armyName, id, units, hitPoints, initiative, weakTo, immuneTo, damageType, damage + boost);
  }

  public static GroupBuilder builder() {
    return new GroupBuilder();
  }

  public static class GroupBuilder {
    private int id;
    private String armyName;
    private int units;
    private int hitPoints;
    private int initiative;
    private Set<String> weakTo;
    private Set<String> immuneTo;
    private String damageType;
    private int damage;

    GroupBuilder() {
    }

    public GroupBuilder id(int id) {
      this.id = id;
      return this;
    }

    public GroupBuilder armyName(String armyName) {
      this.armyName = armyName;
      return this;
    }

    public GroupBuilder units(int units) {
      this.units = units;
      return this;
    }

    public GroupBuilder hitPoints(int hitPoints) {
      this.hitPoints = hitPoints;
      return this;
    }

    public GroupBuilder initiative(int initiative) {
      this.initiative = initiative;
      return this;
    }

    public GroupBuilder weakTo(Set<String> weakTo) {
      this.weakTo = weakTo;
      return this;
    }

    public GroupBuilder immuneTo(Set<String> immuneTo) {
      this.immuneTo = immuneTo;
      return this;
    }

    public GroupBuilder damageType(String damageType) {
      this.damageType = damageType;
      return this;
    }

    public GroupBuilder damage(int damage) {
      this.damage = damage;
      return this;
    }

    public Group build() {
      return new Group(armyName, id, units, hitPoints, initiative, weakTo, immuneTo, damageType, damage);
    }
  }
}
