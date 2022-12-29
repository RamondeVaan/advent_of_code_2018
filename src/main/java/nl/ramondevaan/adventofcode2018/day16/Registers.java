package nl.ramondevaan.adventofcode2018.day16;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Registers {
  public final List<Integer> registers;

  public int get(final int i) {
    return registers.get(i);
  }

  public Registers with(final int register, final int value) {
    final var ret = new ArrayList<>(registers);
    ret.set(register, value);
    return new Registers(Collections.unmodifiableList(ret));
  }
}
