package org.aoc.day16;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class TheFloorWillBeLava {
    public static final String INPUT_FILE = "src/main/resources/inputs/day16.txt";

    public static List<List<Integer>> energizedLocations = new ArrayList<>();
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

    private static long partOne(String data) {
        long result = 0;
        List<List<String>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));



        for (List<String> row : matrix) {
            System.out.println(row);
        }
        // beamDirection: Right = 1, Left = -1, Up = 2, Up = -2
        recurse(matrix, Arrays.asList(0, 0, 1));
        //result = energizedLocations.size();

        for (List<Integer> pos : energizedLocations) {
            matrix.get(pos.get(0)).set(pos.get(1), "#");
        }

        for (List<String> row : matrix) {
            System.out.println(row);
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("#"))
                    result += 1;
            }
        }
        return result;
    }

    private static long partTwo(String data) {
        long max = 0;
        List<List<String>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));



//        for (List<String> row : matrix) {
//            System.out.println(row);
//        }
        // beamDirection: Right = 1, Left = -1, Up = 2, Up = -2
        for (int i = 0; i < matrix.size(); i++) {
            List<List<String>> newMatrix = new ArrayList<>();
            for (List<String> r: matrix) {
                newMatrix.add(new ArrayList<>(r));
            }
            recurse(newMatrix, Arrays.asList(i, 0, 1));

            for (List<Integer> pos : energizedLocations) {
                newMatrix.get(pos.get(0)).set(pos.get(1), "#");
            }

            int result = 0;
            for (int k = 0; k < newMatrix.size(); k++) {
                for (int j = 0; j < newMatrix.get(k).size(); j++) {
                    if (newMatrix.get(k).get(j).equals("#"))
                        result += 1;
                }
            }
            max = Math.max(result, max);
//            System.out.println("Max of " + i + ": " + max);
//            for (List<String> row : newMatrix) {
//                System.out.println(row);
//            }
//            System.out.println();
//            for (List<String> row1 : matrix) {
//                System.out.println(row1);
//            }
//            System.out.println();

            energizedLocations = new ArrayList<>();
        }
        System.out.println("Max: " + max);

        for (int i = 0; i < matrix.size(); i++) {
            List<List<String>> newMatrix = new ArrayList<>();
            for (List<String> r: matrix) {
                newMatrix.add(new ArrayList<>(r));
            }

            recurse(matrix, Arrays.asList(i, matrix.get(0).size() - 1, -1));

            for (List<Integer> pos : energizedLocations) {
                newMatrix.get(pos.get(0)).set(pos.get(1), "#");
            }

            int result = 0;
            for (int k = 0; k < matrix.size(); k++) {
                for (int j = 0; j < matrix.get(k).size(); j++) {
                    if (newMatrix.get(k).get(j).equals("#"))
                        result += 1;
                }
            }
            max = Math.max(result, max);

            energizedLocations = new ArrayList<>();
        }
        System.out.println("Max: " + max);
        for (int i = 0; i < matrix.get(0).size(); i++) {
            List<List<String>> newMatrix = new ArrayList<>();
            for (List<String> r: matrix) {
                newMatrix.add(new ArrayList<>(r));
            }
            recurse(matrix, Arrays.asList(0, i, -2));

            for (List<Integer> pos : energizedLocations) {
                newMatrix.get(pos.get(0)).set(pos.get(1), "#");
            }

            int result = 0;
            for (int k = 0; k < matrix.size(); k++) {
                for (int j = 0; j < matrix.get(k).size(); j++) {
                    if (newMatrix.get(k).get(j).equals("#"))
                        result += 1;
                }
            }
            max = Math.max(result, max);

            energizedLocations = new ArrayList<>();
        }
        System.out.println("Max: " + max);
        for (int i = 0; i < matrix.get(0).size(); i++) {
            List<List<String>> newMatrix = new ArrayList<>();
            for (List<String> r: matrix) {
                newMatrix.add(new ArrayList<>(r));
            }
            recurse(matrix, Arrays.asList(matrix.size() - 1, i, 2));

            for (List<Integer> pos : energizedLocations) {
                newMatrix.get(pos.get(0)).set(pos.get(1), "#");
            }

            int result = 0;
            for (int k = 0; k < matrix.size(); k++) {
                for (int j = 0; j < matrix.get(k).size(); j++) {
                    if (newMatrix.get(k).get(j).equals("#"))
                        result += 1;
                }
            }
            max = Math.max(result, max);

            energizedLocations = new ArrayList<>();
        }
        System.out.println("Max: " + max);
        return max;
    }


    // beamDirection: Right = 1, Left = -1, Up = 2, Up = -2
    private static void recurse(List<List<String>> matrix, List<Integer> beamPos) {
        // Out of bounds
        if (beamPos.get(0) < 0 || beamPos.get(0) >= matrix.size()
            || beamPos.get(1) < 0 || beamPos.get(1) >= matrix.get(0).size()) {
            return;
        }
        // hit already energized location dont add to energized
        if (energizedLocations.contains(beamPos)) {
            return;
        } else {
            energizedLocations.add(beamPos);
            //System.out.println(energizedLocations);
        }
        int row = beamPos.get(0);
        int col = beamPos.get(1);

        switch (matrix.get(beamPos.get(0)).get(beamPos.get(1))) {
            case "/":
                switch (beamPos.get(2)) {
                    case 1:
                        recurse(matrix, Arrays.asList(row - 1, col, 2));
                        break;
                    case -1:
                        recurse(matrix, Arrays.asList(row + 1, col, -2));
                        break;
                    case 2:
                        recurse(matrix, Arrays.asList(row, col + 1, 1));
                        break;
                    case -2:
                        recurse(matrix, Arrays.asList(row, col - 1, -1));
                        break;
                }
                break;
            case "\\":
                switch (beamPos.get(2)) {
                    case 1:
                        recurse(matrix, Arrays.asList(row + 1, col, -2));
                        break;
                    case -1:
                        recurse(matrix, Arrays.asList(row - 1, col, 2));
                        break;
                    case 2:
                        recurse(matrix, Arrays.asList(row, col - 1, -1));
                        break;
                    case -2:
                        recurse(matrix, Arrays.asList(row, col + 1, 1));
                        break;
                }
                break;
            case "|":
                switch (beamPos.get(2)) {
                    case 1, -1:
                        recurse(matrix, Arrays.asList(row - 1, col, 2));
                        recurse(matrix, Arrays.asList(row + 1, col, -2));
                        break;
                    case 2:
                        recurse(matrix, Arrays.asList(row - 1, col, 2));
                        break;
                    case -2:
                        recurse(matrix, Arrays.asList(row + 1, col, -2));
                        break;
                }
                break;
            case "-":
                switch (beamPos.get(2)) {
                    case 1:
                        recurse(matrix, Arrays.asList(row, col + 1, 1));
                        break;
                    case -1:
                        recurse(matrix, Arrays.asList(row, col - 1, -1));
                        break;
                    case 2, -2:
                        recurse(matrix, Arrays.asList(row, col - 1, -1));
                        recurse(matrix, Arrays.asList(row, col + 1, 1));
                        break;
                }
                break;
            default:
                switch (beamPos.get(2)) {
                    case 1:
                        recurse(matrix, Arrays.asList(row, col + 1, 1));
                        break;
                    case -1:
                        recurse(matrix, Arrays.asList(row, col - 1, -1));
                        break;
                    case 2:
                        recurse(matrix, Arrays.asList(row - 1, col, 2));
                        break;
                    case -2:
                        recurse(matrix, Arrays.asList(row + 1, col, -2));
                        break;
                }
                break;
        }
    }
}
