package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DayEleven {
    public static void main(String[] args) throws IOException {
        String filename = "day11/input.txt";

        List<String> deviceStrings = Files.readAllLines(Paths.get(filename));
        Map<String, Device> devices = parseDevices(deviceStrings);

        //List<List<Device>> paths = findAllPaths(devices, "svr", "out");

        //System.out.println("Total paths svr -> out: " + paths );


         List<String> pitstops = new ArrayList<>();
         pitstops.add("dac");
         pitstops.add("fft");

        //List<List<Device>> pathsWithStops = findAllPathsWithStops(devices, "svr", "out", pitstops);
        System.out.println("Total paths with stops: " + countAllPathsWithStops(devices, "svr", "out", pitstops) + countAllPathsWithStops(devices, "svr", "out", pitstops.reversed()));

    }

    private static Map<String, Device> parseDevices(List<String> strings) {
        DeviceParser parser = new DeviceParser();
        return parser.parseDevices(strings);
    }

    private static List<List<Device>> findAllPaths(Map<String, Device> devices, String srcStr, String destStr) {
        Device source = devices.get(srcStr);
        Device destination = devices.get(destStr);

        Queue<State> queue = new ArrayDeque<>();

        List<List<Device>> paths = new ArrayList<>();

        queue.add(new State(source, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.current.equals(destination)) {
                List<Device> completedPath = new ArrayList<>(current.path);
                completedPath.add(current.current);
                paths.add(completedPath);
                continue;
            }

            for (Device next : current.current.getOutputs()) {
                if (current.path.contains(next)) {
                    continue;
                }
                List<Device> newPath = new ArrayList<>(current.path);
                newPath.add(current.current);

                State newState = new State(next, newPath);
                queue.add(newState);
            }
        }

        return paths;
    }

    private static long countAllPathsWithStops(Map<String, Device> devices, String srcStr, String destStr, List<String> stopStrings) {
        Device source = devices.get(srcStr);
        Device destination = devices.get(destStr);

        List<Device> stops = new ArrayList<>();
        for (String s : stopStrings) {
            stops.add(devices.get(s));
            System.out.println("device = " + devices.get(s));
        }

        // state 0 if at svr
        // state 1 if passed dac/fft
        // state 2 if passed fft/dac
        // state 3 if passed out

        Queue<NewState> queue = new ArrayDeque<>();
        queue.add(new NewState(source, 0));

        long count = 0;

        while (!queue.isEmpty()) {
            NewState current = queue.poll();

            if (current.current.equals(destination)) {
                if (current.stop == stops.size()) {
                    count++;
                }
                continue;
            }

            for (Device next : current.current.getOutputs()) {
                int nextStop = current.stop;

                if (nextStop < stops.size() && next.equals(stops.get(nextStop))) {
                    nextStop++;
                }

                queue.add(new NewState(next, nextStop));
            }
        }

        return count;
    }

    private static class State {
        Device current;
        List<Device> path;

        public State(Device current, List<Device> path) {
            this.current = current;
            this.path = path;
        }
    }

    private static class NewState {
        Device current;
        int stop;

        public NewState(Device current, int stop) {
            this.current = current;
            this.stop = stop;
        }
    }
}
