package nl.ramondevaan.adventofcode2018.day12;

import lombok.Value;

import java.util.List;

@Value
public class Pots {
    private long offset;
    private List<Boolean> pots;
}
