package nl.ramondevaan.adventofcode2018.day07;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskParser {
    private static final String TASK_FORMAT = "Step (.+) must be finished before step (.+) can begin.";
    private static final Pattern TASK_PATTERN = Pattern.compile(TASK_FORMAT);

    public Collection<Task> parse(Stream<String> input) {
        Map<String, List<String>> steps = input
            .map(TASK_PATTERN::matcher)
            .peek(Matcher::matches)
            .map(m -> Pair.of(m.group(1), m.group(2)))
            .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList())));

        Map<String, Task> taskSet = Stream.concat(steps.keySet().stream(),
                                                  steps.values().stream().flatMap(Collection::stream))
                                          .distinct()
                                          .collect(Collectors.toMap(s -> s, Task::new));

        taskSet.values().forEach(t -> {
            t.setParents(Collections.emptySet());
            if (steps.containsKey(t.getId())) {
                t.setChildren(steps.get(t.getId())
                                   .stream()
                                   .map(taskSet::get)
                                   .collect(Collectors.toSet()));
            } else {
                t.setChildren(Collections.emptySet());
            }
        });
        taskSet.values().forEach(t -> t.getChildren().forEach(s -> {
            HashSet<Task> h = new HashSet<>(s.getParents());
            h.add(t);
            s.setParents(h);
        }));

        return new ArrayList<>(taskSet.values());
    }
}
