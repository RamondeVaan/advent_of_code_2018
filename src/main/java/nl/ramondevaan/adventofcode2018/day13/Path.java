package nl.ramondevaan.adventofcode2018.day13;

import java.util.Map;

public enum Path {
    VERTICAL(Map.of(Direction.UP, Direction.UP, Direction.DOWN, Direction.DOWN)),
    HORIZONTAL(Map.of(Direction.LEFT, Direction.LEFT, Direction.RIGHT, Direction.RIGHT)),
    CROSSING(Map.of(Direction.DOWN,
                    Direction.DOWN,
                    Direction.UP,
                    Direction.UP,
                    Direction.LEFT,
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.RIGHT)),
    SW_NE(Map.of(Direction.UP,
                 Direction.RIGHT,
                 Direction.RIGHT,
                 Direction.UP,
                 Direction.LEFT,
                 Direction.DOWN,
                 Direction.DOWN,
                 Direction.LEFT)),
    NW_SE(Map.of(Direction.UP,
                 Direction.LEFT,
                 Direction.LEFT,
                 Direction.UP,
                 Direction.RIGHT,
                 Direction.DOWN,
                 Direction.DOWN,
                 Direction.RIGHT));

    public final Map<Direction, Direction> directionMap;

    Path(Map<Direction, Direction> directionMap) {
        this.directionMap = directionMap;
    }
}
