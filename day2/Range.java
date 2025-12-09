package day2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Range {
    private long start;
    private long end;

    private int startLength;
    private int endLength;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;

        this.startLength = String.valueOf(start).length();
        this.endLength = String.valueOf(end).length();
    }

    // Parses a string in format [start]-[end] into a Range object
    public static Range parseRange(String range) {
        long start = 0;
        long end = 0;

        int dashIndex = 0;
        for (int i = 0; i < range.length(); i++) {
            if (range.charAt(i) == '-') {
                dashIndex = i;
            }
        }

        // initialise start
        for (int i = 0; i < dashIndex; i++) {
            start = start * 10 + (range.charAt(i) - 48);
        }

        // initialise end
        for (int i = dashIndex + 1; i < range.length(); i++) {
            end = end * 10 + (range.charAt(i) - 48);
        }

        return new Range(start, end);
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    // returns size of range
    public long size() {
        return end - start + 1;
    }

    /**
     * Finds a list of invalid ids in the range, where an invalid id has a sequence of
     * digits repeated exactly twice.
     *
     * @return
     */
    public List<Long> findInvalidIds() {
        List<Long> ids = new ArrayList<>();

        for (int i = 2; i < startLength; i++) {
            for (long current = start; current <= end; current++) {
                String s = Long.toString(current);
                int len = s.length();
                if (len % i != 0) continue;

                int half = len / 2;
                String left = s.substring(0, half);
                String right = s.substring(half);

                if (left.equals(right)) {
                    ids.add(current);
                }
            }
        }

        return ids;
    }

    /**
     * Finds the sum of invalid ids.
     *
     * @return
     */
    public long findInvalidIdsSum() {
        long sum = findInvalidIds().stream()
                .reduce(0L, Long::sum);

        return sum;
    }

    /**
     * Finds a list of invalid ids in the range, where an invalid id has a sequence of
     * digits repeated at least twice.
     *
     * @return
     */
    public List<Long> findNewInvalidIds() {
        List<Long> ids = new ArrayList<>();

        for (int i = 2; i <= endLength; i++) {
            for (long current = start; current <= end; current++) {
                String s = Long.toString(current);
                int len = s.length();
                if (len % i != 0) continue;

                int sect = len / i;
                List<String> sections = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    String section = s.substring(j * sect, (j + 1) * sect);
                    sections.add(section);
                }

                boolean allEqual = sections.stream().distinct().limit(2).count() == 1;

                if (allEqual && !ids.contains(current)) {
                    ids.add(current);
                }
            }
        }

        return ids;
    }

    /**
     * Finds the sum of new invalid ids.
     *
     * @return
     */
    public long findNewInvalidIdsSum() {
        long sum = findNewInvalidIds().stream()
                .reduce(0L, Long::sum);

        return sum;
    }

    // Override equals() and hashCode() to compare
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return start == range.start && end == range.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
