package nl.ramondevaan.adventofcode2018.day13;

import java.util.Map;

public enum Turn {
    LEFT(Map.of(Direction.RIGHT,
                Direction.UP,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.LEFT,
                Direction.DOWN,
                Direction.UP,
                Direction.LEFT)),
    RIGHT(Map.of(Direction.RIGHT,
                 Direction.DOWN,
                 Direction.DOWN,
                 Direction.LEFT,
                 Direction.LEFT,
                 Direction.UP,
                 Direction.UP,
                 Direction.RIGHT)),
    STRAIGHT(Map.of(Direction.RIGHT,
                    Direction.RIGHT,
                    Direction.DOWN,
                    Direction.DOWN,
                    Direction.LEFT,
                    Direction.LEFT,
                    Direction.UP,
                    Direction.UP));

    public final Map<Direction, Direction> directionMap;

    Turn(Map<Direction, Direction> directionMap) {
        this.directionMap = directionMap;
    }
}
