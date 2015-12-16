package nl.jonghuis.adventofcode;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Set;

public class Day3 extends Day {
    private static final int START = 1 << 24 | 1 << 8;
    private static final int VERT = 1 << 16;

    public static int step(int c, int loc) {
        switch (c) {
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

    private char[] input;

    @Override
    public long solvePart1() {
        Set<Integer> locations = new HashSet<>();
        locations.add(CharBuffer.wrap(input).chars().reduce(START, (r, e) -> {
            locations.add(r);
            r = step(e, r);
            return r;
        }));
        return locations.size();
    }

    @Override
    public long solvePart2() {
        Set<Integer> locations = new HashSet<>();
        int santaLocation = START, roboLocation = START;
        locations.add(START);

        int count = 0;
        for (char c : input) {
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
        input = readInput().toCharArray();
    }
}
