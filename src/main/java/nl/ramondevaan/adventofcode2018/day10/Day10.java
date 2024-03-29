package nl.ramondevaan.adventofcode2018.day10;

import lombok.Builder;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class Day10 {
    private final List<Star> stars;
    private final LettersParser lettersParser;

    public String puzzle1() {
        List<Point> positions = initialPositions();
        List<Point> nextPositions = initialPositions();
        propagate(positions, nextPositions, true);

        List<Point> t;
        while (!diverging(positions, nextPositions)) {
            t = positions;
            positions = nextPositions;
            propagate(positions, t, true);
            nextPositions = t;
        }

        return readCode(positions);
    }

    public int puzzle2() {
        List<Point> positions = initialPositions();
        List<Point> nextPositions = initialPositions();
        propagate(positions, nextPositions, true);
        int rounds = 0;

        List<Point> t;
        while (!diverging(positions, nextPositions)) {
            t = positions;
            positions = nextPositions;
            propagate(positions, t, true);
            nextPositions = t;
            rounds++;
        }

        return rounds;
    }

    private List<Point> initialPositions() {
        return stars.stream()
                    .map(Star::getPosition)
                    .map(v -> new Point(v.getX(), v.getY()))
                    .collect(Collectors.toList());
    }

    private String readCode(List<Point> positions) {
        return read(positions);
    }

    private void propagate(List<Point> origin, List<Point> propgated, boolean plus) {
        Point o;
        Point p;
        Vector v;
        int mult = plus ? 1 : -1;
        for (int i = 0; i < stars.size(); i++) {
            o = origin.get(i);
            p = propgated.get(i);
            v = stars.get(i).getVelocity();
            p.x = o.x + v.getX() * mult;
            p.y = o.y + v.getY() * mult;
        }
    }

    private static boolean diverging(List<Point> before, List<Point> after) {
        Vector boxSizeBefore = boxSize(before);
        Vector boxSizeAfter = boxSize(after);

        return boxSizeAfter.getX() > boxSizeBefore.getX() && boxSizeAfter.getY() > boxSizeBefore.getY();
    }

    private static Vector boxSize(List<Point> points) {
        return new Vector(
            points.stream().mapToInt(p -> p.x).max().orElseThrow() -
                points.stream().mapToInt(p -> p.x).min().orElseThrow(),
            points.stream().mapToInt(p -> p.y).max().orElseThrow() -
                points.stream().mapToInt(p -> p.y).min().orElseThrow()
        );
    }

    private String read(List<Point> points) {
        int startX = points.stream().mapToInt(p -> p.x).min().orElseThrow();
        int startY = points.stream().mapToInt(p -> p.y).min().orElseThrow();

        int endX = Math.min(points.stream().mapToInt(p -> p.x).max().orElseThrow() + 1, startX + 100);
        int endY = Math.min(points.stream().mapToInt(p -> p.y).max().orElseThrow() + 1, startY + 30);

        int width = endX - startX;
        int height = endY - startY;

        boolean[][] screen = new boolean[height][width];

        for (final var point : points) {
            screen[point.y - startY][point.x - startX] = true;
        }

        return lettersParser.parse(screen);
    }
}
