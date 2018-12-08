package nl.ramondevaan.adventofcode2018.day04;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(of = "id")
public class Guard {
    private int id;
    private List<Shift> shifts;
}
