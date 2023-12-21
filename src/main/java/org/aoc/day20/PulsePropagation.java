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
        Map<String, Boolean> flipFlops = new HashMap<>();
        Map<String, Map<String, String>> conjunctions = new HashMap<>();
        Map<String, String> toProcess = new HashMap<>();

        for (List<String> m : items) {
            modules.put(m.get(0), Arrays.stream(m.get(1).split(", ")).toList());
            if (m.get(0).charAt(0) == '%') {
                flipFlops.put(m.get(0).substring(1), false);
            } else if (m.get(0).charAt(0) == '&') {
                Map<String, String> map = new HashMap<>();
                for (Map.Entry<String, List<String>> entry : modules.entrySet()) {
                    if (entry.getValue().contains(m.get(0).substring(1))) {
                        map.put(entry.getKey(), "low");
                    }
                }
                conjunctions.put(m.get(0).substring(1), map);
            }
        }
        System.out.println(modules);
        System.out.println(flipFlops);
        System.out.println(conjunctions);

        int highPulses = 0;
        int lowPulses = 0;
        int buttonPressed = 0;

        // [[name, pulseSending]]
        List<List<String>> queue = new ArrayList<>();
        queue.add(Arrays.asList("broadcaster", "low"));
        while (!queue.isEmpty()) {
            List<String> list = queue.removeFirst();
            String name = list.get(0);
            String pulseSending = list.get(1);
            System.out.println(name);
            List<String> receivers = modules.get(name);
            for (String r : receivers) {
                if (pulseSending.equals("low")) lowPulses++;
                if (pulseSending.equals("high")) highPulses++;
                if (flipFlops.containsKey(r)) {
                    if (pulseSending.equals("low")) {
                        if (!flipFlops.get(r)) {
                            queue.add(Arrays.asList("%" + r, "high"));
                        } else {
                            queue.add(Arrays.asList("%" + r, "low"));
                        }
                    } else {
                        // do nothing when get high pulse
                    }
                    // Flip switch
                    flipFlops.put(r, !flipFlops.get(r));
                } else if (conjunctions.containsKey(r)) {
                    conjunctions.get(r).put(name, pulseSending);
                    if (conjunctions.get(r).values().stream().allMatch(value -> value.equals("high"))) {
                        queue.add(Arrays.asList("&" + r, "low"));
                    } else {
                        queue.add(Arrays.asList("&" + r, "high"));
                    }
                }
            }
        }
        System.out.println(flipFlops);
        System.out.println(conjunctions);
        System.out.println(lowPulses + " " + highPulses);
        return lowPulses * highPulses;


//        // Button press
//        do {
//            buttonPressed++;
//            lowPulses += 1 ;
//            toProcess.put("broadcaster", "low");
//            do {
//
//                Map<String, String> newProcesses = new HashMap<>();
//                for (String process : toProcess.keySet()) {
//                    String pulse = toProcess.get(process);
//
//                    List<String> destinations = modules.get(process);
//                    System.out.println();
//                    System.out.println("Low: " + lowPulses + " High: " + highPulses);
//                    System.out.println("Current Flops: " + flipFlops);
//                    System.out.println("Current Cons: " + conjunctions);
//                    System.out.println("From: " + process);
//                    for (String d : destinations) {
//                        if (pulse.equals("low")) lowPulses++;
//                        if (pulse.equals("high")) highPulses++;
//                        System.out.println("\tSent " + pulse + " pulse to " + d);
//                        if (flipFlops.containsKey(d)) {
//                            if (pulse.equals("low")) {
//                                boolean state = flipFlops.get(d);
//                                if (!state) {
//                                    flipFlops.put(d, true);
//                                    newProcesses.put("%" + d, "high");
//                                    //highPulses++;
//                                } else {
//                                    flipFlops.put(d, false);
//                                    newProcesses.put("%" + d, "low");
//                                    //lowPulses++;
//                                }
//                            } else if (pulse.equals("high")) {
//                                // do nothing
//                            }
//                        } else if (conjunctions.containsKey(d)) {
//                            conjunctions.get(d).put(process, pulse);
//                            if (conjunctions.get(d).values().stream().allMatch(item -> item.equals("high"))) {
//                                newProcesses.put("&" + d, "low");
//                                //lowPulses++;
//                            } else {
//                                newProcesses.put("&" + d, "high");
//                                //highPulses++;
//                            }
//                        }
//                    }
//                    //conjunctions.replaceAll((k,v) -> v = Arrays.asList("low"));
//
//                }
//                //System.out.println(newProcesses);
//                toProcess = newProcesses;
//            } while (!toProcess.isEmpty());
//            System.out.println("Button: " + buttonPressed + " " + flipFlops + " " + conjunctions);
//        } while (flipFlops.values().stream().anyMatch(item -> item) && buttonPressed < 1000);
//        System.out.println("End Flops: " + flipFlops);
//        System.out.println("End Cons: " + conjunctions);
//        System.out.println("Low Pulses: " + lowPulses);
//        System.out.println("High Pulses: " + highPulses);
//        System.out.println("Button Pressed: " + buttonPressed);
//        lowPulses *= (1000 / buttonPressed);
//        System.out.println(lowPulses);
//        highPulses *= (1000 / buttonPressed);
//        System.out.println(highPulses);
//
//        return (long) lowPulses * highPulses;

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
