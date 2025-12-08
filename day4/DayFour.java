package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DayFour {
    public static void main(String[] args) throws IOException {
        String filename = "day4/input.txt";

        List<String> grid = Files.readAllLines(Paths.get(filename));

        int rolls = countRolls(grid);
        System.out.println("Total rolls accessed = " + rolls);

        int rollIterations = countTotalRolls(grid);
        System.out.println("Total rolls accessed after many iterations = " + rollIterations);
    }

    /**
     * Counts the total rolls accessed after multiple iterations.
     *
     * @param grid
     * @return integer total
     */
    private static int countTotalRolls(List<String> grid) {
        List<String> g = grid;
        int count = countRolls(grid);
        int total = count;

        while (count != 0) {
            g = removeRolls(g);
            count = countRolls(g);
            total += count;
        }

        return total;
    }

    /**
     * Counts number of accessible rolls by a forklift in a grid.
     *
     * @param grid
     * @return
     */
    private static int countRolls(List<String> grid) {
        int rows = grid.size();
        int cols = grid.get(0).length();
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isAccessible(grid, i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static List<String> removeRolls(List<String> grid) {
        List<StringBuilder> newGrid = new ArrayList<>();
        for (String row : grid) {
            newGrid.add(new StringBuilder(row));
        }

        int rows = grid.size();
        int cols = grid.get(0).length();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isAccessible(grid, i, j)) {
                    newGrid.get(i).setCharAt(j, '.');
                }
            }
        }

        List<String> result = newGrid.stream()
                .map(StringBuilder::toString)
                .toList(); // Java 16+

        return result;
    }

    /**
     * Checks whether a character is accessible by checking if there are fewer than four
     * adjacent rolls.
     *
     * @param grid
     * @param row
     * @param col
     * @return
     */
    private static boolean isAccessible(List<String> grid, int row, int col) {
        if (getCharacter(grid, row, col) != '@') {
            return false;
        }

        int count = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (row + i < 0 || col + j < 0 || row + i >= grid.size() || col + j >= grid.get(0).length()) {
                    continue;
                }
                if (getCharacter(grid, row + i, col + j) == '@' && (i != 0 || j != 0)) {
                    count++;
                }
            }
        }

        return count < 4;
    }

    /**
     * Gets the character at a specific coordinate in a grid.
     *
     * @param grid
     * @param row
     * @param col
     * @return
     */
    private static char getCharacter(List<String> grid, int row, int col) {
        return grid.get(row).charAt(col);
    }
}
