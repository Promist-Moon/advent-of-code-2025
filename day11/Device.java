package day11;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a vertex in a directed graph
 */
public class Device {
    private String name;
    private Set<Device> outputs;

    public Device(String name) {
        this.name = name;
        this.outputs = new HashSet<>();
    }

    public Device(String name, Set<Device> outputs) {
        this.name = name;
        this.outputs = outputs;
    }

    public boolean hasOutput(Device other) {
        if (other == null) {
            return false;
        }
        return outputs.contains(other);
    }

    public void addOutput(Device other) {
        if (other != null) {
            outputs.add(other);
        }
    }

    public Set<Device> getOutputs() {
        return this.outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        Device other = (Device) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
 