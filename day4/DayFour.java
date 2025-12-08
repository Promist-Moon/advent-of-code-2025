package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class DayFour {
    public static void main(String[] args) throws IOException {
        String filename = "day4/input.txt";

        List<String> grid = Files.readAllLines(Paths.get(filename));

        int rolls = countRolls(grid);
        System.out.println("Total rolls accessed = " + rolls);
    }

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

    private static char getCharacter(List<String> grid, int row, int col) {
        return grid.get(row).charAt(col);
    }
}
