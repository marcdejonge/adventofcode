package nl.jonghuis.adventofcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 extends Day {
    private final byte[] base = "ckczppom".getBytes();
    private final MessageDigest digest;

    public Day4() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("MD5");
    }

    public long findUntil(int min) {
        for (int ix = 1;; ix++) {
            digest.reset();
            digest.update(base);
            if (getZeroNibbles(digest.digest(Integer.toString(ix).getBytes())) >= min) {
                return ix;
            }
        }
    }

    private int getZeroNibbles(byte[] bytes) {
        int count = 0;
        for (byte b : bytes) {
            if (b == 0) {
                count += 2;
            } else if ((b & 0xf0) == 0) {
                return count + 1;
            } else {
                return count;
            }
        }
        return bytes.length * 2;
    }

    @Override
    public long solvePart1() {
        return findUntil(5);
    }

    @Override
    public long solvePart2() {
        return findUntil(6);
    }
}
