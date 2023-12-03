package org.aoc.day3;

import org.aoc.utils.ReadFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DayThree {
    private static final String INPUT_FILE = "src/main/resources/inputs/day3.txt";
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

    public static int partOne(String data) {
        // Create 2D Matrix
        // Search through matrix
        // When you see a number  Character.isDigit
        // Look around the number in a box up/down/left/right/diagonal
        // If symbol is found set flag to keep=true
        // continue to build the number, number is complete if on character that is not a digit
        // while loop with pointers is probably easier
        String[] lines = data.split("\n");
        //System.out.println(Arrays.toString(lines));
        int numRows = lines.length;
        int numCols = lines[0].toCharArray().length - 1;
        //System.out.println(numRows + " " + numCols);
        char[][] matrix = new char[numRows][numCols];

        // Build Matrix
        for (int i = 0; i < lines.length; i++) {
            matrix[i] = lines[i].toCharArray();
        }

        int result = 0;
        boolean keep;

        // Search Matrix
        for (int row = 0; row < numRows; row++) {
            //System.out.println(Arrays.toString(matrix[row]));
            for (int col = 0; col < numCols; col++) {
                //System.out.println("Row: " + row + " Col: " + col);
                // Start building number and looking for symbols
                if (Character.isDigit(matrix[row][col])) {
                    StringBuilder number = new StringBuilder();
                    boolean buildingNumber = true;
                    keep = false;
                    System.out.println("Found digit building number");

                    while(buildingNumber) {
                        // append to number
                        number.append(matrix[row][col]);
                        //System.out.println(row + " " + col);
                        // check around for symbols
                        if (!keep && checkForAdjacentSymbols(matrix, row, col, numRows, numCols)) {
                            keep = true;
                            System.out.println("Found adjacent symbol next to: " + row + " " + col);
                        }
                        // End of line
                        if (col == numCols - 1) {
                            System.out.println("On last char in line exiting");
                            buildingNumber = false;
                        } else {
                            // If no longer on number exit loop
                            buildingNumber = Character.isDigit(matrix[row][col + 1]);
                        }
                        col++;
                    }
                    col--;

                    if (keep) {
                        System.out.println("Adding: " + Integer.parseInt(String.valueOf(number)));
                        result += Integer.parseInt(String.valueOf(number));
                        System.out.println("Finished building number and KEEP: " + number);
                    } else {
                        System.out.println("NOT KEEPING: " + number);
                    }
                }
            }
            //System.out.println();
        }

        return result;
    }

    public static int partTwo(String data) {

        String[] lines = data.split("\n");
        //System.out.println(Arrays.toString(lines));
        int numRows = lines.length;
        int numCols = lines[0].toCharArray().length - 1;
        //System.out.println(numRows + " " + numCols);
        char[][] matrix = new char[numRows][numCols];

        // Build Matrix
        for (int i = 0; i < lines.length; i++) {
            matrix[i] = lines[i].toCharArray();
        }

        int result = 0;
        boolean keep;

        Map<String, Integer> starPosToNums = new HashMap<>();

        // Search Matrix
        for (int row = 0; row < numRows; row++) {
            //System.out.println(Arrays.toString(matrix[row]));
            for (int col = 0; col < numCols; col++) {
                //System.out.println("Row: " + row + " Col: " + col);
                // Start building number and looking for symbols
                if (Character.isDigit(matrix[row][col])) {
                    StringBuilder number = new StringBuilder();
                    boolean buildingNumber = true;
                    keep = false;
                    System.out.println("Found digit building number");
                    int[] res = new int[3];

                    while(buildingNumber) {
                        // append to number
                        number.append(matrix[row][col]);
                        //System.out.println(row + " " + col);
                        // check around for symbols
                        if (checkForAdjacentStars(matrix, row, col, numRows, numCols) != null) {
                            res = checkForAdjacentStars(matrix, row, col, numRows, numCols);
                        }
                        if (!keep && checkForAdjacentStars(matrix, row, col, numRows, numCols) != null) {
                            keep = true;
                            System.out.println("Found adjacent star next to: " + row + " " + col);
                        }
                        // End of line
                        if (col == numCols - 1) {
                            System.out.println("On last char in line exiting");
                            buildingNumber = false;
                        } else {
                            // If no longer on number exit loop
                            buildingNumber = Character.isDigit(matrix[row][col + 1]);
                        }
                        col++;
                    }
                    col--;

                    if (keep) {
                        //System.out.println("Adding: " + Integer.parseInt(String.valueOf(number)));
                        int num = Integer.parseInt(String.valueOf(number));
                        //System.out.println("Finished building number and KEEP: " + num);
                        if (starPosToNums.get(String.format("%s %s", res[1], res[2])) != null) {
                            System.out.println("star pos not null for pos: " + res[1] + res[2] + " " + num);
                            System.out.println("Pair is: " + starPosToNums.get(String.format("%s %s", res[1], res[2])));
                            result += starPosToNums.get(String.format("%s %s", res[1], res[2])) * num;
                        } else {
                            System.out.println("adding new star pos: " + res[1] + res[2] + " " + num);
                            starPosToNums.put(String.format("%s %s", res[1], res[2]), num);
                        }
                    } else {
                        System.out.println("NOT KEEPING: " + number);
                    }
                }
            }
            //System.out.println();
        }

        return result;
    }

    private static boolean checkForAdjacentSymbols(char[][] matrix, int row, int col, int numRows, int numCols) {
        boolean isAdjacent = false;
        // Up
        if (row != 0 && matrix[row - 1][col] != '.'
                && !Character.isDigit(matrix[row - 1][col])) isAdjacent = true;
        // Down
        else if (row != numRows - 1 && matrix[row + 1][col] != '.'
                && !Character.isDigit(matrix[row + 1][col])) isAdjacent = true;
        // Left
        else if (col != 0 && matrix[row][col - 1] != '.'
                && !Character.isDigit(matrix[row][col - 1])) isAdjacent = true;
        // Right
        else if (col != numCols - 1 && matrix[row][col + 1] != '.'
                && !Character.isDigit(matrix[row][col + 1])) isAdjacent = true;
        // Up-Left
        else if (row != 0 && col != 0
                && matrix[row - 1][col - 1] != '.'
                && !Character.isDigit(matrix[row - 1][col - 1])) isAdjacent = true;
        // Up-Right
        else if (row != 0 && col != numCols - 1
                && matrix[row - 1][col + 1] != '.'
                && !Character.isDigit(matrix[row - 1][col + 1])) isAdjacent = true;
        // Down-Left
        else if (row != numRows - 1 && col != 0
                && matrix[row + 1][col - 1] != '.'
                && !Character.isDigit(matrix[row + 1][col - 1])) isAdjacent = true;
        // Down-Right
        else if (row != numRows - 1 && col != numCols - 1
                && matrix[row + 1][col + 1] != '.'
                && !Character.isDigit(matrix[row + 1][col + 1])) isAdjacent = true;

        return isAdjacent;
    }

    private static int[] checkForAdjacentStars(char[][] matrix,
                                                 int row, int col,
                                                 int numRows, int numCols) {
        boolean isAdjacent = false;
        // Up
        if (row != 0 && matrix[row - 1][col] == '*'){
            return new int[]{1, row - 1, col};
        }
            // Down
        else if (row != numRows - 1 && matrix[row + 1][col] == '*'){
            return new int[]{1, row + 1, col};
        }
            // Left
        else if (col != 0 && matrix[row][col - 1] == '*'){
            return new int[]{1, row, col - 1};
        }
            // Right
        else if (col != numCols - 1 && matrix[row][col + 1] == '*'){
            return new int[]{1, row, col + 1};
        }
            // Up-Left
        else if (row != 0 && col != 0
                && matrix[row - 1][col - 1] == '*'){
            return new int[]{1, row - 1, col - 1};
        }
            // Up-Right
        else if (row != 0 && col != numCols - 1
                && matrix[row - 1][col + 1] == '*'){
            return new int[]{1, row - 1, col + 1};
        }
            // Down-Left
        else if (row != numRows - 1 && col != 0
                && matrix[row + 1][col - 1] == '*'){
            return new int[]{1, row + 1, col - 1};
        }
            // Down-Right
        else if (row != numRows - 1 && col != numCols - 1
                && matrix[row + 1][col + 1] == '*'){
            return new int[]{1, row + 1, col + 1};
        }

        return null;
    }
}
