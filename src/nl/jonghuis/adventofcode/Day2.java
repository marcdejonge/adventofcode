package nl.jonghuis.adventofcode;

import java.io.IOException;
import java.util.Arrays;

public class Day2 extends Day {
    private int totalPaper;
    private int totalRibbon;

    @Override
    public long solvePart1() {
        return totalPaper;
    }

    @Override
    public long solvePart2() {
        return totalRibbon;
    }

    @Override
    public void solvePrepare() throws IOException {
        totalPaper = totalRibbon = 0;
        for (String line : readLines()) {
            int[] sides = Arrays.stream(line.split("x")).mapToInt(Integer::parseInt).sorted().toArray();
            int a = sides[0];
            int b = sides[1];
            int c = sides[2];

            int side1 = a * b;
            int side2 = a * c;
            int side3 = b * c;

            int paper = 2 * (side1 + side2 + side3) + side1;
            totalPaper += paper;
            totalRibbon += 2 * a + 2 * b + a * b * c;
        }
    }
}
