package org.aoc.day6;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WaitForIt {
    public static final String INPUT_FILE = "src/main/resources/inputs/day6.txt";
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
        // start speed = 0ms
        // each 1ms boat speed increases 1ms
        int result = 1;

        List<Integer> times = Arrays.stream(data.split("\n")[0]
                .split(":")[1]
                .strip().split(" "))
                .filter(item -> !item.isEmpty())
                .map(item -> Integer.parseInt(item.strip())).toList();

        List<Integer> distances = Arrays.stream(data.split("\n")[1]
                .split(":")[1]
                .strip().split(" "))
                .filter(item -> !item.isEmpty())
                .map(item -> Integer.parseInt(item.strip())).toList();

        System.out.println(times);
        System.out.println(distances);

        for (int j = 0; j < times.size() ; j++) {
            int time = times.get(j);
            int distanceToBeat = distances.get(j);
            int timeLeft = time;

            int numWays = 0;
            int distanceTraveled = 0;

            for (int i = 0; i <= time; i++) {
                // i = how long we hold the button
                // timeLeft is how long we can move for
                // i = the speed our boat can go
                distanceTraveled = timeLeft * i;
                if (distanceTraveled > distanceToBeat) {
                    numWays++;
                }
                timeLeft--;
            }
            result *= numWays;
        }

        return result;
    }
    public static BigInteger partTwo(String data) {

        List<Integer> times = Arrays.stream(data.split("\n")[0]
                        .split(":")[1]
                        .strip().split(" "))
                .filter(item -> !item.isEmpty())
                .map(item -> Integer.parseInt(item.strip())).toList();

        List<Integer> distances = Arrays.stream(data.split("\n")[1]
                        .split(":")[1]
                        .strip().split(" "))
                .filter(item -> !item.isEmpty())
                .map(item -> Integer.parseInt(item.strip())).toList();

        System.out.println(times);
        System.out.println(distances);

        BigInteger time = new BigInteger(times.stream()
                .map(item -> Integer.toString(item))
                .collect(Collectors.joining("")));

        BigInteger distanceToBeat = new BigInteger(distances.stream()
                .map(item -> Integer.toString(item))
                .collect(Collectors.joining("")));
        System.out.println("Time: " + time + "\tDistance: " + distanceToBeat);

        BigInteger timeLeft = time;

        BigInteger numWays = BigInteger.ZERO;
        BigInteger distanceTraveled = BigInteger.ZERO;

        BigInteger left = BigInteger.ZERO;
        BigInteger right = time;
        // Binary Search for start and end ranges
        // find left
        while (left.compareTo(right) <= 0) {

            BigInteger timeToHoldButton = left.add(right).divide(BigInteger.TWO);
            System.out.println(timeToHoldButton);
            timeLeft = time.subtract(timeToHoldButton);
            distanceTraveled = timeLeft.multiply(timeToHoldButton);

            System.out.println("Left: " + left + "\tRight: " + right);
            System.out.println("distanceTraveled: " + distanceTraveled + "\tdistanceToBeat: " + distanceToBeat);

            if (distanceTraveled.compareTo(distanceToBeat) > 0) {
                right = timeToHoldButton.subtract(BigInteger.ONE);
            } else if (distanceTraveled.compareTo(distanceToBeat) < 0) {
                left = timeToHoldButton.add(BigInteger.ONE);
            } else {
                System.out.println("equal?");
            }

        }
        System.out.println(left + " " + right);
        BigInteger leftRange = left;

        left = BigInteger.ZERO;
        right = time;
        // find right
        while (left.compareTo(right) <= 0) {

            BigInteger timeToHoldButton = left.add(right).divide(BigInteger.TWO);
            System.out.println(timeToHoldButton);
            timeLeft = time.subtract(timeToHoldButton);
            distanceTraveled = timeLeft.multiply(timeToHoldButton);

            System.out.println("Left: " + left + "\tRight: " + right);
            System.out.println("distanceTraveled: " + distanceTraveled + "\tdistanceToBeat: " + distanceToBeat);

            if (distanceTraveled.compareTo(distanceToBeat) < 0) {
                right = timeToHoldButton.subtract(BigInteger.ONE);
            } else if (distanceTraveled.compareTo(distanceToBeat) > 0) {
                left = timeToHoldButton.add(BigInteger.ONE);
            } else {
                System.out.println("equal?");
            }

        }
        System.out.println("Right pointer: " + left + " " + right);
        BigInteger rightRange = left;

        return rightRange.subtract(leftRange);
    }
}
