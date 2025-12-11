package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayNine {

    public static void main(String[] args) throws IOException {
        String filename = "day9/input.txt";

        List<String> coords = Files.readAllLines(Paths.get(filename));
        List<Coordinate> coordinates = parseCoordinates(coords);

        long maxArea = findMaxArea(coordinates);
        System.out.println("Maximum area = " + maxArea);
    }

    /**
     * Parses a list of Strings representing coordinates into a list of Coordinates.
     *
     * @param coordinates list of Strings.
     * @return list of Coordinate objects.
     */
    private static List<Coordinate> parseCoordinates(List<String> coordinates) {
        List<Coordinate> coords = new ArrayList<>();

        for (String coord : coordinates) {
            Coordinate coordObject = Coordinate.parseCoordinate(coord);
            coords.add(coordObject);
        }

        System.out.println(coords);

        return coords;
    }

    private static long findMaxArea(List<Coordinate> coordinates) {
        long maxArea = 0;
        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = i + 1; j < coordinates.size(); j++) {
                long area = coordinates.get(i).getArea(coordinates.get(j));
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }
}
