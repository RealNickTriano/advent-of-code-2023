package org.aoc.day10;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PipeMaze {
    public static final String INPUT_FILE = "src/main/resources/inputs/day10.txt";
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
        System.out.println(data);
//        | is a vertical pipe connecting north and south.
//        - is a horizontal pipe connecting east and west.
//        L is a 90-degree bend connecting north and east.
//        J is a 90-degree bend connecting north and west.
//        7 is a 90-degree bend connecting south and west.
//        F is a 90-degree bend connecting south and east.
//        . is ground; there is no pipe in this tile.
//        S is the starting position of the animal; there is a pipe on this tile,
//                but your sketch doesn't show what shape the pipe has.

        List<List<String>> matrix = Arrays.asList(data.split(System.lineSeparator())).stream()
                .map(item -> Arrays.asList(item.split("")))
                .toList();

        System.out.println(matrix);
        int[] startPos = new int[] {0, 0};
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if(matrix.get(i).get(j).equals("S")){
                    System.out.println(i + " " + j + " " + "S");
                    startPos[0] = i;
                    startPos[1] = j;
                }

            }
        }
        int[] currPos = new int[] {startPos[0], startPos[1]};
        int length = 1;
        String previous = "";
        List<String> right = Arrays.asList("S", "-", "L", "F");
        List<String> left = Arrays.asList("S", "-", "J", "7");
        List<String> up = Arrays.asList("S", "|", "L", "J");
        List<String> down = Arrays.asList("S", "|", "7", "F");
        System.out.println("Start Pos:  " + Arrays.toString(startPos));
        do {
            if (!previous.equals("left") && right.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookRight(currPos[0], currPos[1], matrix)) {
                currPos[1]++;
                previous = "right";
            }else if (!previous.equals("right") && left.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookLeft(currPos[0], currPos[1], matrix)) {
                currPos[1]--;
                previous = "left";
            }else if (!previous.equals("down") && up.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookUp(currPos[0], currPos[1], matrix)) {
                currPos[0]--;
                previous = "up";
            }else if (!previous.equals("up") && down.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookDown(currPos[0], currPos[1], matrix)) {
                currPos[0]++;
                previous = "down";
            }
            length++;
            System.out.println("Moved to: " + Arrays.toString(currPos));
            //if (currPos[0] == 3 && currPos[1] == 3) break;
        } while (!Arrays.equals(currPos, startPos));

        return length / 2;
    }

    private static int partTwo(String data) {
        System.out.println(data);
//        | is a vertical pipe connecting north and south.
//        - is a horizontal pipe connecting east and west.
//        L is a 90-degree bend connecting north and east.
//        J is a 90-degree bend connecting north and west.
//        7 is a 90-degree bend connecting south and west.
//        F is a 90-degree bend connecting south and east.
//        . is ground; there is no pipe in this tile.
//        S is the starting position of the animal; there is a pipe on this tile,
//                but your sketch doesn't show what shape the pipe has.

        List<List<String>> matrix = new ArrayList<>(Arrays.asList(data.split(System.lineSeparator())).stream()
                .map(item -> Arrays.asList(item.split("")))
                .toList());

        System.out.println(matrix);
        int[] startPos = new int[] {0, 0};
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if(matrix.get(i).get(j).equals("S")){
                    System.out.println(i + " " + j + " " + "S");
                    startPos[0] = i;
                    startPos[1] = j;
                }

            }
        }
        List<List<Integer>> borderPos = new ArrayList<>();
        int[] currPos = new int[] {startPos[0], startPos[1]};
        int length = 1;
        String previous = "";
        List<String> right = Arrays.asList("S", "-", "L", "F");
        List<String> left = Arrays.asList("S", "-", "J", "7");
        List<String> up = Arrays.asList("S", "|", "L", "J");
        List<String> down = Arrays.asList("S", "|", "7", "F");
        System.out.println("Start Pos:  " + Arrays.toString(startPos));
        do {
            if (!previous.equals("left") && right.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookRight(currPos[0], currPos[1], matrix)) {
                //matrix.get(currPos[0]).set(currPos[1], "S");
                currPos[1]++;
                previous = "right";
            }else if (!previous.equals("right") && left.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookLeft(currPos[0], currPos[1], matrix)) {
                //matrix.get(currPos[0]).set(currPos[1], "S");
                currPos[1]--;
                previous = "left";
            }else if (!previous.equals("down") && up.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookUp(currPos[0], currPos[1], matrix)) {
                //matrix.get(currPos[0]).set(currPos[1], "S");
                currPos[0]--;
                previous = "up";
            }else if (!previous.equals("up") && down.contains(matrix.get(currPos[0]).get(currPos[1]))
                    && lookDown(currPos[0], currPos[1], matrix)) {
                //matrix.get(currPos[0]).set(currPos[1], "S");
                currPos[0]++;
                previous = "down";
            }
            length++;
            System.out.println("Moved to: " + Arrays.toString(currPos));
            borderPos.add(Arrays.asList(currPos[0], currPos[1]));
            //matrix.get(currPos[0]).set(currPos[1], "#");
        } while (!Arrays.equals(currPos, startPos));

        for (List<String> s: matrix) {
            System.out.println(s);
        }

        System.out.println("Border: " + borderPos);
        boolean in = false;
        int count = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (!borderPos.contains(Arrays.asList(i, j))) {
                    // check left and right
                    int intersections = 0;
                    for (int k = 0; k < j; k++) {
                        //System.out.println(i + " " + k);
                        if (borderPos.contains(Arrays.asList(i, k))) {
                            if(matrix.get(i).get(k).equals("|")
                                    || matrix.get(i).get(k).equals("J")
                                    || matrix.get(i).get(k).equals("L")) {
                                System.out.println(i + " " + j + " Intersected with position: " + i + " " + k);
                                intersections++;
                            }
                        }
                    }
                    if (intersections % 2 == 1) {
                        count++;
                        System.out.println("point in polygon: " + i + " " + j);
                        List<String> row = matrix.get(i);
                        row.set(j, "I");
                        matrix.set(i, row);
                    }
                }
            }
        }

        for (List<String> list : matrix) {
            System.out.println(list);
        }
        return count;
    }

    // Look right for anything that can connect to the left: -, J, 7
    public static boolean lookRight(int row, int col, List<List<String>> matrix) {
        String pipe = matrix.get(row).get(col + 1);
        if (pipe.equals("-")) return true;
        if (pipe.equals("J")) return true;
        if (pipe.equals("7")) return true;
        if (pipe.equals("S")) return true;

        return false;
    }

    public static boolean lookLeft(int row, int col, List<List<String>> matrix) {
        String pipe = matrix.get(row).get(col - 1);
        if (pipe.equals("-")) return true;
        if (pipe.equals("L")) return true;
        if (pipe.equals("F")) return true;
        if (pipe.equals("S")) return true;

        return false;
    }

    public static boolean lookUp(int row, int col, List<List<String>> matrix) {
        String pipe = matrix.get(row - 1).get(col);
        if (pipe.equals("|")) return true;
        if (pipe.equals("F")) return true;
        if (pipe.equals("7")) return true;
        if (pipe.equals("S")) return true;

        return false;
    }

    public static boolean lookDown(int row, int col, List<List<String>> matrix) {
        String pipe = matrix.get(row + 1).get(col);
        if (pipe.equals("|")) return true;
        if (pipe.equals("J")) return true;
        if (pipe.equals("L")) return true;
        if (pipe.equals("S")) return true;

        return false;
    }
}
