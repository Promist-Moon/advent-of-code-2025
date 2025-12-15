package day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an indicator light diagram for a machine.
 */
public class Indicator {

    private List<Boolean> lights;

    public Indicator(int size) {
        List<Boolean> init = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            init.add(false);
        }

        this.lights = init;
    }
    public Indicator(List<Boolean> lights) {
        this.lights = lights;
    }

    /**
     * Parses a string of the form [..#.] into an Indicator object.
     * @param s
     * @return
     */
    public static Indicator parseIndicator (String s) {
        List<Boolean> lights = new ArrayList<>();

        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == '#') {
                lights.add(true);
            } else {
                // char == '.'
                lights.add(false);
            }
        }

        return new Indicator(lights);
    }

    public int size() {
        return lights.size();
    }

    public Indicator press(Button b) {
        List<Boolean> newLights = new ArrayList<>(lights);
        for (Integer i : b.getIndices()) {
            boolean state = lights.get(i);
            newLights.set(i, !state);
        }

        return new Indicator(newLights);
    }

    public Indicator press(List<Button> buttons) {
        Indicator initial = null;
        for (Button b : buttons) {
            initial = press(b);
        }

        return initial;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Indicator other = (Indicator) obj;

        return Objects.equals(this.lights, other.lights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lights);
    }

    @Override
    public String toString() {
        String s = "[";
        for (Boolean b : lights) {
            if (b) {
                s += "#";
            } else {
                s += ".";
            }
        }

        return s + "]";
    }
}
