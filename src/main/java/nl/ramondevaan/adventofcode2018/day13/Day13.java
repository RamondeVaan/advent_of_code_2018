package nl.ramondevaan.adventofcode2018.day13;

import lombok.Builder;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
public class Day13 {
    private final Set<Cart> initialCarts;
    private final Path[][] paths;

    public String puzzle1() {
        Set<Cart> carts = new HashSet<>(initialCarts);
        Map<Integer, Iterator<Turn>> nextTurns = initialCarts.stream()
                                                             .collect(Collectors.toMap(Cart::getId, c -> getTurns()));

        while (true) {
            Iterator<Cart> cartIterator = carts.stream().sorted(getCartComparator()).iterator();
            while (cartIterator.hasNext()) {
                Cart c = cartIterator.next();
                carts.remove(c);
                carts.add(propagateCart(c, nextTurns.get(c.getId())));
                Point collision = getCollision(carts);
                if (collision != null) {
                    return String.format("%d,%d", collision.x, collision.y);
                }
            }
        }
    }

    public String puzzle2() {
        Set<Cart> carts = new HashSet<>(initialCarts);
        Map<Integer, Iterator<Turn>> nextTurns = initialCarts.stream()
                                                             .collect(Collectors.toMap(Cart::getId, c -> getTurns()));

        while (carts.size() > 1) {
            Iterator<Cart> cartIterator = carts.stream().sorted(getCartComparator()).iterator();
            while (cartIterator.hasNext()) {
                Cart c = cartIterator.next();
                if (!carts.contains(c)) {
                    continue;
                }
                carts.remove(c);
                carts.add(propagateCart(c, nextTurns.get(c.getId())));
                Point collision = getCollision(carts);
                if (collision != null) {
                    carts.removeAll(carts.stream()
                                         .filter(e -> e.getLocation().equals(collision))
                                         .collect(Collectors.toSet()));
                }
            }
        }

        Cart cart = carts.stream().findFirst().orElseThrow();
        return String.format("%d,%d", cart.getLocation().x, cart.getLocation().y);
    }

    private Point getCollision(Set<Cart> carts) {
        for (Cart c : carts) {
            for (Cart d : carts) {
                if (c.equals(d)) {
                    continue;
                }
                if (c.getLocation().equals(d.getLocation())) {
                    return c.getLocation();
                }
            }
        }
        return null;
    }

    private Comparator<Cart> getCartComparator() {
        return Comparator.<Cart>comparingInt(c -> c.getLocation().y).thenComparingInt(c -> c.getLocation().x);
    }

    private Iterator<Turn> getTurns() {
        Turn[] turns = new Turn[]{Turn.LEFT, Turn.STRAIGHT, Turn.RIGHT};
        return IntStream.iterate(0, i -> (i + 1) % turns.length).mapToObj(i -> turns[i]).iterator();
    }

    private Cart propagateCart(Cart c, Iterator<Turn> turns) {
        Direction k = c.getDirection();
        Point nextLocation = new Point(c.getLocation().x + k.dx, c.getLocation().y + k.dy);

        Path nextPath = paths[nextLocation.x][nextLocation.y];
        Direction nextDirection;

        if (nextPath.equals(Path.CROSSING)) {
            Turn t = turns.next();
            nextDirection = t.directionMap.get(k);
        } else {
            nextDirection = nextPath.directionMap.get(k);
        }

        return new Cart(c.getId(), nextLocation, nextDirection);
    }
}
