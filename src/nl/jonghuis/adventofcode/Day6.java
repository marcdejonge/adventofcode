package nl.jonghuis.adventofcode;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 extends Day {
    static class Job {
        final JobType type;
        final int fromX, fromY;
        final int toX, toY;

        public Job(String line) {
            Matcher matcher = REGEX.matcher(line);
            if (matcher.matches()) {
                type = JobType.parse(matcher.group(1));
                fromX = Integer.parseInt(matcher.group(2));
                fromY = Integer.parseInt(matcher.group(3));
                toX = Integer.parseInt(matcher.group(4));
                toY = Integer.parseInt(matcher.group(5));
            } else {
                throw new RuntimeException("Illegal line: " + line);
            }
        }
    }

    static enum JobType {
                         ON,
                         OFF,
                         TOGGLE;

        static JobType parse(String action) {
            if ("turn on".equals(action.toLowerCase())) {
                return ON;
            } else if ("turn off".equals(action.toLowerCase())) {
                return OFF;
            } else if ("toggle".equals(action.toLowerCase())) {
                return TOGGLE;
            } else {
                throw new RuntimeException("Illegal line");
            }
        }
    }

    private static final Pattern REGEX = Pattern.compile("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)",
                                                         Pattern.CASE_INSENSITIVE);
    private List<Job> jobs;

    @Override
    public long solvePart1() {
        boolean[][] lights = new boolean[1000][1000];
        for (Job job : jobs) {
            for (int x = job.fromX; x <= job.toX; x++) {
                for (int y = job.fromY; y <= job.toY; y++) {
                    if (job.type == JobType.OFF) {
                        lights[x][y] = false;
                    } else if (job.type == JobType.ON) {
                        lights[x][y] = true;
                    } else if (job.type == JobType.TOGGLE) {
                        lights[x][y] = !lights[x][y];
                    }
                }
            }
        }

        int count = 0;

        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (lights[x][y]) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public long solvePart2() {
        int[][] lights = new int[1000][1000];
        for (Job job : jobs) {
            for (int x = job.fromX; x <= job.toX; x++) {
                for (int y = job.fromY; y <= job.toY; y++) {
                    if (job.type == JobType.OFF) {
                        lights[x][y] = Math.max(0, lights[x][y] - 1);
                    } else if (job.type == JobType.ON) {
                        lights[x][y] += 1;
                    } else if (job.type == JobType.TOGGLE) {
                        lights[x][y] += 2;
                    }
                }
            }
        }

        int count = 0;

        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                count += lights[x][y];
            }
        }

        return count;
    }

    @Override
    public void solvePrepare() throws IOException {
        jobs = readLines().stream().map(Job::new).collect(Collectors.toList());
    }

}
