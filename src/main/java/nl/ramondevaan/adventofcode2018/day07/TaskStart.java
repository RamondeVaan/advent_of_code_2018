package nl.ramondevaan.adventofcode2018.day07;

import lombok.Value;

@Value
public class TaskStart {
    private Task task;
    private int start;
    private int end;

    public TaskStart(Task task, int start, int extraTime) {
        this.task = task;
        this.start = start;
        this.end = start + task.getId().charAt(0) - 'A' + extraTime + 1;
    }
}
