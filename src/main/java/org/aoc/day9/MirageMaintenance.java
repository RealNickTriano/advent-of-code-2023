package org.aoc.day9;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MirageMaintenance {
    public static final String INPUT_FILE = "src/main/resources/inputs/day9.txt";
    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(partOne(data));
        System.out.println(partTwo(data));
    }

    private static long partOne(String data) {
        List<List<Long>> histories = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(" "))
                        .map(Long::parseLong).toList())
                .toList();

        System.out.println(histories);

        long result = 0;

        for (List<Long> h : histories) {
            List<List<Long>> allSteps = new ArrayList<>();
            allSteps.add(h);
            while (!h.stream().allMatch(item -> item == 0)) {
                List<Long> steps = new ArrayList<>();
                for (int i = 0; i < h.size() - 1; i++) {
                    long difference = h.get(i + 1) - h.get(i);
                    steps.add(difference);
                }
                allSteps.add(steps);
                h = steps;
            }
            System.out.println(allSteps);

            long below = -1;
            for (int j = allSteps.size() - 1; j >= 0; j--) {
                if (j == allSteps.size() - 1) {
                    System.out.println("all zeroes");
                    below = 0;
                    continue;
                }
                System.out.println("Adding " + allSteps.get(j).getLast() + " " + below);
                below = allSteps.get(j).getLast() + below;
            }
            System.out.println(below);
            result += below;
        }

        return result;
    }

    private static long partTwo(String data) {
        List<List<Long>> histories = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(" "))
                        .map(Long::parseLong).toList())
                .toList();

        System.out.println(histories);

        long result = 0;

        for (List<Long> h : histories) {
            List<List<Long>> allSteps = new ArrayList<>();
            allSteps.add(h);
            while (!h.stream().allMatch(item -> item == 0)) {
                List<Long> steps = new ArrayList<>();
                for (int i = 0; i < h.size() - 1; i++) {
                    long difference = h.get(i + 1) - h.get(i);
                    steps.add(difference);
                }
                allSteps.add(steps);
                h = steps;
            }
            System.out.println(allSteps);

            long below = -1;
            for (int j = allSteps.size() - 1; j >= 0; j--) {
                if (j == allSteps.size() - 1) {
                    System.out.println("all zeroes");
                    below = 0;
                    continue;
                }
                // Simple change to get first and subtract
                System.out.println("Adding " + allSteps.get(j).getFirst() + " " + below);
                below = allSteps.get(j).getFirst() - below;
            }
            System.out.println(below);
            result += below;
        }

        return result;
    }
}
