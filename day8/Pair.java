package day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a pair of coordinates.
 */
public class Pair {
    private Coordinate c1;
    private Coordinate c2;

    public Pair(Coordinate c1, Coordinate c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Coordinate getC1() {
        return this.c1;
    }

    public Coordinate getC2() {
        return this.c2;
    }

    public double getDistance() {
        return c1.getDistance(c2);
    }

    /**
     * Generates a list of all possible pairs from a list of coordinates.
     *
     * @param coordinates
     * @return
     */
    public static List<Pair> getPairsList(List<Coordinate> coordinates) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = i + 1; j < coordinates.size(); j++) {
                pairs.add(new Pair(coordinates.get(i), coordinates.get(j)));
            }
        }

        pairs.sort(Comparator.comparing(Pair::getDistance));

        return pairs;
    }

    @Override
    public String toString() {
        return c1.toString() + "& " + c2.toString();
    }
}
