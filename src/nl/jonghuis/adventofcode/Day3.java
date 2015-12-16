package nl.jonghuis.adventofcode;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day3 extends Day {
    private static final int START = 1 << 24 | 1 << 8;
    private static final int VERT = 1 << 16;

    public static int step(int code, int loc) {
        switch (code) {
        case '^':
            return loc + VERT;
        case 'v':
            return loc - VERT;
        case '>':
            return loc + 1;
        case '<':
            return loc - 1;
        default:
            return loc;
        }
    }

    private String input;

    @Override
    public long solvePart1() {
        Set<Integer> locations = new HashSet<>();
        locations.add(input.chars().reduce(START, (currLocation, code) -> {
            locations.add(currLocation);
            currLocation = step(code, currLocation);
            return currLocation;
        }));
        return locations.size();
    }

    @Override
    public long solvePart2() {
        Set<Integer> locations = new HashSet<>();
        int santaLocation = START, roboLocation = START;
        locations.add(START);

        int count = 0;
        for (char c : input.toCharArray()) {
            if (count++ % 2 == 0) {
                santaLocation = step(c, santaLocation);
                locations.add(santaLocation);
            } else {
                roboLocation = step(c, roboLocation);
                locations.add(roboLocation);
            }
        }

        return locations.size();
    }

    @Override
    public void solvePrepare() throws IOException {
        input = readInput();
    }
}
