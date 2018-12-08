package nl.ramondevaan.adventofcode2018.day06;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Builder
public class Day06 {
    private List<Point> points;
    private int maxDist;

    public long puzzle1() {
        Pair<Point, Point> fieldExtremes = getFieldExtremes();
        int[][] field = findClosestPoints(fieldExtremes);
        List<Integer> borderPoints = getPointsOnBorder(field);

        return IntStream.range(0, points.size())
                        .filter(i -> !borderPoints.contains(i))
                        .mapToLong(i -> areaSize(field, i))
                        .max().orElseThrow();
    }

    public long puzzle2() {
        Pair<Point, Point> fieldExtremes = getFieldExtremes();
        int[][] field = findSumDistances(fieldExtremes);

        return IntStream.range(0, field.length).boxed()
                        .flatMap(i -> IntStream.range(0, field[i].length).boxed().map(j -> new Point(i, j)))
                        .filter(p -> field[p.getX()][p.getY()] < maxDist)
                        .count();
    }

    private Pair<Point, Point> getFieldExtremes() {
        int minX = points.stream().mapToInt(Point::getX).min().orElseThrow();
        int minY = points.stream().mapToInt(Point::getY).min().orElseThrow();
        int maxX = points.stream().mapToInt(Point::getX).max().orElseThrow();
        int maxY = points.stream().mapToInt(Point::getY).max().orElseThrow();

        return Pair.of(new Point(minX, minY), new Point(maxX, maxY));
    }

    private List<Integer> getPointsOnBorder(int[][] field) {
        return Stream.concat(IntStream.range(0, field.length)
                                      .boxed()
                                      .flatMap(i -> Stream.of(new Point(i, 0), new Point(i, field[i].length - 1))),
                             IntStream.range(0, field[0].length)
                                      .boxed()
                                      .flatMap(j -> Stream.of(new Point(0, j), new Point(field.length - 1, j))))
                     .map(p -> field[p.getX()][p.getY()]).distinct().collect(Collectors.toList());
    }

    private int[][] getField(Pair<Point, Point> fieldExtremes) {
        int[][] ret = new int[fieldExtremes.getRight().getX() + 1][fieldExtremes.getRight().getY() + 1];
        for (int[] ints : ret) {
            Arrays.fill(ints, -1);
        }
        return ret;
    }

    private int[][] findClosestPoints(Pair<Point, Point> fieldExtremes) {
        final int[][] field = getField(fieldExtremes);

        IntStream.range(0, field.length)
                 .boxed()
                 .flatMap(i -> IntStream.range(0, field[i].length).boxed().map(j -> new Point(i, j)))
                 .forEach(p -> field[p.getX()][p.getY()] = closestPoint(p));

        return field;
    }

    private int closestPoint(Point p) {
        List<Pair<Point, Integer>> distances = points.stream()
                                                     .map(q -> Pair.of(q, manhattanDistance(q, p)))
                                                     .collect(Collectors.toList());
        int min = distances.stream().mapToInt(Pair::getRight).min().orElseThrow();
        boolean distinct = distances.stream().filter(q -> q.getRight().equals(min)).count() == 1L;
        return !distinct ? -1 : IntStream.range(0, distances.size())
                                         .filter(i -> distances.get(i).getRight().equals(min))
                                         .findFirst()
                                         .orElseThrow();
    }

    private int manhattanDistance(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    private long areaSize(int[][] field, int id) {
        return IntStream.range(0, field.length)
                        .boxed()
                        .flatMap(i -> IntStream.range(0, field[i].length).boxed().map(j -> new Point(i, j)))
                        .filter(p -> field[p.getX()][p.getY()] == id)
                        .count();
    }

    private int[][] findSumDistances(Pair<Point, Point> fieldExtremes) {
        int[][] field = new int[fieldExtremes.getRight().getX() + 1][fieldExtremes.getRight().getY() + 1];

        IntStream.range(0, field.length)
                 .boxed()
                 .flatMap(i -> IntStream.range(0, field[i].length).boxed().map(j -> new Point(i, j)))
                 .forEach(p -> field[p.getX()][p.getY()] = sumDistances(p));

        return field;
    }

    private int sumDistances(Point p) {
        return points.stream()
                     .mapToInt(q -> manhattanDistance(q, p))
                     .sum();
    }
}
