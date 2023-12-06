package org.aoc.day5;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class IfYouGiveASeedAFertilizer {
    private static final String INPUT_FILE = "src/main/resources/inputs/day5.txt";
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

    /**
     * New Approach: Use strings.
     * Problem: How do we find the range?
     *  Only cast to bigInt for this calculation
     *  take (source range end > seed > source range start)
     *  then there is a mapping
     *      -> How to find this mapping?
     *          seed - source range start + destination range start = destination mapping val
     *  otherwise, map to itself
     */
    public static BigInteger partOne(String data) {
        //System.out.println(data);
        String[] lines = data.split(System.lineSeparator());
        System.out.println(Arrays.toString(lines));
        List<String> mySeeds = Arrays.stream(lines[0]
                .split(":")[1]
                .strip()
                .split(" "))
                .toList();
        System.out.println(mySeeds);


        List<List<List<String>>> maps = new ArrayList<>();

        int i = 3;
        List<List<String>> numbers = new ArrayList<>();
        while (i < lines.length) {
            if (lines[i].isEmpty()) {
                //System.out.println(numbers);
                maps.add(numbers);
                numbers = new ArrayList<>();
                i += 2;
            }
            numbers.add(Arrays.asList(lines[i].strip().split(" ")));
            i++;
        }
        //System.out.println(numbers);
        maps.add(numbers);
        System.out.println(maps);

        BigInteger minLocation = BigInteger.valueOf((long) Double.POSITIVE_INFINITY);
        //System.out.println("Min: " + minLocation);
        // Go through maps for each seed
        for (String seed: mySeeds) {
            BigInteger value = new BigInteger(seed);
            for (List<List<String>> map: maps) {
                // Check if seed is in any range
                value = getDestinationValue(map, value);
                System.out.println("Final Destination: " + value);
            }
            minLocation = minLocation.min(value);
        }

        return minLocation;
    }

    public static BigInteger partTwo(String data) {
        //System.out.println(data);
        String[] lines = data.split(System.lineSeparator());
        //System.out.println(Arrays.toString(lines));
        List<String> mySeeds = Arrays.stream(lines[0]
                        .split(":")[1]
                        .strip()
                        .split(" "))
                .toList();
        //System.out.println(mySeeds);


        List<List<List<String>>> maps = new ArrayList<>();

        int i = 3;
        List<List<String>> numbers = new ArrayList<>();
        while (i < lines.length) {
            if (lines[i].isEmpty()) {
                //System.out.println(numbers);
                maps.add(numbers);
                numbers = new ArrayList<>();
                i += 2;
            }
            numbers.add(Arrays.asList(lines[i].strip().split(" ")));
            i++;
        }
        //System.out.println(numbers);
        maps.add(numbers);
        //System.out.println(maps);

        BigInteger minLocation = BigInteger.valueOf((long) Double.POSITIVE_INFINITY);
        //System.out.println("Min: " + minLocation);
        // Go through maps for each seed
        for (int k = 0; k + 1 < mySeeds.size(); k += 2) {
            BigInteger start = new BigInteger(mySeeds.get(k));
            BigInteger length = new BigInteger(mySeeds.get(k + 1));
            BigInteger end = start.add(length.subtract(BigInteger.ONE));
            List<List<String>> destinationRanges = new ArrayList<>();
            destinationRanges.add(new ArrayList<>(Arrays.asList(start.toString(), end.toString())));
            System.out.println("Initial Seeds: " + destinationRanges);
            for (List<List<String>> map : maps) {
                destinationRanges = getDestinationRanges(map, destinationRanges);
                System.out.println("Destination Ranges: " + destinationRanges);
            }
            System.out.println("Should be locations: " + destinationRanges);

            // Find minimum from Location ranges
            minLocation = minLocation.min(destinationRanges.stream().map(item ->
                    new BigInteger(item.get(0)).min(new BigInteger(item.get(1))))
                            .reduce(BigInteger.ZERO, BigInteger::min));

//            for (String seed: mySeeds) {
//                BigInteger value = new BigInteger(seed);
//                for (List<List<String>> map: maps) {
//                    // Check if seed is in any range
//                    value = getDestinationValue(map, value);
//                    System.out.println("Final Destination: " + value);
//                }
//                minLocation = minLocation.min(value);
//            }
        }

        return minLocation;
    }

    private static BigInteger getDestinationValue(List<List<String>> numbers, BigInteger seed) {
        BigInteger destination = seed;
        for (List<String> num : numbers) {
            //System.out.println(num);
            BigInteger destinationStart = new BigInteger(num.get(0));
            BigInteger sourceStart = new BigInteger(num.get(1));
            BigInteger rangeLength =  new BigInteger(num.get(2));

            BigInteger sourceEnd = sourceStart.add(rangeLength).subtract(BigInteger.ONE);
//            System.out.println(String.format(
//                    "Source Start:  %s\tSource End: %s\nDestination Start: %s\t Seed: %s",
//                    sourceStart, sourceEnd, destinationStart, seed));
//
//            take (source range end > seed > source range start)
//                    *  then there is a mapping
//     *      -> How to find this mapping?
//     *          seed - source range start + destination range start = destination mapping val
//     *  otherwise, map to itself
            if (seed.compareTo(sourceEnd) <= 0 && seed.compareTo(sourceStart) >= 0) {
                //System.out.println("true");
                destination = seed.subtract(sourceStart).add(destinationStart);
            }
        }

        return destination;
    }

    /**
     *
     * @param numbers
     * @param seed
     * @return List of destination ranges
     */
    private static List<List<String>> getDestinationRanges(List<List<String>> numbers, List<List<String>> seedRanges) {
        List<List<String>> destinationRanges = new ArrayList<>(seedRanges);

        for (int i = 0; i < seedRanges.size(); i++) {
            System.out.println("Checking seed: " + seedRanges.get(i));
            for (List<String> num : numbers) {
                //System.out.println(num);
                BigInteger seedStart = new BigInteger(seedRanges.get(i).get(0));
                BigInteger seedEnd = new BigInteger(seedRanges.get(i).get(1));

                BigInteger destinationStart = new BigInteger(num.get(0));
                BigInteger sourceStart = new BigInteger(num.get(1));
                BigInteger rangeLength =  new BigInteger(num.get(2));

                BigInteger sourceEnd = sourceStart.add(rangeLength).subtract(BigInteger.ONE);

                // Check if source includes seedRange
                System.out.println("Seed Start: " + seedStart + "\tSource End: " + sourceEnd);
                if (sourceStart.compareTo(seedEnd) > 0 || seedStart.compareTo(sourceEnd) > 0) {
                    // no intersection
                } else {
                    BigInteger intersectionStart = seedStart.max(sourceStart);
                    BigInteger intersectionEnd = seedEnd.max(sourceEnd);
                    if (intersectionStart.compareTo(seedStart) > 0) {
                        List<String> selfRanges = Arrays.asList(seedStart.toString(),
                                intersectionStart.subtract(BigInteger.ONE).toString());

                        List<String> intersectionRanges = Arrays.asList(intersectionStart.toString(),
                                intersectionEnd.toString());

                        destinationRanges.add(selfRanges);
                        destinationRanges.add(intersectionRanges);
                    } else if (intersectionEnd.compareTo(seedEnd) < 0) {
                        List<String> selfRanges = Arrays.asList(
                                intersectionEnd.add(BigInteger.ONE).toString(),
                                seedEnd.toString());

                        List<String> intersectionRanges = Arrays.asList(intersectionStart.toString(),
                                intersectionEnd.toString());

                        destinationRanges.add(selfRanges);
                        destinationRanges.add(intersectionRanges);
                    }
                }
            }
        }

        return destinationRanges;
    }
}
