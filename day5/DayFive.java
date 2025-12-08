package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayFive {

    public static void main(String[] args) throws IOException {
        String filename = "day5/input.txt";

        List<String> rangeStrings = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        boolean isRange = true; // before blank line

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    isRange = false; // switch to numbers
                    continue;
                }

                if (isRange) {
                    rangeStrings.add(line);
                } else {
                    ids.add(Long.parseLong(line));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Range> ranges = parseRanges(rangeStrings);
        int freshCount = findValidIdsCount(ids, ranges);
        System.out.println("Number of available ingredients: " + freshCount);

        long ingredientCount = findValidIngredientsCount(ranges);
        System.out.println("Number of available ingredients: " + ingredientCount);
    }

    /**
     * Parses a list of Strings representing ranges into a list of Ranges.
     *
     * @param ranges list of Strings.
     * @return list of Range objects.
     */
    private static List<Range> parseRanges(List<String> ranges) {
        List<Range> rangeObjects = new ArrayList<>();

        for (String range : ranges) {
            Range rangeObject = Range.parseRange(range);
            rangeObjects.add(rangeObject);
        }

        return rangeObjects;
    }

    /**
     * Counts the number of valid ids for part 1.
     *
     * @param ids
     * @param ranges
     * @return integer representing count of available ingredients.
     */
    private static int findValidIdsCount(List<Long> ids, List<Range> ranges) {
        List<Long> validIds = new ArrayList<>();
        for (Long id : ids) {
            for (Range range : ranges) {
                if (range.contains(id)) {
                    validIds.add(id);
                    break;
                }
            }
        }

        return validIds.size();
    }

    /**
     * Counts the number of ingredients that are valid for part 2.
     *
     * @param ranges list of ranges (can be overlapping).
     * @return a long representing number of ingredients.
     */
    private static long findValidIngredientsCount(List<Range> ranges) {
        List<Range> nonOverlapRanges = Range.mergeRanges(ranges);
        long size = 0;

        for (Range range : nonOverlapRanges) {
            size += range.size();
        }

        return size;
    }
}
