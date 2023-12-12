package org.aoc.day12;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class HotSprings {
    public static final String INPUT_FILE = "src/main/resources/inputs/day12.txt";
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

        int result = 0;
        for (int i = 0; i < records.size(); i++) {
            result += printCombinations(records.get(i), requirements.get(i));
        }
        return result;
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
