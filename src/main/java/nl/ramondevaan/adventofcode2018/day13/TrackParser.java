package nl.ramondevaan.adventofcode2018.day13;

import lombok.Getter;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class TrackParser {
    private static final String LEFT_FORMAT = "[-<]";
    private static final String RIGHT_FORMAT = "[->]";
    private static final String UP_FORMAT = "[|^]";
    private static final String DOWN_FORMAT = "[v|]";
    private static final String DR_FORMAT = "/";
    private static final String UL_FORMAT = "\\\\";
    private static final String CROSSING_FORMAT = "\\+";
    private static final String CART_FORMAT = "[<>v^]";

    private Set<Cart> carts;
    private Path[][] paths;

    public void parse(Stream<String> input) {
        List<String> inputStrings = input.collect(Collectors.toList());

        carts = new HashSet<>();
        paths = new Path[inputStrings.stream().mapToInt(String::length).max().orElseThrow()][inputStrings.size()];

        Point point;

        for (int i = 0; i < inputStrings.size(); i++) {
            String[] s = inputStrings.get(i).split("(?!^)");
            for (int j = 0; j < s.length; j++) {
                point = new Point(j, i);
                parseChar(point, s[j]);
            }
        }
    }

    private void parseChar(Point p, String s) {
        if (s.matches(DR_FORMAT)) {
            paths[p.x][p.y] = Path.SW_NE;
        } else if (s.matches(UL_FORMAT)) {
            paths[p.x][p.y] = Path.NW_SE;
        } else if (s.matches(CROSSING_FORMAT)) {
            paths[p.x][p.y] = Path.CROSSING;
        } else {
            Direction d = null;

            if (s.matches(LEFT_FORMAT)) {
                d = Direction.LEFT;
                paths[p.x][p.y] = Path.HORIZONTAL;
            } else if (s.matches(RIGHT_FORMAT)) {
                d = Direction.RIGHT;
                paths[p.x][p.y] = Path.HORIZONTAL;
            } else if (s.matches(UP_FORMAT)) {
                d = Direction.UP;
                paths[p.x][p.y] = Path.VERTICAL;
            } else if (s.matches(DOWN_FORMAT)) {
                d = Direction.DOWN;
                paths[p.x][p.y] = Path.VERTICAL;
            }

            if (s.matches(CART_FORMAT)) {
                carts.add(new Cart(carts.size(), p, d));
            }
        }
    }
}
