package nl.jonghuis.adventofcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public abstract class Day {
    protected static boolean DEBUG = false;

    protected static void debug(String format, Object... args) {
        if (DEBUG) {
            System.out.print("DEBUG: ");
            System.out.printf(format, args);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            DEBUG = true;
            int dayNr = Integer.parseInt(args[0]);
            solve(dayNr);
        } else {
            int dayNr = 1;
            while (solve(dayNr)) {
                dayNr++;
            }
        }
    }

    private static boolean solve(int dayNr) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Day> clazz = (Class<? extends Day>) Class.forName("nl.jonghuis.adventofcode.Day" + dayNr);
            Day day = clazz.newInstance();
            System.out.printf("Solving day %2d%n", dayNr);
            System.out.printf("================%n");
            day.solvePrepare();

            long start = System.currentTimeMillis();
            System.out.printf("Solution part 1: %d%n", day.solvePart1());
            debug("That took %dms", System.currentTimeMillis() - start);

            start = System.currentTimeMillis();
            System.out.printf("Solution part 2: %d%n", day.solvePart2());
            debug("That took %dms", System.currentTimeMillis() - start);

            System.out.printf("================%n%n");
            return true;
        } catch (ClassNotFoundException | ClassCastException ex) {
            return false;
        } catch (Exception ex) {
            System.out.println("Day " + dayNr + " failed");
            ex.printStackTrace(System.out);
            return true;
        }
    }

    public String readInput() throws IOException {
        return IOUtils.toString(new FileInputStream("input-" + getClass().getSimpleName().toLowerCase() + ".txt"));
    }

    public List<String> readLines() throws IOException {
        return Arrays.asList(readInput().split("[\\\r\\\n]+"));
    }

    public abstract long solvePart1();

    public abstract long solvePart2();

    public void solvePrepare() throws IOException {
    }
}
