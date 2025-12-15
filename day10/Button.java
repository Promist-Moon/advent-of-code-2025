package day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a button toggle in a button wiring schematic
 */
public class Button {

    List<Integer> indices;

    public Button(List<Integer> indices) {
        this.indices = indices;
    }

    /**
     * Parses a string of the form (int,int,..,int) into a Button object
     * @param s
     * @return
     */
    public static Button parseButton(String s) {
        List<Integer> indices = new ArrayList<>();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                indices.add(c - '0');
            }
        }

        return new Button(indices);
    }

    public List<Integer> getIndices() {
        return this.indices;
    }

    @Override
    public String toString() {
        return indices.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Button other = (Button) obj;

        return Objects.equals(this.indices, other.indices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indices);
    }
}
