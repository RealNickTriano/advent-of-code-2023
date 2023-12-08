package org.aoc.day8;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HauntedWasteland {
    public static final String INPUT_FILE = "src/main/resources/inputs/day8.txt";
    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //System.out.println(partOne(data));
        System.out.println(partTwo(data));
    }

    private static int partOne(String data) {
        String[] lines = data.split(System.lineSeparator());
        String instructions = lines[0];
        String[] rest = Arrays.copyOfRange(lines, 2, lines.length);

        List<List<String>> splitting = Arrays.stream(rest)
                .map(item -> Arrays.asList(item.split(" = ")))
                .toList();

        System.out.println(splitting);
        Map<String, String[]> network = new HashMap<>();
        for (List<String> line : splitting) {
            String[] paths = line.get(1)
                    .replace('(', ' ')
                    .replace(')', ' ')
                    .strip().split(", ");
            System.out.println(Arrays.toString(paths));
            network.put(line.get(0), paths);
        }
        System.out.println(network);

        int instruction = 0;
        String currentStep = "AAA";
        int steps = 0;

        while (!currentStep.equals("ZZZ")) {
            //System.out.println(currentStep);
            steps++;
            if (instructions.charAt(instruction) == 'L')
                currentStep = network.get(currentStep)[0];
            else if (instructions.charAt(instruction) == 'R')
                currentStep = network.get(currentStep)[1];

            if (instruction == instructions.length() - 1)
                instruction = 0;
            else
                instruction++;
        }

        return steps;
    }

    private static long partTwo(String data) {
        String[] lines = data.split(System.lineSeparator());
        String instructions = lines[0];
        String[] rest = Arrays.copyOfRange(lines, 2, lines.length);

        List<List<String>> splitting = Arrays.stream(rest)
                .map(item -> Arrays.asList(item.split(" = ")))
                .toList();

        System.out.println(splitting);
        Map<String, String[]> network = new HashMap<>();
        for (List<String> line : splitting) {
            String[] paths = line.get(1)
                    .replace('(', ' ')
                    .replace(')', ' ')
                    .strip().split(", ");
            System.out.println(Arrays.toString(paths));
            network.put(line.get(0), paths);
        }
        System.out.println(network);

        List<String> endsWithA = new java.util.ArrayList<>(network.keySet().stream()
                .filter(item -> item.endsWith("A")).toList());
        System.out.println("Ends with A: " + endsWithA);

        long[] stepCounts = new long[endsWithA.size()];

        for (int i = 0; i < endsWithA.size(); i++) {
            int instruction = 0;
            String currentStep = endsWithA.get(i);
            long steps = 0;
            System.out.println(currentStep);
            while (!currentStep.endsWith("Z")) {
                //System.out.println(currentStep);
                steps++;
                if (instructions.charAt(instruction) == 'L')
                    currentStep = network.get(currentStep)[0];
                else if (instructions.charAt(instruction) == 'R')
                    currentStep = network.get(currentStep)[1];

                if (instruction == instructions.length() - 1)
                    instruction = 0;
                else
                    instruction++;
                System.out.println("\t" + currentStep);
            }
            System.out.println(currentStep);
            stepCounts[i] = steps;
        }
        System.out.println(Arrays.toString(stepCounts));
        return Arrays.stream(stepCounts)
                .reduce(1, (a, b) -> lcm(a, b));
    }

    /**
     * @param a number
     * @return least common multiple of nums
     */
    public static long lcm(long a, long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    /**
     *
     * @param a number
     * @param b number
     * @return greatest common divisor of a and b using Euclidean algorithm
     */
    public static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
