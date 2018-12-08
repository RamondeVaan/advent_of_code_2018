package nl.ramondevaan.adventofcode2018.day03;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "id")
public class Claim {
    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
}
