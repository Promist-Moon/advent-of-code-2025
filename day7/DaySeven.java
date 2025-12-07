package day7;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DaySeven {
    public static void main(String[] args) throws IOException {
        String filename = "day7/manifold.txt";

        List<String> grid = Files.readAllLines(Paths.get(filename));

        int splits = countSplits(grid);
        System.out.println("Total splits = " + splits);
    }

    private static int countSplits(List<String> grid) {
        int splits = 0;
        int rows = grid.size();
        int cols = grid.get(0).length();

        int sourceRow = -1;
        int sourceCol = -1;

        for (int r=0; r < rows; r++) {
            for (int c=0; c<cols; c++) {
                if (grid.get(r).charAt(c) == 'S') {
                    sourceRow = r;
                    sourceCol = c;
                    break;
                }
            }
        }
        if (sourceRow == -1) {
            throw new IllegalArgumentException("No source 'S' found in grid");
        }

        // make a boolean row to keep track of columns with beams
        boolean[] beamColumns = new boolean[cols];
        beamColumns[sourceCol] = true;

        for (int r=sourceRow + 1; r < rows; r++) {
            for (int c=0; c<cols; c++) {
                if (!beamColumns[c]) {
                    continue; // no beam in this column
                }

                char cell = grid.get(r).charAt(c);

                if (cell == '^') {
                    // split the beam
                    beamColumns[c-1] = true;
                    beamColumns[c+1] = true;
                    beamColumns[c] = false;
                    splits++;
                } 
            }
        }

        return splits;
    }
}
