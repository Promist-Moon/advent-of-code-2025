package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayTen {
    public static void main(String[] args) throws IOException {
        String filename = "day10/sampleinput.txt";

        List<String> machineStrings = Files.readAllLines(Paths.get(filename));
        List<Machine> machines = parseMachines(machineStrings);

        //System.out.println("Fewest total presses for indicator diagram: " + findFewestTotalPresses(machines));
        System.out.println("Fewest total presses for joltage requirements: " + findFewestTotalPressesForJoltage(machines));
    }

    /**
     * Parses a list of strings representing machines into Machine objects.
     *
     * @param strings
     * @return
     */
    private static List<Machine> parseMachines(List<String> strings) {
        List<Machine> machines = new ArrayList<>();
        for (String s : strings) {
            machines.add(Machine.parseMachine(s));
        }

        return machines;
    }

    /**
     * Finds the fewest total presses of buttons needed to replicate an indicator light diagram.
     * Solution code for part 1.
     *
     * @param machines
     * @return
     */
    private static long findFewestTotalPresses(List<Machine> machines) {
        long sum = 0;

        for (Machine m : machines) {
            sum += m.findFewestPresses();
        }

        return sum;
    }

    /**
     * Finds the fewest total presses of buttons needed to replicate a joltage requirement.
     * Solution code for part 2.
     *
     * @param machines
     * @return
     */
    private static long findFewestTotalPressesForJoltage(List<Machine> machines) {
        long sum = 0;

        for (Machine m : machines) {
            sum += m.findFewestPressesForJoltageILP();
        }

        return sum;
    }

}
