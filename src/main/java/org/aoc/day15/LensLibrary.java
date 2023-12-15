package org.aoc.day15;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LensLibrary {
    public static final String INPUT_FILE = "src/main/resources/inputs/day15.txt";
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
        List<String> steps = Arrays.asList(data.split(","));
        long result = 0;
        for (String s: steps) {
            result += hash(s);
        }

        return result;
    }

    private static long partTwo(String data) {
        List<String> steps = Arrays.asList(data.split(","));
        List<List<String>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }
        long result = 0;
        for (String s: steps) {
            System.out.println(s);
            if (s.contains("=")) {
                String[] split = s.split("=");
                int val = hash(split[0]);
                String label = split[0] + " " + split[1];
                boolean found = false;
                for (int i = 0; i < boxes.get(val).size(); i++) {
                    if (boxes.get(val).get(i).startsWith(split[0])) {
                        boxes.get(val).set(i, label);
                        found = true;
                        break;
                    }
                }
                if (!found) boxes.get(val).add(label);

            } else if (s.contains("-")) {
                String[] split = s.split("-");
                int val = hash(split[0]);
                for (int i = 0; i < boxes.get(val).size(); i++) {
                    if (boxes.get(val).get(i).startsWith(split[0])) {
                        boxes.get(val).remove(i);
                        break;
                    }
                }
            }
            System.out.println(boxes);
            //result += hash(s);
        }
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {

                long val = (long) (i + 1) * (j + 1)
                        * (Integer.parseInt(boxes.get(i).get(j).split(" ")[1]));
                result += val;
                System.out.println(boxes.get(i).get(j) + ": "
                        + (i + 1) + " * " + (j + 1) + " * "
                        + (Integer.parseInt(boxes.get(i).get(j).split(" ")[1]))
                + " = " + val);
            }
        }

        return result;
    }

    private static int hash(String s) {
        int value = 0;
        for (int i = 0; i < s.length(); i++) {
            value += s.charAt(i);
            value *= 17;
            value = value % 256;
        }
        return value;
    }
}
