package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DayEight {
    public static void main(String[] args) throws IOException {
        String filename = "day8/input.txt";

        List<String> strings = Files.readAllLines(Paths.get(filename));

        List<Coordinate> coordinates = parseCoordinates(strings);

        List<Circuit> circuits = findCircuits(coordinates, 1000);
        System.out.println("Number of circuits: " + circuits.size());

        int product = 1;
        for (int i = 0; i < Math.min(3, circuits.size()); i++) {
            product *= circuits.get(i).getSize();
        }
        System.out.println("Circuits size multiplied: " + product);

        Pair lastPair = findCoordsToMakeOne(coordinates);
        System.out.println("Last pair: " + lastPair);
        long prod = lastPair.getC1().getX() * lastPair.getC2().getX();

        System.out.println("Product of x-coordinates: " + prod);
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

    /**
     * Find number of circuits after joining a certain number of pairs of junction box coordinates.
     *
     * @param coordinates list of coordinates of junction boxes.
     * @param count number of pairs of junction boxes to join.
     * @return a List of circuits.
     */
    private static List<Circuit> findCircuits(List<Coordinate> coordinates, int count) {
        List<Circuit> circuits = new ArrayList<>();

        List<Pair> pairs = Pair.getPairsList(coordinates);
        List<Pair> firstNPairs = pairs.stream().limit(count).toList();

        for (Pair p : firstNPairs) {
            // initialise first circuit
            if (circuits.isEmpty()) {
                Circuit c = new Circuit(p);
                circuits.add(c);
                continue;
            }

            boolean isDisconnected = true;

            List<Circuit> connections = new ArrayList<>();

            for (Circuit c : circuits) {
                // pair already fully inside this circuit
                if (c.connects(p)) {
                    isDisconnected = false;
                    connections.clear();
                    connections.add(c); // just to be explicit
                    break;
                }

                if (c.connects(p.getC1()) || c.connects(p.getC2())) {
                    isDisconnected = false;
                    connections.add(c);
                }
            }

            if (isDisconnected) {
                Circuit cct = new Circuit(p);
                circuits.add(cct);
                continue;
            }

            // attach endpoints to the first connected circuit
            Circuit base = connections.get(0);
            base.addConnection(p.getC1());
            base.addConnection(p.getC2());

            // if this pair bridges multiple circuits, merge them
            if (connections.size() > 1) {
                for (int i = 1; i < connections.size(); i++) {
                    Circuit other = connections.get(i);
                    for (Coordinate box : other.getBoxes()) {
                        base.addConnection(box);
                    }
                }

                circuits.removeAll(connections.subList(1, connections.size()));
            }
        }

        circuits.sort(Comparator.comparing(Circuit::getSize));

        return circuits.reversed();
    }

    /**
     * Find the last pair of coordinates needed to make all coordinates one big circuit.
     *
     * @param coordinates list of coordinates.
     * @return
     */
    private static Pair findCoordsToMakeOne(List<Coordinate> coordinates) {
        List<Circuit> circuits = new ArrayList<>();

        List<Pair> pairs = Pair.getPairsList(coordinates);

        Pair lastPair = null;

        for (Pair p : pairs) {

            // initialise first circuit
            if (circuits.isEmpty()) {
                Circuit c = new Circuit(p);
                circuits.add(c);
                continue;
            }

            boolean isDisconnected = true;

            List<Circuit> connections = new ArrayList<>();

            for (Circuit c : circuits) {
                // pair already fully inside this circuit
                if (c.connects(p)) {
                    isDisconnected = false;
                    connections.clear();
                    connections.add(c);
                    break;
                }

                if (c.connects(p.getC1()) || c.connects(p.getC2())) {
                    isDisconnected = false;
                    connections.add(c);
                }
            }

            if (isDisconnected) {
                Circuit cct = new Circuit(p);
                circuits.add(cct);
                continue;
            }

            // attach endpoints to the first connected circuit
            Circuit base = connections.get(0);
            base.addConnection(p.getC1());
            base.addConnection(p.getC2());

            // if this pair bridges multiple circuits, merge them
            if (connections.size() > 1) {
                for (int i = 1; i < connections.size(); i++) {
                    Circuit other = connections.get(i);
                    for (Coordinate box : other.getBoxes()) {
                        base.addConnection(box);
                    }
                }

                circuits.removeAll(connections.subList(1, connections.size()));
            }

            if (circuits.size() == 1 &&
                    circuits.get(0).getBoxes().size() == coordinates.size()) {
                lastPair = p;
                break;
            }
        }

        return lastPair;
    }
}
