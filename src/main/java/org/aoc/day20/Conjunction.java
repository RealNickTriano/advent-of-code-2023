package org.aoc.day20;

import java.util.List;

public class Conjunction extends Module{
    public List<FlipFlop> memory;
    public String pulseReceiving;
    public List<Module> listeners;

    public Conjunction(String name, String prefix,
                       List<FlipFlop> memory, String pulseReceiving,
                    List<Module> listeners) {

        this.name = name;
        this.prefix = prefix;
        this.pulseReceiving = pulseReceiving;
        this.listeners = listeners;
    }

    public String toString() {
        return String.format("%s%s %s -%s-> %s", prefix, name, memory, pulseReceiving, listeners);
    }
}
