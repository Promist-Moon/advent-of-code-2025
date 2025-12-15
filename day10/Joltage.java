package day10;

import java.util.Arrays;

/**
 * Represents the joltage requirements of a machine.
 */
public class Joltage {

    int[] joltages;

    /**
     * Initialises a blank joltage with a list of 0s as initial joltages.
     *
     * @param size
     */
    public Joltage(int size) {
        this.joltages = new int[size];
    }

    public Joltage(int[] joltages) {
        this.joltages = joltages;
    }

    /**
     * Parses a string of the form {int,int,..,int} into a Joltage object.
     *
     * @param s
     * @return
     */
    /**
     * Parses a string of the form {int,int,..,int} into a Joltage object.
     *
     * @param s
     * @return
     */
    public static Joltage parseJoltage(String s) {
        s = s.substring(1, s.length() - 1);
        String[] parts = s.split(",");

        int[] joltages = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            joltages[i] = Integer.parseInt(parts[i].trim());
        }

        return new Joltage(joltages);
    }

    public int size() {
        return joltages.length;
    }

    public int[] getJoltages() {
        return this.joltages;
    }

    public Joltage press(Button b) {
        int[] newJoltages = joltages.clone();
        for (int i : b.getIndices()) {
            newJoltages[i]++;
        }

        return new Joltage(newJoltages);
    }

    public boolean exceeds(Joltage j) {
        for (int i = 0; i < size(); i++) {
            if (this.joltages[i] > j.joltages[i]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Joltage other = (Joltage) obj;

        return Arrays.equals(this.joltages, other.joltages);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(joltages);
    }

    @Override
    public String toString() {
        String s = "{";
        for (int i : joltages) {
            s += i + ",";
        }
        return s + "}";
    }
}
