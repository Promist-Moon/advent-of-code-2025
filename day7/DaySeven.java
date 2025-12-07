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

        long timelines = countTimelines(grid);
        System.out.println("Total timelines = " + timelines);
    }

    private static int[] findSource(List<String> grid) {
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

        int[] source = { sourceRow, sourceCol };
        return source;
    }

    private static int countSplits(List<String> grid) {
        int splits = 0;
        int rows = grid.size();
        int cols = grid.get(0).length();

        // find source
        int[] source = findSource(grid);
        int sourceRow = source[0];
        int sourceCol = source[1];

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

    private static long countTimelines(List<String> grid) {
        long timelines = 1L;
        int rows = grid.size();
        int cols = grid.get(0).length();

        // find source
        int[] source = findSource(grid);
        int sourceRow = source[0];
        int sourceCol = source[1];

        // make a boolean row to keep track of possible columns with beams
        long[] beamColumns = new long[cols];
        beamColumns[sourceCol] = 1L;

        for (int r=sourceRow + 1; r < rows; r++) {
            long[] next = new long[cols];
            for (int c=0; c<cols; c++) {
                long count = beamColumns[c];
                if (count == 0) {
                    continue; // no beam in this column
                }

                char cell = grid.get(r).charAt(c);

                if (cell == '^') {
                    // split the beam
                    if (c - 1 >= 0) {
                        next[c - 1] += count;
                    }
                    if (c + 1 < cols) {
                        next[c + 1] += count;
                    }

                    timelines += count;
                } else {
                    if (r + 1 < rows) {
                        next[c] += count;
                    }
                }
            }
            beamColumns = next;
        }

        return timelines;
    }
}
