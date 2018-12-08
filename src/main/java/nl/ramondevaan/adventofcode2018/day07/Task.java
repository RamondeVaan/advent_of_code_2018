package nl.ramondevaan.adventofcode2018.day07;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"children", "parents"})
public class Task {
    private final String id;
    private Set<Task> children;
    private Set<Task> parents;
}
