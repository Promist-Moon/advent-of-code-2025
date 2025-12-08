package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DayOne {

    public static void main(String[] args) throws IOException {
        String filename = "day1/input.txt";

        List<String> instructions = Files.readAllLines(Paths.get(filename));

        int password = findPassword(instructions);
        System.out.println("Password = " + password);

        int newPassword = findNewPassword(instructions);
        System.out.println("Password = " + newPassword);

    }

    /**
     * Finds the password for part 1 of Day 1
     *
     * @param instructions list of string of instructions, ie the input
     * @return number of times dial lands on 0
     */
    private static int findPassword(List<String> instructions) {
        int position = 50; // initial position
        int password = 0; // number of times position is at 0

        int rows = instructions.size();
        for (int r = 0; r < rows; r++) {
            int value = readInstruction(instructions.get(r));

            position += value;

            if (position % 100 == 0) {
                password++;
            }
        }

        return password;
    }

    /**
     * Finds the password for part 2 of Day 1
     *
     * @param instructions list of string of instructions, ie the input
     * @return number of times dial passes 0
     */
    private static int findNewPassword(List<String> instructions) {
        double position = 50; // initial position
        int password = 0; // number of times position is at 0

        int rows = instructions.size();
        for (int r = 0; r < rows; r++) {
            int value = readInstruction(instructions.get(r));
            double newPosition = position + value;

            double p;
            double np;

            if (newPosition > position) {
                p = Math.floor(position/100);
                np = Math.floor(newPosition/100);
            } else if (newPosition < position) {
                p = Math.floor((position-1)/100);
                np = Math.floor((newPosition-1)/100);
            } else {
                p = 0;
                np = 0;
            }
            password += Math.abs(np - p);

            position = newPosition;
        }

        return password;
    }

    private static int readInstruction(String instruction) {
        char direction = instruction.charAt(0); // 'L' or 'R'
        int value = Integer.parseInt(instruction.substring(1));

        int amount = 0;

        if (direction == 'L') {
            // subtract
            amount = -value;
        } else if (direction == 'R') {
            amount = value;
        }

        return amount;
    }
}
