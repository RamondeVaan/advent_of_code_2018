package nl.ramondevaan.adventofcode2018.day03;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Builder
public class Day03 {
    private final List<Claim> claims;

    private int[][] newField() {
        int maxWidth = claims.stream().mapToInt(c -> c.getX() + c.getWidth()).max().orElse(0);
        int maxHeight = claims.stream().mapToInt(c -> c.getY() + c.getHeight()).max().orElse(0);
        return new int[maxWidth][maxHeight];
    }

    private int[][] runClaims() {
        int[][] field = newField();

        for (Claim c : claims) {
            IntStream.range(c.getX(), c.getX() + c.getWidth()).boxed()
                     .flatMap(x -> IntStream.range(c.getY(), c.getY() + c.getHeight())
                                            .mapToObj(y -> Pair.of(x, y)))
                     .forEach(p -> field[p.getLeft()][p.getRight()]++);
        }

        return field;
    }

    public long puzzle1() {
        int[][] field = runClaims();

        return IntStream.range(0, field.length)
                        .flatMap(i -> Arrays.stream(field[i]))
                        .filter(i -> i >= 2)
                        .count();
    }

    public int puzzle2() {
        int[][] field = runClaims();

        return claims.stream().filter(
            c -> IntStream.range(c.getX(), c.getX() + c.getWidth()).boxed()
                          .flatMap(x -> IntStream.range(c.getY(), c.getY() + c.getHeight())
                                                 .mapToObj(y -> Pair.of(x, y)))
                          .mapToInt(p -> field[p.getLeft()][p.getRight()])
                          .allMatch(i -> i == 1)
        ).findAny().map(Claim::getId).orElseThrow();
    }
}
