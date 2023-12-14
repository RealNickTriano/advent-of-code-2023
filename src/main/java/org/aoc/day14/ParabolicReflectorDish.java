package org.aoc.day14;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ParabolicReflectorDish {
    public static final String INPUT_FILE = "src/main/resources/inputs/day14.txt";
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
        List<String> lines = Arrays.stream(data.split(System.lineSeparator())).toList();

        List<List<String>> matrix = lines.stream()
                .map(item -> Arrays.stream(item.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        for (List<String> row : matrix) {
            System.out.println(row);
        }
        return calculateNorthLoad(rollStonesUp(matrix));

//
    }

    private static long partTwo(String data) {
        List<String> lines = Arrays.stream(data.split(System.lineSeparator())).toList();

        List<List<String>> matrix = lines.stream()
                .map(item -> Arrays.stream(item.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

//        for (List<String> row : matrix) {
//            System.out.println(row);
//        }

        long result = 0;
        Map<Integer, String> cycleStates = new HashMap<>();
        Map<String, Integer> statesSeen = new HashMap<>();

        for (List<String> row : matrix) {
            System.out.println(row);
        }
        System.out.println();
        for (int i = 0; i < Math.pow(10, 9); i++) {
            // Cycle
            for (int j = 0; j < 4; j++) {
                matrix = rollStonesUp(matrix);
                matrix = rotateMatrix90(matrix);
            }
//            for (List<String> row : matrix) {
//                System.out.println(row);
//            }
//            System.out.println();
//            if (i == 17) break;
            StringBuilder keyBuilder = new StringBuilder();
            for (List<String> r : matrix) {
                keyBuilder.append(String.join(",", r));
                keyBuilder.append("\n");
            }

            String key = keyBuilder.toString();
            System.out.println(key);
            if (statesSeen.containsKey(key)) {
                int cycleSeen = statesSeen.get(key);
                int period = i - cycleSeen;
                System.out.println("Cycle seen: " + cycleSeen);
                String encodedMatrix = cycleStates.get(((int) Math.pow(10, 9) - i - 1) % period + cycleSeen);
                matrix = decodeMatrix(encodedMatrix);
                break;
            }

            statesSeen.put(key, i);
            cycleStates.put(i, key);

            System.out.print("Iteration: " + i + "\r");
        }

        return calculateNorthLoad(matrix);
    }

    private static List<List<String>> decodeMatrix(String encodedMatrix) {
        return Arrays.stream(encodedMatrix.split("\n"))
                .map(item -> Arrays.stream(item.split(",")).toList())
                .toList();
    }

    public static List<List<String>> rollStonesUp(List<List<String>> matrix) {
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.get(0).size(); col++) {
                //System.out.println("Looking at: " + row + " " + col);
                if (matrix.get(row).get(col).equals("O")) {
                    int index = row - 1;
                    while (index >= 0 && matrix.get(index).get(col).equals(".")) {
                        index--;
                    }
                    // reached # or border
                    matrix.get(row).set(col, ".");
                    matrix.get(index + 1).set(col, "O");
                }
            }
        }

        return matrix;
    }

    public static List<List<String>> rotateMatrix90(List<List<String>> matrix) {
        List<List<String>> rotated = new ArrayList<>();
        for (int i = 0; i < matrix.get(0).size(); i++) {
            List<String> newRow = new ArrayList<>();
            for (int j = matrix.size() - 1; j >=0 ; j--) {
                newRow.add(matrix.get(j).get(i));
            }
            rotated.add(newRow);
        }

        return rotated;
    }

    public static long calculateNorthLoad(List<List<String>> matrix) {
        int load = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (matrix.get(i).get(j).equals("O")) {
                    load += matrix.size() - i;
                    //System.out.println("Load: " + load + " " + i + " " + j);
                }
            }
        }
        return load;
    }
}
