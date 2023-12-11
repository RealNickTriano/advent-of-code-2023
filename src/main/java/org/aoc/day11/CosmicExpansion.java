package org.aoc.day11;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CosmicExpansion {
    public static final String INPUT_FILE = "src/main/resources/inputs/day11.txt";

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
        List<List<String>> matrix =  Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.asList(item.split("")))
                .collect(Collectors.toList());

        for (List<String> s : matrix) {
            System.out.println(s);
        }
        List<List<String>> expandedMatrix = expandMatrix(matrix);
        List<List<Integer>> galaxies = findAllGalaxies(expandedMatrix);
        List<List<List<Integer>>> pairs = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                pairs.add(Arrays.asList(galaxies.get(i), galaxies.get(j)));
            }
        }
        System.out.println(expandedMatrix);
        System.out.println(galaxies);
        System.out.println(pairs);

        int result = 0;
        // Run Dijkstra's on all pairs as [start, goal] to find shortest paths
        for (List<List<Integer>> p : pairs) {
            List<Integer> start = p.get(0);
            List<Integer> goal = p.get(1);

            int distance = Math.abs(start.get(0) - goal.get(0)) + Math.abs(start.get(1) - goal.get(1));
            System.out.println(distance);
            result += distance;
        }
        return result;
    }

    private static long partTwo(String data) {
        List<List<String>> matrix =  Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.asList(item.split("")))
                .collect(Collectors.toList());

        for (List<String> s : matrix) {
            System.out.println(s);
        }

        // Find empty rows and cols
        int[] rows = new int[matrix.size()];
        int[] cols = new int[matrix.get(0).size()];
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("#")) {
                    rows[i]++;
                    cols[j]++;
                }
            }
        }
        System.out.println("Rows: " + Arrays.toString(rows) + "\n" + "Cols: " +  Arrays.toString(cols));

        List<Integer> emptyRows = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] == 0)
                emptyRows.add(i);
        }
        List<Integer> emptyCols = new ArrayList<>();
        for (int i = 0; i < cols.length; i++) {
            if (cols[i] == 0)
                emptyCols.add(i);
        }

        List<List<Integer>> galaxies = findAllGalaxies(matrix);
        List<List<List<Integer>>> pairs = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                pairs.add(Arrays.asList(galaxies.get(i), galaxies.get(j)));
            }
        }

        System.out.println(galaxies);
        System.out.println(pairs);

        long result = 0;
        // Run Dijkstra's on all pairs as [start, goal] to find shortest paths
        for (List<List<Integer>> p : pairs) {
            System.out.println("Pair: " + p);
            int startX = p.get(0).get(1);
            int startY = p.get(0).get(0);

            int goalX = p.get(1).get(1);
            int goalY = p.get(1).get(0);

            int millions = 0;

            System.out.println("Cols: " + emptyCols);
            for (int c : emptyCols) {
                System.out.println(Math.min(startX, goalX) + " to " + Math.max(startX, goalX));
                if (Math.min(startX, goalX) < c && c < Math.max(startX, goalX)) {
                    millions++;
                }
            }

            System.out.println("Empty Cols passed through: " + millions);

            System.out.println("Rows: " + emptyRows);
            for (int r : emptyRows) {
                System.out.println(Math.min(startY, goalY) + " to " + Math.max(startY, goalY));
                if (Math.min(startY, goalY) < r && r < Math.max(startY, goalY)) {
                    millions++;
                }
            }

            System.out.println("Empty Rows passed through: " + millions);

            long distance = (Math.abs(startY - goalY) + Math.abs(startX - goalX)) + (millions * 999999);
            System.out.println("Distance: " + distance);
            //System.out.println(distance);
            result += distance;
        }
        return result;
    }

    private static List<List<String>> expandMatrix(List<List<String>> matrix) {
        int[] rows = new int[matrix.size()];
        int[] cols = new int[matrix.get(0).size()];
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("#")) {
                    rows[i]++;
                    cols[j]++;
                }
            }
        }
        System.out.println(Arrays.toString(rows) + "\n" + Arrays.toString(cols));
        int matrixPos = 0;
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] == 0) {
                List<String> newRow = Arrays.asList(new String[rows.length]);
                Collections.fill(newRow, ".");
                matrix.add(matrixPos, newRow);
                matrixPos++;
            }
            matrixPos++;

        }

        for (int i = 0; i < matrix.size(); i++) {
            int matrixRowPos = 0;
            for (int j = 0; j < cols.length; j++) {
                if (cols[j] == 0) {
                    List<String> newRow = new ArrayList<>(matrix.get(i));
                    newRow.add(matrixRowPos, ".");
                    matrix.set(i, newRow);
                    matrixRowPos++;
                }
                matrixRowPos++;
            }
        }
        return matrix;
    }

    private static List<List<Integer>> findAllGalaxies(List<List<String>> matrix) {
        List<List<Integer>> positions = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j).equals("#"))
                    positions.add(Arrays.asList(i, j));
            }
        }

        return positions;
    }
}
