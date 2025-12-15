package day10;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a button wiring schematic
 */
public class Schematic {

    private List<Button> buttons;

    public Schematic(List<Button> buttons) {
        this.buttons = buttons;
    }

    public static Schematic parseSchematic(String s) {
        int start = 0;
        int end = 0;
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            if (start < end) {
                Button b = Button.parseButton(s.substring(start, end + 1));
                if (!buttons.contains(b)) {
                    buttons.add(b);
                }
            }

            if (s.charAt(i) == '(') {
                start = i;
            } else if (s.charAt(i) == ')') {
                end = i;
            }
        }

        return new Schematic(buttons);
    }

    public List<Button> getButtons() {
        return this.buttons;
    }

    @Override
    public String toString() {
        return this.buttons.toString();
    }
}
