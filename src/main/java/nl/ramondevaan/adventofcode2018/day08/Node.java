package nl.ramondevaan.adventofcode2018.day08;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"parent", "children"})
public class Node {
    private int id;
    private Node parent;
    private int childrenSize;
    private int metadataSize;

    private List<Node> children;
    private List<Node> allChildren;
    private List<Integer> metadata;
}
