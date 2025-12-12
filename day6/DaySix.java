package day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaySix {
    public static void main(String[] args) throws Exception {
        String filename = "day6/input.txt";

        List<String> strings = Files.readAllLines(Paths.get(filename));

        List<String[]> arrays = parseRows(strings);
        List<List<Long>> operands = new ArrayList<>();

        for (int i = 0; i < arrays.size() - 1; i++) {
            operands.add(parseOperands(arrays.get(i)));
        }

        String[] operators = arrays.getLast();

        int columns = operands.getFirst().size();
        long sum = 0;

        for (int i = 0; i < columns; i++) {
            List<Long> numbers = new ArrayList<>();
            for (int j = 0; j < operands.size(); j++) {
                Long o = operands.get(j).get(i);
                numbers.add(o);
            }

            sum += solveEquation(numbers, operators[i]);

        }

        System.out.println("Sum of operations: " + sum);
    }

    private static List<String[]> parseRows(List<String> strings) {
        List<String[]> arrays = new ArrayList<>();
        for (String s : strings) {
            String[] a = s.split("\\s+");
            arrays.add(a);
        }

        return arrays;
    }

    private static List<Long> parseOperands(String[] strings) {
        List<Long> operands = new ArrayList<>();
        for (String s : strings) {
            if (s == "") {
                continue;
            }
            operands.add(Long.parseLong(s));
        }

        return operands;
    }

    private static long solveEquation(List<Long> operands, String operator) throws Exception {
        long sol;
        if (Objects.equals(operator, "*")) {
            sol = 1;
            for (Long l : operands) {
                sol *= l;
            }
        } else if (Objects.equals(operator, "+")) {
            sol = 0;
            for (Long l : operands) {
                sol += l;
            }
        } else {
            throw new Exception("Not valid operation: " + operator);
        }

        return sol;
    }
}
