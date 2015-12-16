package nl.jonghuis.adventofcode;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 extends Day {

    public static boolean isNice1(String word) {
        boolean foundDouble = false;
        int forbidden = 0, last = 0;
        int vowels = 0;

        for (char c : word.toCharArray()) {
            if (c == forbidden) {
                debug("Word %s is not nice, because it has a forbidden set", word);
                return false;
            }

            if (c == last) {
                foundDouble = true;
            }
            last = c;

            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }

            if (c == 'a' || c == 'c' || c == 'p' || c == 'x') {
                forbidden = c + 1;
            } else {
                forbidden = 0;
            }
        }

        if (vowels < 3) {
            debug("Word %s is not nice, because it has no 3 vowels", word);
            return false;
        } else if (!foundDouble) {
            debug("Word %s is not nice, because it has no double characters", word);
            return false;
        } else {
            debug("Word %s is nice", word);
            return true;
        }
    }

    private static boolean isNice2(String word) {
        Set<Integer> digraphs = new HashSet<>();
        boolean repeat = false, foundDouble = false;
        char last2 = 0, last1 = 0;
        for (char c : word.toCharArray()) {
            if (last1 > 0) {
                int digraph = last1 << 16 | c;
                if (digraphs.contains(digraph) && !(last2 == last1 && last1 == c)) {
                    foundDouble = true;
                }
                digraphs.add(digraph);
            }

            if (c == last2) {
                repeat = true;
            }
            last2 = last1;
            last1 = c;
        }

        if (!repeat) {
            debug("Word %s is not nice, because it didn't repeat a letter with 1 in between", word);
            return false;
        } else if (!foundDouble) {
            debug("Word %s is not nice, because it didn't contain a double digraph", word);
            return false;
        } else {
            debug("Word %s is nice", word);
            return true;
        }
    }

    private List<String> lines;

    @Override
    public long solvePart1() {
        return lines.stream().filter(Day5::isNice1).count();
    }

    @Override
    public long solvePart2() {
        return lines.stream().filter(Day5::isNice2).count();
    }

    @Override
    public void solvePrepare() throws IOException {
        lines = readLines();
    }
}
