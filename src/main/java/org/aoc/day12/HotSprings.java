package org.aoc.day12;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class HotSprings {
    public static final String INPUT_FILE = "src/main/resources/inputs/day12.txt";
    // HashMap to memoize results of ways function
    public static Map<String, Long> memo = new HashMap<>();
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
        List<List<String>> lines = asList(data.split(System.lineSeparator())).stream()
                .map(item -> List.of(item.split(" ")))
                .toList();
        //System.out.println(lines);

        List<String> records = lines.stream()
                .map(item -> item.get(0))
                .toList();
        List<List<Integer>> requirements = lines.stream()
                .map(item -> Arrays.stream(item.get(1).split(",")).map(Integer::parseInt).toList())
                .toList();

        //System.out.println(records);
        //System.out.println(requirements);

        long result = 0;
        for (int i = 0; i < records.size(); i++) {
            long num = ways(records.get(i), requirements.get(i));
            System.out.println(num);
            result += num;
        }
        return result;
    }

    private static long partTwo(String data) {
        List<List<String>> lines = asList(data.split(System.lineSeparator())).stream()
                .map(item -> List.of(item.split(" ")))
                .toList();
        //System.out.println(lines);

        List<String> records = lines.stream()
                .map(item -> item.get(0)
                        + "?" + item.get(0)
                        + "?" + item.get(0)
                        + "?" + item.get(0)
                        + "?" + item.get(0))
                .toList();
        List<List<Integer>> requirements = lines.stream()
                .map(item -> Arrays.stream(new String(item.get(1)
                        + "," + item.get(1)
                        + "," + item.get(1)
                        + "," + item.get(1)
                        + "," + item.get(1))
                        .split(",")).map(Integer::parseInt).toList())
                .toList();

        System.out.println(records);
        System.out.println(requirements);

        List<Integer> list = Arrays.asList(5, 22, 73, 2, -23);
        list.sort(null);

        long result = 0;
        for (int i = 0; i < records.size(); i++) {
            long num = ways(records.get(i), requirements.get(i));
            //System.out.println(num);
            result += num;
        }
        return result;
    }

    public static long ways(String record, List<Integer> requirements) {

        if (record.isEmpty()) {
            if (requirements.isEmpty()) {
                //System.out.println("Match!");
                return 1;
            }
            return 0;
        }

        if (requirements.isEmpty()) {
            if (record.contains("#")) return 0;
            //System.out.println("Match!");
            return 1;
        }

        String key = record + " " + requirements;
        if (memo.containsKey(key)) return memo.get(key);

        long result = 0;

        if (record.charAt(0) == '.' || record.charAt(0) == '?') {
            //System.out.println(record + "\t" + requirements);
            result += ways(record.substring(1), requirements);
        }

        if (record.charAt(0) == '#' || record.charAt(0) == '?') {
                // there should be enough chars left in the string to
                // satisfy the current block size condition
            if (requirements.get(0) <= record.length()
                    // The next number of characters in the string should not
                    // contain a "." because that would interrupt the block
                    && !record.substring(0, requirements.get(0)).contains(".")
                    && (requirements.get(0) == record.length()
                        || record.charAt(requirements.get(0)) != '#')) {

                //System.out.println(record + "\t" + requirements);
                //System.out.println("Block Consumed: " + record);
                int startIndex = requirements.get(0) == record.length()
                        ? requirements.get(0)
                        : requirements.get(0) + 1;

                result += ways(record.substring(startIndex)
                        , requirements.subList(1, requirements.size()));

            }
        }

        memo.put(key, result);
        return result;
    }

    public static int printCombinations2(String record, List<Integer> requirement) {

        for (int i = 0; i < record.length(); i++) {
            String subRecord = record.substring(0, i + 1);
            for (int j = 0; j < requirement.size(); j++) {
                List<Integer> subRequirement = requirement.subList(0, j + 1);
                for (int k = 0; k <= subRequirement.getLast(); k++) {

                }
            }
        }
        int matches = 0;
        System.out.println(record);
        if (!record.contains("?")) {
            if (satisfiesRequirements(record, requirement))
                return 1;
            else return 0;
        }
        matches += printCombinations(record.replaceFirst("[?]", "#"), requirement);
        matches += printCombinations(record.replaceFirst("[?]", "."), requirement);

        return matches;
    }

    public static int printCombinations(String record, List<Integer> requirement) {
        int matches = 0;
        System.out.println(record);
        if (!record.contains("?")) {
            if (satisfiesRequirements(record, requirement))
                return 1;
            else return 0;
        }
        matches += printCombinations(record.replaceFirst("[?]", "#"), requirement);
        matches += printCombinations(record.replaceFirst("[?]", "."), requirement);

        return matches;
    }

    private static boolean satisfiesRequirements(String record, List<Integer> requirement) {
        List<Integer> nums = Arrays.stream(record.split("\\."))
                .filter(item -> !item.isEmpty())
                .map(String::length).toList();
        //System.out.println(nums);
        return nums.equals(requirement);
    }
}
