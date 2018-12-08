package nl.ramondevaan.adventofcode2018.day08;

import java.util.ArrayList;
import java.util.Arrays;

public class NodeParser {
    private int[] nums;
    private int cur;
    private Node current;
    private int id;

    public Node parse(String input) {
        nums = Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        cur = 0;
        id = 0;
        current = parseHeader();

        while (cur < nums.length) {
            while (current.getChildren().size() != current.getChildrenSize()) {
                Node n = parseHeader();
                current.getChildren().add(n);
                current = n;
                Node k = current;
                while ((k = k.getParent()) != null) {
                    k.getAllChildren().add(n);
                }
            }
            parseMetaData();
            current = current.getParent() == null ? current : current.getParent();
        }

        return current;
    }

    private Node parseHeader() {
        Node n = new Node();
        n.setId(id++);
        n.setParent(current);
        n.setChildrenSize(nums[cur++]);
        n.setMetadataSize(nums[cur++]);
        n.setChildren(new ArrayList<>());
        n.setAllChildren(new ArrayList<>());
        n.setMetadata(new ArrayList<>());
        return n;
    }

    private void parseMetaData() {
        while (current.getMetadataSize() != current.getMetadata().size()) {
            current.getMetadata().add(nums[cur++]);
        }
    }
}
