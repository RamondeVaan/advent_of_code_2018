package nl.ramondevaan.adventofcode2018.day05;

import lombok.Builder;

import java.util.stream.IntStream;

@Builder
public class Day05 {

    private final String input;

    public int puzzle1() {
        return reactFully(input).length();
    }

    public int puzzle2() {
        return IntStream.rangeClosed('a', 'z')
                        .mapToObj(i -> input.replace(Character.toString((char) i), "")
                                            .replace(Character.toString(Character.toUpperCase((char) i)), ""))
                        .map(this::reactFully)
                        .mapToInt(String::length)
                        .min().orElseThrow();
    }

    private String reactFully(String s) {
        ReactorResult r = react(s);

        while (r.isReacted()) {
            r = react(r.getReactorResult());
        }

        return r.getReactorResult();
    }

    private ReactorResult react(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            if (Character.toLowerCase(a) == Character.toLowerCase(b) &&
                Character.isLowerCase(a) != Character.isLowerCase(b)) {
                return new ReactorResult(true, s.substring(0, i) + s.substring(i + 2));
            }
        }

        return new ReactorResult(false, s);
    }
}
