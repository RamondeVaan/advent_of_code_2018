package nl.ramondevaan.adventofcode2018.day16;

import nl.ramondevaan.adventofcode2018.util.BlankStringPartitioner;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {

  private final static Set<Instruction> INSTRUCTIONS = Set.of(
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) + reg.get(inst.b)),   // addr
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) + inst.b),            // addi
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) * reg.get(inst.b)),   // mulr
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) * inst.b),            // muli
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) & reg.get(inst.b)),   // banr
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) & inst.b),            // bani
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) | reg.get(inst.b)),   // borr
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) | inst.b),            // bori
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a)),                           // setr
    (inst, reg) -> reg.with(inst.c, inst.a),                                    // seti
    (inst, reg) -> reg.with(inst.c, inst.a > reg.get(inst.b) ? 1 : 0),          // gtir
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) > inst.b ? 1 : 0),          // gtri
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) > reg.get(inst.b) ? 1 : 0), // gtrr
    (inst, reg) -> reg.with(inst.c, inst.a == reg.get(inst.b) ? 1 : 0),         // eqir
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) == inst.b ? 1 : 0),         // eqri
    (inst, reg) -> reg.with(inst.c, reg.get(inst.a) == reg.get(inst.b) ? 1 : 0) // eqrr
  );
  private final List<Sample> samples;
  private final List<NumberedInstruction> program;

  public Day16(final List<String> lines) {
    final var instructionParser = new InstructionParser();
    final var sampleParser = new SampleParser(instructionParser);
    final var partitioner = new BlankStringPartitioner();
    final var partitions = partitioner.partition(lines);
    this.samples = partitions.stream().limit(partitions.size() - 1).map(sampleParser::parse).toList();
    this.program = partitions.get(partitions.size() - 1).stream().map(instructionParser::parse).toList();
  }

  public long solve1() {
    return samples.stream()
      .map(sample -> filterOptions(sample, INSTRUCTIONS))
      .map(Collection::size)
      .filter(count -> count >= 3L).count();
  }

  public long solve2() {
    final var map = instructionsByOpcode(samples);
    var registers = new Registers(List.of(0, 0, 0, 0));

    for (final var instruction : program) {
      registers = map.get(instruction.opcode).apply(instruction, registers);
    }

    return registers.get(0);
  }

  private static Map<Integer, Instruction> instructionsByOpcode(final List<Sample> samples) {
    final var map = new HashMap<Integer, Set<Instruction>>();
    IntStream.range(0, 16).forEach(i -> map.put(i, Set.copyOf(INSTRUCTIONS)));
    samples.forEach(sample -> map.compute(sample.instruction.opcode,
      (opcode, instructions) -> filterOptions(sample, instructions)));

    final var ret = new HashMap<Integer, Instruction>();

    while (!map.isEmpty()) {
      final var oneLeft = map.entrySet().stream().filter(entry -> entry.getValue().size() == 1).findAny().orElseThrow();
      final var value = oneLeft.getValue().stream().findAny().orElseThrow();
      map.remove(oneLeft.getKey());
      map.replaceAll(remove(value));
      ret.put(oneLeft.getKey(), value);
    }

    return Collections.unmodifiableMap(ret);
  }

  private static BiFunction<Integer, Set<Instruction>, Set<Instruction>> remove(Instruction instruction) {
    return (integer, instructions) -> {
      final var ret = new HashSet<>(instructions);
      ret.remove(instruction);
      return Collections.unmodifiableSet(ret);
    };
  }

  private static Set<Instruction> filterOptions(final Sample sample, final Set<Instruction> instructions) {
    return instructions.stream()
      .filter(instruction -> instruction.apply(sample.instruction, sample.before).equals(sample.after))
      .collect(Collectors.toSet());
  }
}
