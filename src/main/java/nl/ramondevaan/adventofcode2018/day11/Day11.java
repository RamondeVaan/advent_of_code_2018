package nl.ramondevaan.adventofcode2018.day11;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.IntStream;

@Builder
public class Day11 {
    private static final int GRID_SIZE = 300;
    private static final int SQUARE_SIZE = 3;
    private final int gridSerialNumber;

    public String puzzle1() {
        int[][] grid = initGrid();
        Pair<Point, Integer> result = find(grid, SQUARE_SIZE);
        return String.format("%d,%d", result.getLeft().x, result.getLeft().y);
    }

    public String puzzle2() {
        int[][] grid = initGrid();

        return IntStream.rangeClosed(1, GRID_SIZE)
                        .mapToObj(i -> Pair.of(i, find(grid, i)))
                        .max(Comparator.comparingInt(p -> p.getRight().getRight()))
                        .map(p -> String.format("%d,%d,%d",
                                                p.getRight().getLeft().x,
                                                p.getRight().getLeft().y,
                                                p.getLeft()))
                        .orElseThrow();
    }

    private int[][] initGrid() {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];

        IntStream.range(0, GRID_SIZE)
                 .boxed()
                 .flatMap(i -> IntStream.range(0, GRID_SIZE).mapToObj(j -> new Point(i, j)))
                 .forEach(p -> grid[p.x][p.y] = valueOfPoint(p));

        return grid;
    }

    private Pair<Point, Integer> find(int[][] grid, int squareSize) {
        return IntStream.rangeClosed(0, GRID_SIZE - squareSize)
                        .boxed()
                        .flatMap(i -> IntStream.rangeClosed(0, GRID_SIZE - squareSize).mapToObj(j -> new Point(i, j)))
                        .map(p -> Pair.of(p, squareWorth(grid, p, squareSize)))
                        .max(Comparator.comparingInt(Pair::getRight))
                        .orElseThrow();
    }

    private int valueOfPoint(Point point) {
        int rackId = point.x + 10;
        int s = rackId * point.y;
        s += gridSerialNumber;
        s *= rackId;

        return (s / 100) % 10 - 5;
    }

    private int squareWorth(int[][] grid, Point topLeft, int squareSize) {
        int x, y, s = 0;

        for (int i = 0; i < squareSize; i++) {
            x = topLeft.x + i;
            for (int j = 0; j < squareSize; j++) {
                y = topLeft.y + j;
                s += grid[x][y];
            }
        }

        return s;
    }
}
