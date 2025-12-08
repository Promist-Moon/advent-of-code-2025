package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayThree {

    public static void main(String[] args) throws IOException {
        String filename = "day3/input.txt";

        List<String> banks = Files.readAllLines(Paths.get(filename));

        // part 1
        int totalJoltage = findTotalJoltage(banks);
        System.out.println("Total output joltage = " + totalJoltage);

        // part 2
        double newTotalJoltage = findNewTotalJoltage(banks);
        System.out.println("Total new output joltage = " + newTotalJoltage);

    }

    /**
     * Finds the total joltage for a two digit joltage.
     * The solution code for part 1.
     *
     * @param banks list of battery banks
     * @return total value of joltage
     */
    private static int findTotalJoltage(List<String> banks) {
        int totalJoltage = 0;
        int rows = banks.size();

        for (int r = 0; r < rows; r++) {
            String bank = banks.get(r);
            int joltage = findLargestJoltage(bank);
            totalJoltage += joltage;
        }

        return totalJoltage;
    }

    /**
     * Finds the largest 2-digit joltage of a battery.
     *
     * @param bank a battery bank, which is a string of digits
     * @return a two-digit integer
     */
    private static int findLargestJoltage(String bank) {
        // find largest digit
        int largestIndex = 0;
        int largestDigit = bank.charAt(largestIndex) - 48;

        for (int i = 0; i < bank.length() - 1; i++) {
            int digit = bank.charAt(i) - 48;
            if (digit > largestDigit) {
                largestIndex = i;
                largestDigit = bank.charAt(largestIndex) - 48;
            }
        }

        int secondIndex = largestIndex + 1;
        int secondDigit = bank.charAt(secondIndex) - 48;

        for (int i = largestIndex + 1; i < bank.length(); i++) {
            int digit = bank.charAt(i) - 48;
            if (digit > secondDigit) {
                secondIndex = i;
                secondDigit = bank.charAt(secondIndex) - 48;
            }
        }

        int joltage = largestDigit * 10 + secondDigit;
        System.out.println(joltage);
        return joltage;
    }

    /**
     * Finds the total new joltage for a twelve digit joltage.
     * The solution code for part 2.
     *
     * @param banks list of battery banks
     * @return total value of joltage
     */
    private static double findNewTotalJoltage(List<String> banks) {
        double totalJoltage = 0;
        int rows = banks.size();

        for (int r = 0; r < rows; r++) {
            String bank = banks.get(r);
            double joltage = findNewLargestJoltage(bank);
            totalJoltage += joltage;
        }

        return totalJoltage;
    }

    /**
     * Finds the largest 12-digit joltage of a battery.
     *
     * @param bank a battery bank, which is a string of digits
     * @return a twelve-digit integer
     */
    private static double findNewLargestJoltage(String bank) {
        // define indices array as the array of digit values
        int[] indices = new int[12];
        indices[0] = 0;

        int[] digits = new int[12];
        digits[0] = 0;

        // find largest digit (0)
        for (int i = 0; i < bank.length() - 11; i++) {
            int digit = bank.charAt(i) - 48;
            if (digit > digits[0]) {
                indices[0] = i;
                digits[0] = bank.charAt(i) - 48;
            }
        }

        double joltage = digits[0];

        // general, for the (i+1)th digit (with index i)
        for (int i = 1; i < 12; i++) {
            for (int j = indices[i-1] + 1; j < bank.length() - 11 + i; j++) {
                int digit = bank.charAt(j) - 48;
                if (digit > digits[i]) {
                    indices[i] = j;
                    digits[i] = bank.charAt(j) - 48;
                }
            }

            joltage = joltage * 10 + digits[i];
        }

        System.out.println(joltage);
        return joltage;
    }
}
