package nl.ramondevaan.adventofcode2018.day15;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.awt.*;

@Value
@EqualsAndHashCode(of = "id")
public class Unit {
    private final int id;
    private final String team;
    private int health;
    private int attackPower;
    private Point location;
}
