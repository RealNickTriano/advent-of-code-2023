package org.aoc.day2;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class DayTwo {
    private static final String INPUT_FILE = "src/main/resources/inputs/day1.txt";
    public static void main(String[] args) {
        try {
            String data = ReadFiles.readFileToString(INPUT_FILE);
            String[] lines = data.split(System.lineSeparator());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
