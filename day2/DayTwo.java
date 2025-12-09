package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayTwo {

    public static void main(String[] args) throws IOException {
        String filename = "day2/input.txt";

        List<Range> ranges = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ranges.addAll(parseRanges(values));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long sum = countInvalids(ranges);
        System.out.println("Total sum of invalids: " + sum);

        long newSum = countNewInvalids(ranges);
        System.out.println("Total sum of new invalids: " + newSum);

    }

    /**
     * Parses a list of Strings representing ranges into a list of Ranges.
     *
     * @param rangeStrings list of Strings.
     * @return list of Range objects.
     */
    private static List<Range> parseRanges(String[] rangeStrings) {
        List<Range> ranges = new ArrayList<>();

        for (String range : rangeStrings) {
            Range rangeObject = Range.parseRange(range);
            ranges.add(rangeObject);
        }

        return ranges;
    }

    /**
     * Counts the sum of invalid ids for part 1, where an id is invalid if
     * a sequence of digits is repeated exactly twice.
     *
     * @param ranges
     * @return
     */
    private static long countInvalids(List<Range> ranges) {
        long sum = 0;
        for (Range range : ranges) {
            //System.out.println(range.findInvalidIds());
            sum += range.findInvalidIdsSum();
        }

        return sum;
    }

    /**
     * Counts the sum of invalid ids in a range for part 1, where an id is invalid if
     * a sequence of digits is repeated at least twice.
     *
     * @param ranges
     * @return
     */
    private static long countNewInvalids(List<Range> ranges) {
        long sum = 0;
        for (Range range : ranges) {
            // System.out.println(range.findNewInvalidIds());
            sum += range.findNewInvalidIdsSum();
        }

        return sum;
    }
}
