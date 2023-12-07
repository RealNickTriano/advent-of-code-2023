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

        System.out.println(partOne(data));
        //System.out.println(partTwo(data));
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

    public static long partTwo(String data) {
        //System.out.println(data);
        String[] lines = data.split(System.lineSeparator());
        //System.out.println(Arrays.toString(lines));
        List<Long> mySeeds = Arrays.stream(lines[0]
                .split(":")[1]
                .strip()
                .split(" "))
                .map(Long::parseLong)
                .toList();


        List<List<Long>> seedsRange = new ArrayList<>();
        for (int i = 0; i < mySeeds.size() - 1; i+=2) {
            List<Long> range = new ArrayList<>();
            range.add(mySeeds.get(i));
            range.add(mySeeds.get(i) + mySeeds.get(i + 1)  - 1);
            seedsRange.add(range);
        }

        System.out.println("Seed range: " + seedsRange);


        List<List<List<Long>>> maps = new ArrayList<>();

        int i = 3;
        List<List<Long>> numbers = new ArrayList<>();
        while (i < lines.length) {
            if (lines[i].isEmpty()) {
                //System.out.println(numbers);
                maps.add(numbers);
                numbers = new ArrayList<>();
                i += 2;
            }
            numbers.add(Arrays.stream(lines[i]
                    .strip()
                    .split(" "))
                    .map(Long::parseLong)
                    .toList());
            i++;
        }
        //System.out.println(numbers);
        maps.add(numbers);
        System.out.println(maps);

        List<List<Long>> locationRanges = new ArrayList<>();
        for (List<Long> range : seedsRange) {
            List<List<Long>> destinationRanges = new ArrayList<>();
            destinationRanges.add(new ArrayList<>(range));
            for (List<List<Long>> map : maps) {
                destinationRanges = getNextDestinationRanges(destinationRanges, map);
            }
            locationRanges.addAll(destinationRanges);
        }

        System.out.println(locationRanges);
        return 0;
    }

    private static List<List<Long>> getNextDestinationRanges(List<List<Long>> ranges, List<List<Long>> map) {
        List<List<Long>> result = new ArrayList<>();
        // Get destination ranges from source range
        for (List<Long> seed : ranges) {
            long seedStart = seed.get(0);
            long seedEnd = seed.get(1);


            System.out.println("Seed Start: " + seedStart + "\tSeed End: " + seedEnd);

            for (List<Long> mapRange : map) {
                long sourceStart = mapRange.get(1);
                long sourceEnd = mapRange.get(1) + mapRange.get(2) - 1;

                long destinationStart = mapRange.get(0);
                long destinationEnd = mapRange.get(0) + mapRange.get(2) - 1;

                System.out.println("sourceStart: " + sourceStart
                        + "\tsourceEnd: " + sourceEnd
                        + "\tdestinationStart: " + destinationStart
                        + "\tdestinationEnd: " + destinationEnd);

                result.addAll(getMappedRanges(seedStart, seedEnd,
                        sourceStart, sourceEnd,
                        destinationStart, destinationEnd));
            }
        }
        return result;
    }

    private static List<List<Long>> getMappedRanges(long seedStart, long seedEnd,
                                        long sourceStart, long sourceEnd,
                                        long destinationStart, long destinationEnd) {
        List<List<Long>> result = new ArrayList<>();
        // Find source intersections
        if (seedStart > sourceEnd || seedEnd < sourceStart) {
            // No Intersection
        } else {
            // Some Intersection
            if (seedStart >= sourceStart && seedEnd <= sourceEnd) {
                // seed is fully inside source
                long offsetFromStart = seedStart - sourceStart;
                long length = seedEnd - seedStart;
                long newStart = destinationStart + offsetFromStart;
                long newEnd = newStart + length;
                List<List<Long>> newRanges = new ArrayList<>();
                newRanges.add((Arrays.asList(newStart, newEnd)));
                System.out.println("New Ranges: " + newRanges);
                return newRanges;
            } else if (seedEnd >= sourceStart && seedStart < sourceStart) {
                // seed intersects on lower end of source
                List<Long> rangeMapsToSelf = Arrays.asList(seedStart, sourceStart - 1);
                List<Long> destination = Arrays.asList(
                        destinationStart,
                        destinationStart + (seedEnd - sourceStart));

                List<List<Long>> newRanges = new ArrayList<>();
                newRanges.add(rangeMapsToSelf);
                newRanges.add(destination);
                System.out.println("New Ranges: " + newRanges);
                return newRanges;
            } else if (seedStart <= sourceEnd && seedEnd > sourceEnd) {
                // seed intersects on higher end of source
                List<Long> rangeMapsToSelf = Arrays.asList(sourceStart + 1, seedEnd);
                List<Long> destination = Arrays.asList(
                        destinationEnd - (sourceEnd - seedStart),
                        destinationEnd);

                List<List<Long>> newRanges = new ArrayList<>();
                newRanges.add(rangeMapsToSelf);
                newRanges.add(destination);
                System.out.println("New Ranges: " + newRanges);
                return newRanges;
            }
        }

        return result;
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
