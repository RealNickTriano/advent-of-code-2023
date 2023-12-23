package org.aoc.day20;

import java.util.List;

public class FlipFlop extends Module{
    public boolean state;
    public String pulseReceiving;
    public List<Module> listeners;

    public FlipFlop(String name, String prefix,
                    boolean state, String pulseReceiving,
                    List<Module> listeners) {

        this.name = name;
        this.prefix = prefix;
        this.state = state;
        this.pulseReceiving = pulseReceiving;
        this.listeners = listeners;
    }

    public String toString() {
        return String.format("%s%s %s -%s-> %s", prefix, name, state, pulseReceiving, listeners);
    }
}
