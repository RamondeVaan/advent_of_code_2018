package nl.ramondevaan.adventofcode2018.day09;

import lombok.Builder;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.IntStream;

@Builder
public class Day09 {
    private final int numberOfPlayers;
    private final int lastMarblePoints;

    public long puzzle1() {
        Iterator<Integer> marbleGenerator = IntStream.rangeClosed(0, lastMarblePoints).iterator();
        return propagate(marbleGenerator);
    }

    public long puzzle2() {
        Iterator<Integer> marbleGenerator = IntStream.rangeClosed(0, 100 * lastMarblePoints).iterator();
        return propagate(marbleGenerator);
    }

    private long propagate(Iterator<Integer> marbleGenerator) {
        Field field = initField(marbleGenerator);

        while (marbleGenerator.hasNext()) {
            nextMove(field, marbleGenerator);
        }

        return field.getPlayerScores().stream().mapToLong(Long::longValue).max().orElseThrow();
    }

    private Field initField(Iterator<Integer> marbleGenerator) {
        Deque<Long> playerScores = new ArrayDeque<>();
        IntStream.range(0, numberOfPlayers).forEach(i -> playerScores.add(0L));
        Deque<Integer> marbles = new ArrayDeque<>(lastMarblePoints);
        marbles.add(marbleGenerator.next());
        return new Field(playerScores, marbles, 0);
    }

    private void nextMove(Field field, Iterator<Integer> marbleGenerator) {
        Deque<Long> playerScores = field.getPlayerScores();
        int m = marbleGenerator.next();

        Deque<Integer> marbles = field.getMarbles();

        if (m % 23 == 0) {
            rotate(marbles, -7);
            playerScores.addFirst(playerScores.removeFirst() + m + marbles.remove());
        } else {
            rotate(marbles, 2);
            marbles.addFirst(m);
        }

        rotate(playerScores, 1);
    }

    private static <E> void rotate(Deque<E> queue, int distance) {
        distance = distance % queue.size();

        if (distance < 0) {
            for (int i = 0; i > distance; i--) {
                queue.addFirst(queue.removeLast());
            }
        } else {
            for (int i = 0; i < distance; i++) {
                queue.addLast(queue.removeFirst());
            }
        }
    }
}
