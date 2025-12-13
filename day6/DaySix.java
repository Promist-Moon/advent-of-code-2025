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

        // use same operators for part 1 and 2
        String[] operators = arrays.getLast();

        int columns = operands.getFirst().size();

        // part 1
        long sum1 = 0;

        for (int i = 0; i < columns; i++) {
            List<Long> numbers = new ArrayList<>();

            for (int j = 0; j < operands.size(); j++) {
                Long o = operands.get(j).get(i);
                numbers.add(o);
            }

            sum1 += solveEquation(numbers, operators[i]);

        }

        System.out.println("Sum of operations: " + sum1);

        // part 2
        List<List<String>> newArrays = parseNewRows(strings, findColumnIndices(strings.getLast()));

        long sum2 = 0;

        for (int i = 0; i < columns; i++) {
            List<String> nos = new ArrayList<>();

            for (int j = 0; j < newArrays.size(); j++) {
                String o = newArrays.get(j).get(i);
                nos.add(o);
            }

            List<Long> numbers = parseNewOperands(nos);

            sum2 += solveEquation(numbers, operators[i]);

        }

        System.out.println("New sum of operations: " + sum2);
    }

    /**
     * Parses each row of strings into string arrays.
     * This will help parse it into long operands later on.
     *
     * @param strings
     * @return
     */
    private static List<String[]> parseRows(List<String> strings) {
        List<String[]> arrays = new ArrayList<>();
        for (String s : strings) {
            String[] a = s.split("\\s+");
            arrays.add(a);
        }

        return arrays;
    }

    private static List<Integer> findColumnIndices(String string) {
        // find start columns for operator
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != ' ') {
                result.add(i);
            }
        }

        return result;
    }

    private static List<String> sliceByColumns(String line, List<Integer> columnStarts) {
        List<String> cells = new ArrayList<>();
        for (int c = 0; c < columnStarts.size(); c++) {
            int start = columnStarts.get(c);
            int end = (c + 1 < columnStarts.size()) ? columnStarts.get(c + 1) : line.length();
            if (start >= line.length()) {
                cells.add("");
            } else {
                cells.add(line.substring(start, Math.min(end, line.length())));
            }
        }
        return cells;
    }

    /**
     * Parses each row of strings into string arrays.
     * For part 2
     *
     * @param strings
     * @return
     */
    private static List<List<String>> parseNewRows(List<String> strings, List<Integer> starts) {
        List<List<String>> arrays = new ArrayList<>();
        for (String s : strings) {
            List<String> sp = sliceByColumns(s, starts);
            arrays.add(sp);
        }

        arrays.removeLast();

        return arrays;
    }

    /**
     * Parses an array of Strings into a list of Longs.
     * Used in part 1 of day 8.
     *
     * @param strings
     * @return
     */
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

    /**
     * Parses a list of Strings, which should be the operands including whitespace, into a list of Longs.
     * Used in part 2 of day 8.
     *
     * @param opsWithWhitespace
     * @return
     */
    private static List<Long> parseNewOperands(List<String> opsWithWhitespace) {
        List<Long> operands = new ArrayList<>();
        int length = 0;
        for (String s : opsWithWhitespace) {
            if (s.length() > length) {
                length = s.length();
            }
        }

        for (int i = length - 1; i >= 0; i--) {
            long no = 0;
            for (String op : opsWithWhitespace) {
                if (i >= op.length()) {
                    continue;
                }

                char c = op.charAt(i);


                if (c == ' ') {
                    continue;
                }

                int digit = c - '0';

                no = no * 10 + digit;

            }

            if (no != 0) {
                operands.add(no);
            }
        }

        return operands;
    }

    /**
     * Solves the equation with a given list of operands and an operator.
     *
     * @param operands
     * @param operator "*" or "+"
     * @return
     * @throws Exception
     */
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
