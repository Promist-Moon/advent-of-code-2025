package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import day5.Range;

public class DayTwo {

    public static void main(String[] args) throws IOException {
        String filename = "day5/input.txt";

        List<Range> ranges = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ranges = parseRanges(values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
}
