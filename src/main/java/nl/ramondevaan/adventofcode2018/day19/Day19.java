package nl.ramondevaan.adventofcode2018.day19;

import com.google.common.math.IntMath;
import nl.ramondevaan.adventofcode2018.day19.instruction.Instruction;

import java.util.HashMap;
import java.util.List;

public class Day19 {

  private final int indexIP;
  private final List<Instruction> instructions;

  public Day19(final List<String> lines) {
    final var ipIndexParser = new IPRegisterParser();
    final var instructionParser = new InstructionParser();
    this.indexIP = ipIndexParser.parse(lines.get(0));
    this.instructions = lines.stream().skip(1).map(instructionParser::parse).toList();
  }

  public long solve1() {
    final var registers = new int[6];
    final var max = instructions.size();

    while (registers[indexIP] < max) {
      instructions.get(registers[indexIP]).apply(registers);
      registers[indexIP]++;
    }

    return registers[0];
  }

  public long solve2() {
    var numberToFactorize = 10551236 + instructions.get(21).b * 22 + instructions.get(23).b;

    final var factors = new HashMap<Integer, Integer>();
    var possiblePrimeDivisor = 2;
    while ((possiblePrimeDivisor * possiblePrimeDivisor) <= numberToFactorize) {
      while (numberToFactorize % possiblePrimeDivisor == 0) {
        numberToFactorize /= possiblePrimeDivisor;
        factors.put(possiblePrimeDivisor, factors.getOrDefault(possiblePrimeDivisor, 0) + 1);
      }
      possiblePrimeDivisor++;
    }
    if (numberToFactorize > 1) {
      factors.put(numberToFactorize, factors.getOrDefault(numberToFactorize, 0) + 1);
    }
    var sumOfDivisors = 1;
    for (final var entry : factors.entrySet()) {
      sumOfDivisors = sumOfDivisors * (IntMath.pow(entry.getKey(), entry.getValue() + 1) - 1) / (entry.getKey() - 1);
    }
    return sumOfDivisors;
  }
}
