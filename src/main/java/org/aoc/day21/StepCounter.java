package org.aoc.day21;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class StepCounter {
    public static final String INPUT_FILE = "src/main/resources/inputs/day21.txt";

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

        List<List<String>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(""))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        List<Integer> startPos = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("S")) {
                    startPos = Arrays.asList(i, j);
                }
            }
        }

        int steps = 0;
        int maxSteps = 64;
        int popsToStep = 1;
        int pops = 0;
        int numAdded = 0;
        List<List<Integer>> directions = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, -1),
                Arrays.asList(1, 0), Arrays.asList(-1, 0));
        List<List<Integer>> queue = new ArrayList<>();
        queue.add(startPos);
        while (!queue.isEmpty() && steps < maxSteps) {
            List<Integer> currentPos = queue.removeFirst();
            pops++;
            for (List<Integer> dir : directions) {
                List<Integer> newPos = Arrays.asList(currentPos.get(0) + dir.get(0), currentPos.get(1) + dir.get(1));
                // Check out of bounds or rock
                if (matrix.get(newPos.get(0)).get(newPos.get(1)).equals("#") ||
                    newPos.get(0) < 0 || newPos.get(0) >= matrix.size()
                    || newPos.get(1) < 0 || newPos.get(1) >= matrix.get(0).size()) {
                    // Rock so cant go
                    continue;
                } else {
                    if (!queue.contains(newPos)) {
                        queue.add(newPos);
                        numAdded++;
                    }
                }
            }

            if (pops == popsToStep) {
                steps++;
                pops = 0;
                popsToStep = numAdded;
                numAdded = 0;
            }
        }
//        System.out.println(queue);
//        System.out.println(queue.size());
//        for (List<Integer> pos : queue) {
//            matrix.get(pos.get(0)).set(pos.get(1), "O");
//        }
//        for (List<String> r:  matrix) {
//            System.out.println(r);
//        }


        return queue.size();
    }

    private static int partTwo(String data) {

        List<List<String>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(""))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        List<Integer> startPos = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("S")) {
                    startPos = Arrays.asList(i, j);
                }
            }
        }

        Map<List<Integer>, Integer> positionCounts = new HashMap<>();
        // IDEA: when reach a new position that mods to an already seen location
        // just add a counter to show how many instances of that lcoation is there
        // then whatever change in that location happens happens x amount of times
        // but we only do it once and update the location
        // lcoation is identified as a larger grid of maps

        int steps = 0;
        int maxSteps = 10;
        int popsToStep = 1;
        int pops = 0;
        int numAdded = 0;
        int numGardens = 0;
        List<List<Integer>> directions = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, -1),
                Arrays.asList(1, 0), Arrays.asList(-1, 0));
        List<List<Integer>> queue = new ArrayList<>();
        queue.add(startPos);
        while (steps < maxSteps) {
            System.out.println(queue);
            List<Integer> currentPos = queue.removeFirst();
            List<Integer> modPos = getModPos(currentPos, matrix.size(), matrix.get(0).size());
            if (positionCounts.containsKey(currentPos)) {
                // know what directions to add on queue?
                // queue size stil increases, so runtime is still rough?
                numGardens += positionCounts.get(modPos);
                pops += positionCounts.get(modPos);
                System.out.println("Step: " + steps + " Seen: " + modPos + " Really at: " + currentPos + " Can reach: " + numGardens + " gardens");

            } else {
                pops++;
                for (List<Integer> dir : directions) {
                    List<Integer> newPos = Arrays.asList(currentPos.get(0) + dir.get(0), currentPos.get(1) + dir.get(1));
                    //System.out.println(newPos);

                    modPos = getModPos(newPos, matrix.size(), matrix.get(0).size());
                    //System.out.println(modRow + " " + modCol);
                    // Check out of bounds or rock
                    if (matrix.get(modPos.get(0)).get(modPos.get(1)).equals("#")) {
                        //System.out.println("Row: " + modRow + " Col: " + modCol + " has a #");
                        // Rock so cant go
                        continue;
                    } else {
                        if (!queue.contains(newPos)) {
                            queue.add(newPos);
                            numAdded++;
                        }
                    }
                }
            }

            if (pops == popsToStep) {
                steps++;
                pops = 0;
                positionCounts.put(modPos, numAdded);
                popsToStep = numAdded;
                numAdded = 0;
            }
        }
        //System.out.println(queue);
        System.out.println(queue.size());
        System.out.println(numGardens);
//        for (List<Integer> pos : queue) {
//            matrix.get(pos.get(0)).set(pos.get(1), "O");
//        }
//        for (List<String> r:  matrix) {
//            System.out.println(r);
//        }


        return queue.size();
    }

    public static List<Integer> getModPos(List<Integer> position, int rowSize, int colSize) {
        int modRow = 0;
        int modCol = 0;
        if (position.get(0) >= 0)
            modRow = position.get(0) % rowSize;
        else if (position.get(0) < 0) {
            modRow = rowSize - 1 - (Math.abs(position.get(0) + 1) % rowSize);
        }

        if (position.get(1) >= 0)
            modCol = position.get(1) % colSize;
        else if (position.get(1) < 0) {
            modCol = colSize - 1 - (Math.abs(position.get(1) + 1) % colSize);
        }

        return Arrays.asList(modRow, modCol);
    }
}
