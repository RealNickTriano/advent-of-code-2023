package org.aoc.day17;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ClumsyCrucible {
    public static final String INPUT_FILE = "src/main/resources/inputs/day17.txt";
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

    private static int partOne(String data) {
        List<List<Integer>> matrix = Arrays.stream(data.split(System.lineSeparator()))
                .map(item -> Arrays.stream(item.split(""))
                        .map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        int result = 0;

        for (List<Integer> r : matrix) {
            System.out.println(r);
        }
        result = shortestWeightedPath(matrix);

        return result;
    }

    private static int shortestWeightedPath(List<List<Integer>> matrix) {
        int total = 0;
        List<List<Integer>> nextLocations = Arrays.asList(
                Arrays.asList(0, 1), Arrays.asList(0, -1),
                Arrays.asList(1, 0), Arrays.asList(-1, 0));


        PriorityQueue<Node> next = new PriorityQueue<>();
        List<Node> seen = new ArrayList<>();
        Node start = new Node(0, 0, 0, 0, 0);
        next.add(start);

        while (!next.isEmpty()) {
            Node current = next.remove();
            if (seen.contains(current)) {
                continue;
            }
            System.out.println("At: " + current);
            seen.add(current);
            if (current.row == matrix.size() - 1 && current.col == matrix.get(0).size() - 1) {
                // found goal
                return current.value;
            }
            for (List<Integer> loc : nextLocations) {
                int newRow = current.row + loc.get(0);
                int newCol = current.col + loc.get(1);
                int direction = getDirection(loc);
                System.out.println("Direction: " + direction);
                try {
                    int newVal = current.value + matrix.get(newRow).get(newCol);
                    Node newNode = new Node(newRow, newCol, newVal, direction, 0);
                    if (current.direction == -direction) continue;
                    if (current.directionCount == 3
                            && (current.direction == direction)) {
                        // cant go here? so put on seen?
                        System.out.println("Direction count maxed");
                        System.out.println("\t" + current);
                        System.out.println("\t" + newNode);
                        //seen.add(newNode);
                        continue;
                    }


                    if (current.direction == direction) {
                        newNode.directionCount = current.directionCount + 1;
                    } else {
                        newNode.directionCount = 1;
                    }

                    newNode.direction = direction;
                    next.add(newNode);
                    System.out.println("Added: " + newNode + " From: " + current);

                } catch (IndexOutOfBoundsException ignored) {
                    System.out.println("Out of bounds, did not add");
                }

            }

        }
        List<Node> goals =  seen.stream().filter(item -> item.row == 12 && item.col == 12).toList();
        System.out.println(goals);
        System.out.println("Something went wrong");

        return total;
    }

    private static int getDirection(List<Integer> direction) {

        if (direction.get(0) == 0 && direction.get(1) == 1) return 1;
        if (direction.get(0) == 0 && direction.get(1) == -1) return -1;
        if (direction.get(0) == -1 && direction.get(1) == 0) return 2;
        if (direction.get(0) == 1 && direction.get(1) == 0) return -2;

        return 0;
    }

    static class Node implements Comparable<Node>{
        public int row;
        public int col;
        public int value;
        public int directionCount;
        public int direction;

        public Node(int row, int col, int value, int direction, int directionCount) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.direction = direction;
            this.directionCount = directionCount;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.value, o.value);
        }

        public String toString() {
            return this.row + ", " + this.col + " Value: " + this.value + " "
                    + this.direction + ", " + this.directionCount;
        }

        public boolean equals(Object o) {
            if (o instanceof Node other)
                return this.row == other.row && this.col == other.col
                        && this.directionCount == other.directionCount
                        && this.direction == other.direction;
            else
                return  false;
        }
    }
}
