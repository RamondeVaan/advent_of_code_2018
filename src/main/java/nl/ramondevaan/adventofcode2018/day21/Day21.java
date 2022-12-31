package nl.ramondevaan.adventofcode2018.day21;

import nl.ramondevaan.adventofcode2018.util.instruction.IPRegisterParser;
import nl.ramondevaan.adventofcode2018.util.instruction.Instruction;
import nl.ramondevaan.adventofcode2018.util.instruction.InstructionParser;

import java.util.LinkedHashSet;
import java.util.List;

public class Day21 {

  private final int indexIP;
  private final List<Instruction> instructions;

  public Day21(final List<String> lines) {
    final var ipIndexParser = new IPRegisterParser();
    final var instructionParser = new InstructionParser();
    this.indexIP = ipIndexParser.parse(lines.get(0));
    this.instructions = lines.stream().skip(1).map(instructionParser::parse).toList();
  }

  public long solve1() {
    final var registers = new int[6];

    while (true) {
      if (registers[indexIP] == 28) {
        return registers[instructions.get(28).a];
      }
      instructions.get(registers[indexIP]).apply(registers);
      registers[indexIP]++;
    }
  }

  public long solve2() {
    final var seen = new LinkedHashSet<Integer>();
    final var registers = new int[6];

    while (true) {
      if (registers[indexIP] == 28) {
        if (!seen.add(registers[instructions.get(28).a])) {
          return seen.stream().skip(seen.size() - 1).findAny().orElseThrow();
        }
      }
      instructions.get(registers[indexIP]).apply(registers);
      registers[indexIP]++;
    }
  }
}
