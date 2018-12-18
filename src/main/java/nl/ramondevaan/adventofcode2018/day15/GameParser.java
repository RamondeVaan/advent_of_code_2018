package nl.ramondevaan.adventofcode2018.day15;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class GameParser {
    private static final String WALL_FORMAT = "#";
    private static final String OPEN_FORMAT = "\\.";
    private static final String GOBLIN_FORMAT = "G";
    private static final String ELF_FORMAT = "E";

    private static final String TEAM_ELF = "E";
    private static final String TEAM_GOBLIN = "G";

    private List<Unit> units;
    private Block[][] field;

    public void parse(Stream<String> in) {
        List<String> input = in.collect(Collectors.toList());

        field = new Block[input.stream().mapToInt(String::length).max().orElseThrow()][input.size()];
        units = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            String t = input.get(y);
            for (int x = 0; x < t.length(); x++) {
                parseChar(new Point(x, y), t.substring(x, x + 1));
            }
        }
    }

    private void parseChar(Point p, String s) {
        if (s.matches(WALL_FORMAT)) {
            field[p.x][p.y] = Block.WALL;
        } else if (s.matches(OPEN_FORMAT)) {
            field[p.x][p.y] = Block.OPEN;
        } else {
            field[p.x][p.y] = Block.OPEN;

            Unit u;

            if (s.matches(GOBLIN_FORMAT)) {
                u = new Unit(units.size(), TEAM_GOBLIN, 200, 3, p);
            } else if (s.matches(ELF_FORMAT)) {
                u = new Unit(units.size(), TEAM_ELF, 200, 3, p);
            } else {
                throw new IllegalArgumentException();
            }

            units.add(u);
        }
    }
}
