package day9;

import day2.Range;

public class Coordinate {

    private long x;
    private long y;

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    // Parses a string in format [x],[y] into a Coordinate object
    public static Coordinate parseCoordinate(String string) {
        long x = 0;
        long y = 0;

        int commaIndex = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ',') {
                commaIndex = i;
            }
        }

        // initialise start
        for (int i = 0; i < commaIndex; i++) {
            x = x * 10 + (string.charAt(i) - 48);
        }

        // initialise end
        for (int i = commaIndex + 1; i < string.length(); i++) {
            y = y * 10 + (string.charAt(i) - 48);
        }

        return new Coordinate(x, y);
    }

    /**
     * Calculates the Euclidean distance between two coordinates
     *
     * @param other a Coordinate object
     * @return
     */
    public double getDistance(Coordinate other) {
        long xDist = (this.x - other.x) ^ 2;
        long yDist = (this.y - other.y) ^ 2;
        return Math.sqrt(xDist + yDist);
    }

    /**
     * Calculates the area of a rectangle formed between two coordinates
     *
     * @param other a Coordinate object
     * @return
     */
    public long getArea(Coordinate other) {
        long xDist = this.x - other.x + 1;
        long yDist = this.y - other.y + 1;
        return xDist * yDist;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
