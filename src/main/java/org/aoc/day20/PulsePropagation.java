package org.aoc.day20;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;

public class PulsePropagation {
    public static final String INPUT_FILE = "src/main/resources/inputs/day20.txt";

    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(partOne(data));
        //System.out.println(partTwo(data));
    }

    private static long partOne(String data) {
        long result = 0;

        List<String> lines = Arrays.stream(data.split(System.lineSeparator())).toList();

        List<List<String>> items = lines.stream()
                .map(item -> Arrays.stream(item.split(" -> "))
                        .toList())
                .toList();
        System.out.println(items);

        Map<String, List<String>> modules = new HashMap<>();
        List<FlipFlop> flipFlopsList = new ArrayList<>();
        List<Conjunction> conjunctionsList = new ArrayList<>();
        Map<String, Boolean> flipFlops = new HashMap<>();
        Map<String, Map<String, String>> conjunctions = new HashMap<>();
        Map<String, String> toProcess = new HashMap<>();

        for (List<String> m : items) {
            modules.put(m.get(0), Arrays.stream(m.get(1).split(", ")).toList());
            if (m.get(0).charAt(0) == '%') {
                FlipFlop flipFlop = new FlipFlop(m.get(0).substring(1), "%",
                        false, null, new ArrayList<>());
                flipFlopsList.add(flipFlop);
                flipFlops.put(m.get(0).substring(1), false);
            } else if (m.get(0).charAt(0) == '&') {
                Conjunction conjunction = new Conjunction(m.get(0).substring(1), "&",
                        new ArrayList<>(), null, new ArrayList<>());
                conjunctionsList.add(conjunction);
                Map<String, String> map = new HashMap<>();
                for (Map.Entry<String, List<String>> entry : modules.entrySet()) {
                    if (entry.getValue().contains(m.get(0).substring(1))) {
                        map.put(entry.getKey(), "low");
                    }
                }
                //conjunction.memory.put();
                conjunctions.put(m.get(0).substring(1), map);
            }
        }
        System.out.println(modules);
        System.out.println(flipFlops);
        System.out.println(conjunctions);
        System.out.println(flipFlopsList);
        System.out.println(conjunctionsList);


        return 0;
    }

    private static int sendPulses(Map<String, List<String>> modules,
                                   Map<String, Boolean> flipFlops,
                                   Map<String, String> conjunctions,
                                   String inputModule,
                                  String pulseStrength) {
        int highPulses = 0;
        int lowPulses = 0;

        if (!inputModule.equals("broadcaster") && flipFlops.values().stream().noneMatch(item -> item.booleanValue())) {
            System.out.println("Cycle");
            return 0;
        }
        List<String> destinations = modules.get(inputModule);
        System.out.println("Current: " + flipFlops + " " + inputModule + " " + pulseStrength);
        for (String d : destinations) {
            System.out.println("Sending " + pulseStrength + " pulse to " + d);
            if (flipFlops.containsKey(d)) {
                if (pulseStrength.equals("low")) {
                    boolean state = flipFlops.get(d);
                    if (!state) {
                        flipFlops.put(d, true);
                        pulseStrength = "high";
                        highPulses++;
                    } else {
                        flipFlops.put(d, false);
                        pulseStrength = "low";
                        lowPulses++;

                    }
                    inputModule = "%" + d;
                    //sendPulses(modules, flipFlops, conjunctions, inputModule, pulseStrength);

                } else if (pulseStrength.equals("high")) {
                    //return 0;
                }
            } else if (conjunctions.containsKey(d)) {
                conjunctions.put(d, pulseStrength);
                if (conjunctions.get(d).equals("high")) {
                    lowPulses++;
                    pulseStrength = "low";
                } else {
                    highPulses++;
                    pulseStrength = "high";
                }
                inputModule = "&" + d;
                //sendPulses(modules, flipFlops, conjunctions, inputModule, pulseStrength);
            }
            sendPulses(modules, flipFlops, conjunctions, inputModule, pulseStrength);
        }

        //System.out.println(flipFlops);
        System.out.println("Low Pulses: " + lowPulses);
        System.out.println("High Pulses: " + highPulses);

        return lowPulses * highPulses;
    }
}
