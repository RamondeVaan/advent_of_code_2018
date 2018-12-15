package nl.ramondevaan.adventofcode2018.day13;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.awt.*;

@Value
@EqualsAndHashCode(of = "id")
public class Cart {
    private int id;
    private Point location;
    private Direction direction;
}
