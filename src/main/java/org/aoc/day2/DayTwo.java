package org.aoc.day2;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;

public class DayTwo {
    private static final String INPUT_FILE = "src/main/resources/inputs/day2.txt";
    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(partOne(data));
        System.out.println(partTwo(data));
    }

    public static int partOne(String data) {
        String[] lines = data.split(System.lineSeparator());

        Map<String, Integer> colorsMap = new HashMap<>();
        colorsMap.put("red", 12);
        colorsMap.put("green", 13);
        colorsMap.put("blue", 14);


        List<List<String>> gameSets = new ArrayList<>(new ArrayList<>());
        for (int i = 0; i < lines.length; i++) {
            gameSets.add(Arrays.asList(
                    lines[i].split(":")[1]
                            .split(";")));
        }
        //System.out.println(gameSets);

        int result = 0;
        for (int j = 0; j < gameSets.size(); j++) {
            boolean isPossible = true;
            for (int k = 0; k < gameSets.get(j).size(); k++) {
                String set = gameSets.get(j).get(k).strip();
                String[] rolls = set.split(",");
                for (String r: rolls) {
                    String[] splitRoll = r.strip().split(" ");
                    int count = Integer.parseInt(splitRoll[0]);
                    String color = splitRoll[1];
                    if (colorsMap.get(color) < count) {
                        isPossible = false;
                    }
                }
                //System.out.println("Game: " + j + " Set: " + k  + " " + gameSets.get(j).get(k).strip());

            }
            if (isPossible) result += j + 1;
        }

        return result;
    }

    public static int partTwo(String data) {
        String[] lines = data.split(System.lineSeparator());

        Map<String, Integer> colorsMap = new HashMap<>();
        colorsMap.put("red", 12);
        colorsMap.put("green", 13);
        colorsMap.put("blue", 14);


        List<List<String>> gameSets = new ArrayList<>(new ArrayList<>());
        for (int i = 0; i < lines.length; i++) {
            gameSets.add(Arrays.asList(
                    lines[i].split(":")[1]
                            .split(";")));
        }
        //System.out.println(gameSets);

        int result = 0;
        for (int j = 0; j < gameSets.size(); j++) {

            Map<String, Integer> minCounts = new HashMap<>();
            minCounts.put("red", 1);
            minCounts.put("blue", 1);
            minCounts.put("green", 1);

            for (int k = 0; k < gameSets.get(j).size(); k++) {
                String set = gameSets.get(j).get(k).strip();
                String[] rolls = set.split(",");

                for (String r: rolls) {
                    String[] splitRoll = r.strip().split(" ");
                    int count = Integer.parseInt(splitRoll[0]);
                    String color = splitRoll[1];
                    minCounts.put(color, Math.max(minCounts.get(color), count));

                }
//                System.out.println("Game: " + j + " Set: " + k  + " "
//                        + gameSets.get(j).get(k).strip() + " | "
//                        + "red " + minCounts.get("red")
//                        + " blue " + minCounts.get("blue")
//                        + " green " + minCounts.get("green"));

            }

            result += minCounts.get("red")
                    * minCounts.get("blue")
                    * minCounts.get("green");
        }

        return result;
    }


}
