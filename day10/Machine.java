package day10;

import com.google.ortools.Loader;
import com.google.ortools.sat.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Machine {

    private Indicator indicator;
    private Schematic schematic;
    private Joltage joltage;

    public Machine (Indicator indicator, Schematic schematic, Joltage joltage) {
        this.indicator = indicator;
        this.schematic = schematic;
        this.joltage = joltage;
    }

    public static Machine parseMachine(String s) {
        // indices of the following characters, in order: [, ], {, }
        int[] indices = new int[4];

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ']') {
                indices[1] = i;
            } else if (s.charAt(i) == '{') {
                indices[2] = i;
            } else if (s.charAt(i) == '}') {
                indices[3] = i;
            }
        }

        Indicator i = Indicator.parseIndicator(s.substring(0, indices[1] + 1));
        Schematic sc = Schematic.parseSchematic(s.substring(indices[1] + 2, indices[2]));
        Joltage j = Joltage.parseJoltage(s.substring(indices[2]));

        return new Machine(i, sc, j);
    }

    public Indicator getIndicator() {
        return this.indicator;
    }

    public Schematic getSchematic() {
        return this.schematic;
    }

    public Joltage getJoltage() {
        return this.joltage;
    }

    /**
     * Finds least button presses needed for indicator light diagram.
     * Solution for part 1.
     *
     * @return
     */
    public List<Button> findLeastPresses() {
        Indicator start = new Indicator(indicator.size());
        Indicator target = indicator;

        Queue<State> queue = new ArrayDeque<>();
        Set<Indicator> visited = new HashSet<>();

        queue.add(new State(start, new ArrayList<>()));
        visited.add(start);

        while (!queue.isEmpty()) {
            State cur = queue.poll();

            if (cur.indicator.equals(target)) {
                return cur.path;
            }

            for (Button b : schematic.getButtons()) {
                Indicator next = cur.indicator.press(b);

                if (visited.contains(next)) continue;

                visited.add(next);

                List<Button> newPath = new ArrayList<>(cur.path);
                newPath.add(b);

                queue.add(new State(next, newPath));
            }
        }

        return List.of();
    }

    /**
     * Finds total size of path of least presses in part 1.
     *
     * @return
     */
    public long findFewestPresses() {
        return findLeastPresses().size();
    }

    /**
     * Finds path of least presses in part 2 for a joltage requirement.
     *
     * @return
     */
    public List<Button> findLeastPressesForJoltage() {
        Joltage start = new Joltage(joltage.size());
        Joltage target = joltage;

        if (start.equals(target)) {
            return List.of();
        }

        Queue<Joltage> queue = new ArrayDeque<>();
        Set<Joltage> visited = new HashSet<>();
        HashMap<Joltage, PreviousState> history = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Joltage cur = queue.poll();

            if (cur.equals(target)) {
                return reconstructPath(history, start, cur, new ArrayList<>());
            }

            for (Button b : schematic.getButtons()) {
                Joltage next = cur.press(b);

                if (next.exceeds(target)) {
                    continue;
                }

                if (visited.contains(next)) {
                    continue;
                }

                visited.add(next);

                queue.add(next);

                history.put(next, new PreviousState(cur, b));
            }
        }

        return List.of();
    }

    /**
     * Reconstructs the path of the least button presses using recursion.
     * Part 2.
     *
     * @param history
     * @param start
     * @param current
     * @param path
     * @return
     */
    private List<Button> reconstructPath(HashMap<Joltage, PreviousState> history, Joltage start, Joltage current, List<Button> path) {

        if (start.equals(current)) {
            return path;
        }

        PreviousState previous = history.get(current);

        if (previous == null) {
            return List.of();
        }

        Joltage previousJoltage = previous.joltage;
        Button previousButton = previous.button;

        path.add(previousButton);

        return reconstructPath(history, start, previousJoltage, path);

    }

    /**
     * Finds fewest presses of buttons for a joltage requirement by modelling the problem as an ILP.
     *
     * @return
     */
    public long findFewestPressesForJoltageILP() {
        Loader.loadNativeLibraries();

        int[] target = joltage.getJoltages();
        int n = target.length;

        List<Button> buttons = schematic.getButtons();
        int m = buttons.size();

        int maxT = 0;
        for (int t : target) maxT = Math.max(maxT, t);

        CpModel model = new CpModel();

        // x[j] = how many times we press button j
        IntVar[] x = new IntVar[m];
        for (int j = 0; j < m; j++) {
            x[j] = model.newIntVar(0, maxT, "x" + j);
        }

        // For each joltage index i: sum_{j: i in button j} x[j] == target[i]
        for (int i = 0; i < n; i++) {
            ArrayList<IntVar> vars = new ArrayList<>();
            ArrayList<Long> coeffs = new ArrayList<>();

            for (int j = 0; j < m; j++) {
                for (int idx : buttons.get(j).getIndices()) {
                    if (idx == i) {
                        vars.add(x[j]);
                        coeffs.add(1L);
                        break;
                    }
                }
            }

            if (vars.isEmpty()) {
                // no button changes index i, hence already 0
                if (target[i] != 0) return -1;
                continue;
            }

            IntVar[] vArr = vars.toArray(new IntVar[0]);
            long[] cArr = new long[coeffs.size()];
            for (int k = 0; k < coeffs.size(); k++) cArr[k] = coeffs.get(k);

            model.addEquality(LinearExpr.weightedSum(vArr, cArr), target[i]);
        }

            // Minimize total presses
            model.minimize(LinearExpr.sum(x));

            CpSolver solver = new CpSolver();
            CpSolverStatus status = solver.solve(model);

            if (status != CpSolverStatus.OPTIMAL && status != CpSolverStatus.FEASIBLE) {
                return -1;
            }

            return Math.round(solver.objectiveValue());
    }

    public long findFewestPressesForJoltage() {
        return findLeastPressesForJoltage().size();
    }

    /**
     * Models tha path of buttons pressed needed to reach an indicator state.
     */
    private static class State {
        Indicator indicator;
        List<Button> path;

        State(Indicator indicator, List<Button> path) {
            this.indicator = indicator;
            this.path = path;
        }
    }

    /**
     * Models the previous button needed to reach a joltage state.
     */
    private static class PreviousState {
        Joltage joltage;
        Button button;

        PreviousState(Joltage joltage, Button button) {
            this.joltage = joltage;
            this.button = button;
        }
    }
}
