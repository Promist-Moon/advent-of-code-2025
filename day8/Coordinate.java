package day8;

/**
 * A Coordinate object represents the position of one junction box.
 */
public class Coordinate {
    private long x;
    private long y;
    private long z;

    public Coordinate(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Parses a string in the form [x], [y], [z] into a Coordinate object
     * @param string
     * @return
     */
    public static Coordinate parseCoordinate(String string) {
        long x = 0;
        long y = 0;
        long z = 0;

        int[] commaIndices = new int[2];
        int commaCount = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ',') {
                commaIndices[commaCount] = i;
                commaCount++;
            }
        }

        // initialise x-coordinate
        for (int i = 0; i < commaIndices[0]; i++) {
            x = x * 10 + (string.charAt(i) - 48);
        }

        // initialise y-coordinate
        for (int i = commaIndices[0] + 1; i < commaIndices[1]; i++) {
            y = y * 10 + (string.charAt(i) - 48);
        }

        // initialise z-coordinate
        for (int i = commaIndices[1] + 1; i < string.length(); i++) {
            z = z * 10 + (string.charAt(i) - 48);
        }

        return new Coordinate(x, y, z);

    }

    /**
     * Calculates the Euclidean distance between two coordinates
     *
     * @param other a Coordinate object
     * @return
     */
    public double getDistance(Coordinate other) {
        long xDist = (this.x - other.x);
        long yDist = (this.y - other.y);
        long zDist = (this.z - other.z);
        return Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);
    }

    public long getX() {
        return this.x;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate other = (Coordinate) o;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
}
