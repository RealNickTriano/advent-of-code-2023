package org.aoc.day18;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LavaductLagoon {
    public static final String INPUT_FILE = "src/main/resources/inputs/day18.txt";

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
        String[] lines = data.split(System.lineSeparator());

        int result = 0;
        List<List<Integer>> borderCords = new ArrayList<>();
        List<Integer> next = Arrays.asList(0, 0);
        borderCords.add(next);
        for (String line : lines) {
            String[] split = line.split(" ");
            String dir = split[0];
            int moves = Integer.parseInt(split[1]);
            String hex = split[2];

            switch (dir) {
                case "R":
                    next = Arrays.asList(next.get(0), next.get(1) + moves);
                    break;
                case "L":
                    next = Arrays.asList(next.get(0), next.get(1) - moves);
                    break;
                case "U":
                    next = Arrays.asList(next.get(0) + moves, next.get(1));
                    break;
                case "D":
                    next = Arrays.asList(next.get(0) - moves, next.get(1));
                    break;
            }
            borderCords.add(next);
            System.out.println(borderCords);

        }

        System.out.println(borderCords);

        // Shoelace Formula to calculate area (This calculates in terms of squares on a graph, not points)
        int area = 0;
        for (int i = 0; i < borderCords.size() - 1; i++) {
            int x1 = borderCords.get(i).get(1);
            int x2 = borderCords.get(i + 1).get(1);
            int y1 = borderCords.get(i).get(0);
            int y2 = borderCords.get(i + 1).get(0);

            int d = (x1 * y2) - (x2 * y1);
            area += d;
        }

        // Calculate perimeter
        int border = 0;
        for (int i = 0; i < borderCords.size() - 1; i++) {
            int differenceRow = Math.abs(borderCords.get(i).get(0) - borderCords.get(i + 1).get(0));
            int differenceCol = Math.abs(borderCords.get(i).get(1) - borderCords.get(i + 1).get(1));
            border += differenceRow + differenceCol;
        }

        // Use Pick's Theorem to get interior area
        area = Math.abs(area / 2);
        System.out.println("Border: " + border);
        int intArea = area - (border / 2) + 1;
        System.out.println("Interior Area: " + intArea);
        result = border + intArea;

        return result;
    }

    private static long partTwo(String data) {
        String[] lines = data.split(System.lineSeparator());

        long result = 0;
        List<List<Long>> borderCords = new ArrayList<>();
        List<Long> next = Arrays.asList(0L, 0L);
        borderCords.add(next);
        for (String line : lines) {
            String[] split = line.split(" ");
            String hex = split[2];
            int dir = Integer.parseInt(hex.substring(hex.length() - 2, hex.length() - 1));
            hex = hex.substring(2, hex.length() - 2);
            int moves = Integer.parseInt(hex, 16);
            System.out.println(moves);
            System.out.println(dir);



            switch (dir) {
                case 0:
                    next = Arrays.asList(next.get(0), next.get(1) + moves);
                    break;
                case 2:
                    next = Arrays.asList(next.get(0), next.get(1) - moves);
                    break;
                case 3:
                    next = Arrays.asList(next.get(0) + moves, next.get(1));
                    break;
                case 1:
                    next = Arrays.asList(next.get(0) - moves, next.get(1));
                    break;
            }
            borderCords.add(next);
            System.out.println(borderCords);

        }

        System.out.println(borderCords);

        // Shoelace Formula to calculate area (This calculates in terms of squares on a graph, not points)
        long area = 0;
        for (int i = 0; i < borderCords.size() - 1; i++) {
            long x1 = borderCords.get(i).get(1);
            long x2 = borderCords.get(i + 1).get(1);
            long y1 = borderCords.get(i).get(0);
            long y2 = borderCords.get(i + 1).get(0);

            long d = (x1 * y2) - (x2 * y1);
            area += d;
        }

        // Calculate perimeter
        long border = 0;
        for (int i = 0; i < borderCords.size() - 1; i++) {
            long differenceRow = Math.abs(borderCords.get(i).get(0) - borderCords.get(i + 1).get(0));
            long differenceCol = Math.abs(borderCords.get(i).get(1) - borderCords.get(i + 1).get(1));
            border += differenceRow + differenceCol;
        }

        // Use Pick's Theorem to get interior area
        area = Math.abs(area / 2);
        System.out.println("Border: " + border);
        long intArea = area - (border / 2) + 1;
        System.out.println("Interior Area: " + intArea);
        result = border + intArea;

        return result;
    }

}
