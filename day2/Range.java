package day2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Range {
    private long start;
    private long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
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

    public static List<Range> mergeRanges(List<Range> ranges) {
        List<Range> nonOverlapRanges = new ArrayList<>();

        ranges.sort(Comparator.comparingLong(Range::getStart));

        Range current = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            Range next = ranges.get(i);
            if (current.isCombinable(next)) {
                current = current.combine(next);
                continue;
            }
            nonOverlapRanges.add(current);
            current = next;
        }

        nonOverlapRanges.add(current);
        return nonOverlapRanges;

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

    // Add method for checking overlap
    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    /**
     * Checks if two Ranges are combinable.
     * Two ranges are combinable if the ranges overlap.
     *
     * @param other
     * @return true if combinable
     */
    public boolean isCombinable(Range other) {
        return this.start <= other.end && other.start <= this.end;
    }

    /**
     * Combines combinable ranges.
     *
     * @param other
     * @return
     */
    public Range combine(Range other) {
        long newStart = Math.min(this.getStart(), other.getStart());
        long newEnd = Math.max(this.getEnd(), other.getEnd());

        return new Range(newStart, newEnd);
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
