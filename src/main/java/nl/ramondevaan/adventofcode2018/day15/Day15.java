package nl.ramondevaan.adventofcode2018.day15;

import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Builder
public class Day15 {
    private final Block[][] field;
    private final List<Unit> initialUnits;

    public int puzzle1() {
        Pair<Integer, Map<Integer, Unit>> outcome = run(new HashMap<>(initialUnits.stream()
                                                                                  .collect(Collectors.toMap(Unit::getId,
                                                                                                            u -> u))));
        return calculateScore(outcome.getLeft(), outcome.getRight());
    }

    public int puzzle2() {
        Pair<Integer, Map<Integer, Unit>> outcome;
        int attackPower = 0;

        do {
            Map<Integer, Unit> units = setAttackPower(initialUnits, "E", ++attackPower);
            outcome = run(units);
        } while (countDeaths(initialUnits, outcome.getRight().values(), "E") > 0);

        return calculateScore(outcome.getLeft(), outcome.getRight());
    }

    private Map<Integer, Unit> setAttackPower(Collection<Unit> units, String team, int attackPower) {
        return new HashMap<>(units.stream()
                                  .map(u -> u.getTeam().equals(team) ? new Unit(u.getId(),
                                                                                u.getTeam(),
                                                                                u.getHealth(),
                                                                                attackPower,
                                                                                u.getLocation()) : u)
                                  .collect(Collectors.toMap(Unit::getId, u -> u)));
    }

    private long countDeaths(Collection<Unit> before, Collection<Unit> after, String team) {
        long c1 = before.stream().map(Unit::getTeam).filter(s -> s.equals(team)).count();
        long c2 = after.stream().map(Unit::getTeam).filter(s -> s.equals(team)).count();

        return c1 - c2;
    }

    private Pair<Integer, Map<Integer, Unit>> run(Map<Integer, Unit> input) {
        int round = 0;

        Map<Integer, Unit> units = new HashMap<>(input);

        while (true) {
            Iterator<Integer> it = units.values().stream()
                                        .sorted(Comparator.<Unit>comparingInt(v -> v.getLocation().y)
                                                    .thenComparingInt(v -> v.getLocation().x))
                                        .map(Unit::getId)
                                        .iterator();
            while (it.hasNext()) {
                Unit u = units.get(it.next());
                if (u != null) {
                    if (isFinished(units)) {
                        return Pair.of(round, units);
                    }
                    propagate(units, u);
                }
            }

            round++;
        }
    }

    private int calculateScore(int round, Map<Integer, Unit> units) {
        int totalHealth = units.values().stream().mapToInt(Unit::getHealth).sum();

        return round * totalHealth;
    }

    private boolean isFinished(Map<Integer, Unit> units) {
        return units.values().stream().map(Unit::getTeam).distinct().count() <= 1L;
    }

    private void propagate(Map<Integer, Unit> units, Unit u) {
        if (!isAtEnemy(units, u)) {
            u = moveToClosestEnemy(units, u);
        }

        attackBestEnemy(units, u);
    }

    private boolean isAtEnemy(Map<Integer, Unit> units, Unit u) {
        return units.values()
                    .stream()
                    .filter(v -> !Objects.equals(u.getTeam(), v.getTeam()))
                    .anyMatch(v -> distance(u, v) <= 1);
    }

    private int distance(Unit u, Unit v) {
        return Math.abs(u.getLocation().x - v.getLocation().x) + Math.abs(u.getLocation().y - v.getLocation().y);
    }

    private void attackBestEnemy(Map<Integer, Unit> units, Unit u) {
        Set<Unit> adjacentEnemies = units.values().stream()
                                         .filter(v -> !Objects.equals(u.getTeam(), v.getTeam()))
                                         .filter(v -> distance(u, v) == 1)
                                         .collect(Collectors.toSet());

        if (adjacentEnemies.isEmpty()) {
            return;
        }

        int lowestHealth = adjacentEnemies.stream().mapToInt(Unit::getHealth).min().orElseThrow();

        Unit toAttack = adjacentEnemies.stream()
                                       .filter(v -> v.getHealth() == lowestHealth)
                                       .min(Comparator.<Unit>comparingInt(v -> v.getLocation().y)
                                                .thenComparingInt(v -> v.getLocation().x))
                                       .orElseThrow();
        units.remove(toAttack.getId());

        toAttack = new Unit(toAttack.getId(),
                            toAttack.getTeam(),
                            toAttack.getHealth() - u.getAttackPower(),
                            toAttack.getAttackPower(),
                            toAttack.getLocation());

        if (toAttack.getHealth() > 0) {
            units.put(toAttack.getId(), toAttack);
        }
    }

    private Unit moveToClosestEnemy(Map<Integer, Unit> units, Unit u) {
        Point target = getTarget(units, u);

        if (target == null) {
            return u;
        }

        int[][] distanceMap = getDistanceMap(units, target);

        Set<Point> options = getValidSurrounding(units, u.getLocation())
            .stream()
            .filter(p -> distanceMap[p.x][p.y] >= 0)
            .collect(
                Collectors.toSet());

        if (options.isEmpty()) {
            return u;
        }

        int min = options.stream().mapToInt(p -> distanceMap[p.x][p.y]).min().orElseThrow();
        units.remove(u.getId());
        Unit v = new Unit(u.getId(), u.getTeam(), u.getHealth(), u.getAttackPower(),
                          options.stream()
                                 .filter(p -> distanceMap[p.x][p.y] == min)
                                 .min(Comparator.<Point>comparingInt(p -> p.y).thenComparingInt(p -> p.x))
                                 .orElseThrow());
        units.put(v.getId(), v);
        return v;
    }

    private Point getTarget(Map<Integer, Unit> units, Unit u) {
        int[][] dist = getDistanceMap(units, u.getLocation());

        Set<Point> targets = units.values().stream()
                                  .filter(v -> !Objects.equals(u.getTeam(), v.getTeam()))
                                  .map(Unit::getLocation)
                                  .flatMap(p -> getValidSurrounding(units, p).stream())
                                  .filter(p -> dist[p.x][p.y] >= 0)
                                  .collect(Collectors.toSet());

        if (targets.isEmpty()) {
            return null;
        }

        int min = targets.stream().mapToInt(p -> dist[p.x][p.y]).min().orElseThrow();

        return targets.stream()
                      .filter(p -> dist[p.x][p.y] == min)
                      .min(Comparator.<Point>comparingInt(p -> p.y).thenComparingInt(p -> p.x))
                      .orElseThrow();
    }

    private int[][] getDistanceMap(Map<Integer, Unit> units, Point... points) {
        int[][] dist = new int[field.length][field[0].length];
        for (int[] d : dist) {
            Arrays.fill(d, -1);
        }

        Set<Point> toCheck = new HashSet<>();

        for (Point p : points) {
            toCheck.addAll(getValidSurrounding(units, p).stream()
                                                        .filter(q -> dist[q.x][q.y] != 0)
                                                        .collect(Collectors.toSet()));
            dist[p.x][p.y] = 0;
        }

        AtomicInteger d = new AtomicInteger(1);

        while (!toCheck.isEmpty()) {
            toCheck.forEach(p -> dist[p.x][p.y] = d.get());
            toCheck = toCheck.stream()
                             .flatMap(p -> getValidSurrounding(units, p).stream())
                             .filter(p -> dist[p.x][p.y] == -1 || dist[p.x][p.y] > d.get())
                             .collect(Collectors.toSet());
            d.incrementAndGet();
        }

        return dist;

    }

    private Set<Point> getValidSurrounding(Map<Integer, Unit> units, Point q) {
        return getSurrounding(q).stream()
                                .filter(p -> p.x >= 0 && p.x < field.length && p.y >= 0 && p.y < field[p.x].length)
                                .filter(p -> units.values().stream().noneMatch(u -> u.getLocation().equals(p)))
                                .filter(p -> field[p.x][p.y].equals(Block.OPEN))
                                .collect(Collectors.toSet());
    }

    private static Set<Point> getSurrounding(Point p) {
        return Arrays.stream(Direction.values())
                     .map(d -> new Point(p.x + d.dx, p.y + d.dy))
                     .collect(Collectors.toSet());
    }
}
