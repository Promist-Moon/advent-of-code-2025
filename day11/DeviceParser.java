package day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceParser {
    public Map<String, Device> parseDevices(List<String> strings) {
        List<Integer> colonIndices = new ArrayList<>();
        Map<String, Device> deviceMap = new HashMap<>();

        // parse all devices first
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == ':') {
                    colonIndices.add(j);
                    break;
                }
            }
            int colonIndex = colonIndices.get(i);
            String name = s.substring(0, colonIndex);
            deviceMap.put(name, new Device(name));
        }

        // redraw to add attachements
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            int colonIndex = colonIndices.get(i);
            String name = s.substring(0, colonIndex);
            Device source = deviceMap.get(name);

            String[] outputNames = s.substring(colonIndex + 2).split(" ");
            for (String n : outputNames) {
                // add if absent
                deviceMap.putIfAbsent(n, new Device(n));
                Device output = deviceMap.get(n);
                source.addOutput(output);
            }
        }

        return deviceMap;
    }
}
