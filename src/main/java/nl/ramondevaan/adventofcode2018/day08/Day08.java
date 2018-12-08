package nl.ramondevaan.adventofcode2018.day08;

import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class Day08 {
    private final Node rootNode;

    public int puzzle1() {
        return Stream.concat(Stream.of(rootNode), rootNode.getAllChildren().stream())
                     .flatMap(n -> n.getMetadata().stream())
                     .mapToInt(Integer::intValue)
                     .sum();
    }

    public int puzzle2() {
        Map<Node, Integer> calculated = new HashMap<>();
        Set<Node> remaining = new HashSet<>(rootNode.getAllChildren());
        remaining.add(rootNode);

        List<Node> leafNodes = remaining.stream()
                                        .filter(n -> n.getChildrenSize() == 0)
                                        .collect(Collectors.toList());
        leafNodes.forEach(n -> {
            remaining.remove(n);
            calculated.put(n, n.getMetadata().stream().mapToInt(Integer::intValue).sum());
        });

        while (!remaining.isEmpty()) {
            List<Node> nodes = remaining.stream()
                                        .filter(n -> n.getChildren().stream().allMatch(calculated::containsKey))
                                        .collect(Collectors.toList());
            nodes.forEach(n -> {
                remaining.remove(n);
                calculated.put(n, n.getMetadata()
                                   .stream()
                                   .mapToInt(i -> i - 1)
                                   .filter(i -> i < n.getChildrenSize() && i >= 0)
                                   .map(i -> calculated.get(n.getChildren().get(i)))
                                   .sum());
            });
        }

        return calculated.get(rootNode);
    }
}
