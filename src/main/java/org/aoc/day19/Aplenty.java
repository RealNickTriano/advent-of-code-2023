package org.aoc.day19;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;

public class Aplenty {
    public static final String INPUT_FILE = "src/main/resources/inputs/day19.txt";

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
        List<String> lines = Arrays.stream(data.split(System.lineSeparator())).toList();
        List<String> workflows = lines.subList(0, lines.indexOf(""));
        List<String> ratings = lines.subList(lines.indexOf("") + 1, lines.size());
        System.out.println(ratings);
        System.out.println(workflows);
        Map<String, String> workflowsMap = new HashMap<>();
        workflows.forEach(item -> {
            workflowsMap.put(item.substring(0, item.indexOf('{')),
                    item.substring(item.indexOf('{')));
        });
        System.out.println(workflowsMap);

        int result = 0;
        for (String r : ratings) {
            Map<String, Integer> rMap = new HashMap<>();
            String[] split = r.substring(1, r.length() - 1).split(",");
            for (int i = 0; i < split.length; i++) {
                rMap.put(split[i].substring(0, 1), Integer.parseInt(split[i].substring(2)));
            }

            boolean isAccepted = runWorkflow(rMap, workflowsMap);
            System.out.println(rMap + " -> " + isAccepted);
            if (isAccepted) result += sumRatings(r);
        }

        return result;
    }

    private static long partTwo(String data) {
        List<String> lines = Arrays.stream(data.split(System.lineSeparator())).toList();
        List<String> workflows = lines.subList(0, lines.indexOf(""));

        Map<String, String> workflowsMap = new HashMap<>();
        workflows.forEach(item -> {
            workflowsMap.put(item.substring(0, item.indexOf('{')),
                    item.substring(item.indexOf('{')));
        });
        System.out.println(workflowsMap);

        Map<String, List<Integer>> currentRanges = new HashMap<>();
        currentRanges.put("x", Arrays.asList(1, 4000));
        currentRanges.put("m", Arrays.asList(1, 4000));
        currentRanges.put("a", Arrays.asList(1, 4000));
        currentRanges.put("s", Arrays.asList(1, 4000));
        return count(currentRanges, workflowsMap, "in");
    }

    private static long count(Map<String, List<Integer>> currentRanges,
                              Map<String, String> workflowsMap, String currentWorkflow) {
        long total = 0;
        if (currentWorkflow.equals("R"))
            return 0;

        if (currentWorkflow.equals("A")) {
            long product = 1;
            List<List<Integer>> values = currentRanges.values().stream().toList();
            for (int i = 0; i < values.size(); i++) {
                product *= values.get(i).get(1) - values.get(i).get(0) + 1;
            }
            return product;
        }
        String rules = workflowsMap.get(currentWorkflow);
        rules = rules.substring(1, rules.length() - 1);
        String[] conditions = rules.split(",");
        boolean broke = false;
        //System.out.println(Arrays.toString(conditions));
        for (int i = 0; i < conditions.length - 1; i++) {

            String next = conditions[i].split(":")[1];
            String con = conditions[i].split(":")[0];
            String letter = con.substring(0, 1);
            String operator = con.substring(1, 2);
            int number = Integer.parseInt(con.substring(2));
            List<Integer> pass = new ArrayList<>();
            List<Integer> fail = new ArrayList<>();
            List<Integer> range = currentRanges.get(letter);
            if (operator.equals(">")) {
                pass = Arrays.asList(number + 1, range.get(1));
                fail = Arrays.asList(range.get(0), number);
            }

            if (operator.equals("<")) {
                pass = Arrays.asList(range.get(0), number - 1);
                fail = Arrays.asList(number, range.get(1));
            }
            if (pass.get(0) <= pass.get(1)) {
                Map<String, List<Integer>> newRanges = new HashMap<>(currentRanges);
                newRanges.put(letter, pass);
                total += count(newRanges, workflowsMap, next);
            }
            if (fail.get(0) <= fail.get(1)) {
                currentRanges = new HashMap<>(currentRanges);
                currentRanges.put(letter, fail);
            } else {
                // none failed so any other cases dont need to be checked
                broke = true;
                break;
            }
            // Go to last condition
        }
        if (!broke)
            total += count(currentRanges, workflowsMap, conditions[conditions.length - 1]);

        return total;
    }

    private static boolean runWorkflow(Map<String, Integer> rMap, Map<String, String> workflowsMap) {
        String rules = workflowsMap.get("in");
        boolean isCondition = false;
        while (true) {

            rules = rules.substring(1, rules.length() - 1);
            String[] conditions = rules.split(",");
            //System.out.println(Arrays.toString(conditions));
            for (int i = 0; i < conditions.length - 1; i++) {
                String next = conditions[i].split(":")[1];
                String con = conditions[i].split(":")[0];
                String letter = con.substring(0, 1);
                String operator = con.substring(1, 2);
                int number = Integer.parseInt(con.substring(2));
                int rMapVal = rMap.get(letter);
                //System.out.println(con + " " + next + " " + letter + " " + operator);
                //System.out.println(conditions[conditions.length - 1]);
                isCondition = evaluateCondition(rMapVal, operator, number);
                if (isCondition) {
                    System.out.println(next);
                    if (next.equals("R")) return false;
                    if (next.equals("A")) return true;
                    rules = workflowsMap.get(next);
                    break;
                }
            }

            if (!isCondition) {
                String temp = conditions[conditions.length - 1];
                if (temp.equals("R")) return false;
                if (temp.equals("A")) return true;
                rules = workflowsMap.get(temp);
            }
            isCondition = false;

        }
    }

    private static boolean evaluateCondition(int rMapVal, String operator, int number) {
        if (operator.equals(">")) {
            return rMapVal > number;
        }
        if (operator.equals("<")) {
            return rMapVal < number;
        }

        return false;
    }

    private static int sumRatings(String r) {
        String[] split = r.substring(1, r.length() - 1).split(",");
        return Arrays.stream(split)
                .map(item -> Integer.parseInt(item.substring(2)))
                .reduce(0, Integer::sum);
    }
}
