package nl.ramondevaan.adventofcode2018.day14;

import lombok.Builder;

import java.util.Arrays;

@Builder
public class Day14 {
    private final int numberOfRecipes;

    public String puzzle1() {
        StringBuilder scores = new StringBuilder("37");
        int[] elfIndices = new int[]{0, 1};

        while (scores.length() < numberOfRecipes + 10) {
            int newScore = Arrays.stream(elfIndices).map(scores::charAt).map(i -> i - '0').sum();
            scores.append(newScore);
            updateElfIndices(scores, elfIndices);
        }

        return scores.substring(numberOfRecipes, numberOfRecipes + 10);
    }

    public int puzzle2() {
        StringBuilder scores = new StringBuilder("37");
        String wanted = String.valueOf(numberOfRecipes);

        int[] elfIndices = new int[]{0, 1};
        int indexOf = -1;

        while (indexOf < 0) {
            int newScore = Arrays.stream(elfIndices).map(scores::charAt).map(i -> i - '0').sum();
            scores.append(newScore);
            indexOf = scores.indexOf(wanted, scores.length() - wanted.length() - 1);
            updateElfIndices(scores, elfIndices);
        }

        return indexOf;
    }

    private void updateElfIndices(CharSequence scores, int[] elfIndices) {
        for (int i = 0; i < elfIndices.length; i++) {
            elfIndices[i] += scores.charAt(elfIndices[i]) - '0' + 1;
            elfIndices[i] %= scores.length();
        }
    }
}
