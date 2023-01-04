package nl.ramondevaan.adventofcode2018.day24;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Attack {
  public final MutableGroup attacker;
  public final MutableGroup defender;
}
