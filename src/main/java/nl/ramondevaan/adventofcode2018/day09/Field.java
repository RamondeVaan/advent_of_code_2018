package nl.ramondevaan.adventofcode2018.day09;

import lombok.Value;

import java.util.Deque;

@Value
public class Field {
    private Deque<Long> playerScores;
    private Deque<Integer> marbles;
    private int round;
}
