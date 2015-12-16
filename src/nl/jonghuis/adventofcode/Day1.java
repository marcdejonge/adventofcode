package nl.jonghuis.adventofcode;

import java.io.IOException;

public class Day1 extends Day {
    private int floor, part2;

    @Override
    public long solvePart1() {
        return floor;
    }

    @Override
    public long solvePart2() {
        return part2;
    }

    @Override
    public void solvePrepare() throws IOException {
        floor = part2 = 0;
        int steps = 0;
        for (char c : readInput().toCharArray()) {
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            }

            steps++;
            if (part2 == 0 && floor == -1) {
                part2 = steps;
            }
        }
    }
}
