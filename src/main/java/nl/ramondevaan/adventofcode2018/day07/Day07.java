package nl.ramondevaan.adventofcode2018.day07;

import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
public class Day07 {
    private final Collection<Task> tasks;
    private final int numberOfWorkers;
    private final int extraTime;

    public String puzzle1() {
        List<Task> done = new ArrayList<>();
        Set<Task> available = new HashSet<>(getRoots());

        while (!available.isEmpty()) {
            Task next = getFirst(done, available);
            available.remove(next);
            done.add(next);
            available.addAll(next.getChildren());
        }

        return done.stream().map(Task::getId).collect(Collectors.joining());
    }

    public int puzzle2() {
        List<Task> done = new ArrayList<>();
        Set<Task> available = new HashSet<>(getRoots());
        TaskStart[] workers = new TaskStart[numberOfWorkers];

        int time = 0;
        while (done.size() != tasks.size()) {
            Task task;
            int worker;
            while ((task = getFirst(done, available)) != null && (worker = getAvailableWorker(workers, time)) != -1) {
                available.remove(task);
                workers[worker] = new TaskStart(task, time, extraTime);
            }
            int nextTime = Arrays.stream(workers)
                                 .filter(Objects::nonNull)
                                 .mapToInt(TaskStart::getEnd)
                                 .min()
                                 .orElseThrow();
            updateState(workers, done, available, nextTime);
            time = nextTime;
        }
        return time;
    }

    private Task getFirst(List<Task> done, Set<Task> available) {
        return available.stream()
                        .filter(t -> done.containsAll(t.getParents()))
                        .min(Comparator.comparing(Task::getId))
                        .orElse(null);
    }

    private int getAvailableWorker(TaskStart[] workers, int time) {
        return IntStream.range(0, numberOfWorkers)
                        .filter(i -> workers[i] == null || workers[i].getEnd() <= time)
                        .findFirst()
                        .orElse(-1);
    }

    private void updateState(TaskStart[] workers, List<Task> done, Set<Task> available, int time) {
        IntStream.range(0, workers.length)
                 .filter(i -> Objects.nonNull(workers[i]))
                 .filter(i -> workers[i].getEnd() <= time)
                 .forEach(t -> {
                     done.add(workers[t].getTask());
                     available.addAll(workers[t].getTask().getChildren());
                     workers[t] = null;
                 });
    }

    private List<Task> getRoots() {
        return tasks.stream().filter(t -> t.getParents().isEmpty()).collect(Collectors.toList());
    }
}
