package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DayEleven {
    public static void main(String[] args) throws IOException {
        String filename = "day11/input.txt";

        List<String> deviceStrings = Files.readAllLines(Paths.get(filename));
        Map<String, Device> devices = parseDevices(deviceStrings);

        List<List<Device>> paths = findAllPaths(devices, "svr", "out");

        System.out.println("Total paths svr -> out: " + paths );


         List<String> pitstops = new ArrayList<>();
         pitstops.add("dac");
         pitstops.add("fft");

        //List<List<Device>> pathsWithStops = findAllPathsWithStops(devices, "svr", "out", pitstops);
        long sum = countAllPathsWithStops(devices, "svr", "out", pitstops) + countAllPathsWithStops(devices, "svr", "out", pitstops.reversed());
        System.out.println("Total paths with stops: " + sum);

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
        }

        List<Device> devicesSorted = topoSortAllDevices(devices.values());

        Map<Device, long[]> ways = new HashMap<>();
        for (Device d : devices.values()) {
            ways.put(d, new long[stops.size() + 1]);
        }
        ways.get(source)[0] = 1;

        for (Device current : devicesSorted) {
            long[] curWays = ways.get(current);

            for (Device next : current.getOutputs()) {
                long[] nextWays = ways.get(next);

                for (int k = 0; k <= stops.size(); k++) {
                    long add = curWays[k];
                    if (add == 0) continue;

                    int nextStop = k;
                    if (k < stops.size() && next.equals(stops.get(k))) {
                        nextStop = k + 1;
                    }

                    nextWays[nextStop] += add;
                }
            }
        }

        return ways.get(destination)[stops.size()];
    }

    private static List<Device> topoSortAllDevices(Collection<Device> devices) {
        Map<Device, Integer> indegree = new HashMap<>();
        for (Device d : devices) {
            indegree.put(d, 0);
        }

        for (Device u : devices) {
            for (Device v : u.getOutputs()) {
                indegree.put(v, indegree.get(v) + 1);
            }
        }

        // queue of nodes with indegree 0
        Queue<Device> queue = new ArrayDeque<>();
        for (Map.Entry<Device, Integer> e : indegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        List<Device> topo = new ArrayList<>();

        while (!queue.isEmpty()) {
            Device u = queue.poll();
            topo.add(u);

            for (Device v : u.getOutputs()) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) {
                    queue.add(v);
                }
            }
        }

        if (topo.size() != devices.size()) {
            throw new IllegalStateException("Graph is not a DAG (cycle detected)");
        }

        return topo;
    }

    private static class State {
        Device current;
        List<Device> path;

        public State(Device current, List<Device> path) {
            this.current = current;
            this.path = path;
        }
    }
}
