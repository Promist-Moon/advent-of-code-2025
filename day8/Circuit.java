package day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Circuit class represents a list of connected junction boxes, represented by Coordinates.
 */
public class Circuit {
    private List<Coordinate> boxes = new ArrayList<>();

    public Circuit(Pair pair) {
        this.boxes = new ArrayList<>();
        boxes.add(pair.getC1());
        boxes.add(pair.getC2());
    }

    public List<Coordinate> getBoxes() {
        return this.boxes;
    }

    public boolean connects(Coordinate c) {
        return boxes.contains(c);
    }

    public boolean connects(Pair p) {
        return boxes.contains(p.getC1()) && boxes.contains(p.getC2());
    }

    public void addConnection(Coordinate c) {
        if (!boxes.contains(c)) {
            boxes.add(c);
        }
    }

    public int getSize() {
        return boxes.size();
    }

    public String toString() {
        String s = "[";
        for (Coordinate c : boxes) {
            s = s + c;
            s += ";";
        }
        return s + "]";
    }
}
