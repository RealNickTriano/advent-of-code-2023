package org.aoc.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayOne {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/main/resources/inputs/day1.txt");

        Scanner scanner = new Scanner(file);
        List<String> data = new ArrayList<>();
        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }
        scanner.close();

        System.out.println(day1SolutionPartTwo(data));

    }

    public static int day1Solution(List<String> input) {
        int result = 0;

        // String[] lines = input.split("\n");
        // System.out.println(lines[0]);

        for (String in: input) {
            char first = 'a';
            char second = 'a';
            for (int i = 0; i < in.length(); i++) {
                char c = in.charAt(i);
                if (Character.isDigit(c)) {
                    first = c;
                    System.out.println("first " + first);
                    break;
                }
            }
            for (int j = in.length() - 1; j >= 0; j--) {
                char c = in.charAt(j);
                if (Character.isDigit(c)) {
                    second = c;
                    System.out.println("second " + second);
                    break;
                }
            }

            int total = Character.getNumericValue(first) * 10
                    + Character.getNumericValue(second);
            System.out.println("total " + total);
            result += total;
        }

        return result;

    }

    public static int day1SolutionPartTwo(List<String> input) {
        int result = 0;
        Map<String, Integer> numMap = new HashMap<>();
        numMap.put("one", 1);
        numMap.put("two", 2);
        numMap.put("three", 3);
        numMap.put("four", 4);
        numMap.put("five", 5);
        numMap.put("six", 6);
        numMap.put("seven", 7);
        numMap.put("eight", 8);
        numMap.put("nine", 9);

        // String[] lines = input.split("\n");
        // System.out.println(lines[0]);

        for (String in: input) {
            int first = 0;
            int second = 0;
            for (int i = 0; i < in.length(); i++) {
                char c = in.charAt(i);
                if (Character.isDigit(c)) {
                    first = Character.getNumericValue(c);

                    break;
                } else {
                    if (i + 3 < in.length()) {
                        int temp = checkSubstring(in.substring(i, i + 3), numMap);
                        if (temp != -1) {
                            first = temp;
                            break;
                        }
                    }
                    if (i + 4 < in.length()) {
                        int temp = checkSubstring(in.substring(i, i + 4), numMap);
                        if (temp != -1) {
                            first = temp;
                            break;
                        }
                    }
                    if (i + 5 < in.length()) {
                        int temp = checkSubstring(in.substring(i, i + 5), numMap);
                        if (temp != -1) {
                            first = temp;
                            break;
                        }
                    }
                }
            }
            System.out.println("first " + first);
            for (int j = in.length() - 1; j >= 0; j--) {
                char c = in.charAt(j);
                if (Character.isDigit(c)) {
                    second = Character.getNumericValue(c);
                    break;
                } else {
                    if (j - 2 >= 0) {
                        System.out.println(in.substring(j - 2, j + 1));
                        int temp = checkSubstring(in.substring(j - 2, j + 1), numMap);
                        if (temp != -1) {
                            second = temp;
                            break;
                        }
                    }
                    if (j - 3 >= 0) {
                        System.out.println(in.substring(j - 3, j + 1));
                        int temp = checkSubstring(in.substring(j - 3, j + 1), numMap);
                        if (temp != -1) {
                            second = temp;
                            break;
                        }
                    }
                    if (j - 4 >= 0) {
                        System.out.println(in.substring(j - 4, j + 1));
                        int temp = checkSubstring(in.substring(j - 4, j + 1), numMap);
                        if (temp != -1) {
                            second = temp;
                            break;
                        }
                    }
                }

            }
            System.out.println("second " + second);

            int total = first * 10
                    + second;
            result += total;
        }

        return result;

    }

    public static int checkSubstring(String str, Map<String, Integer> numMap) {
        if (numMap.get(str) ==  null) {
            return -1;
        } else {
            return numMap.get(str);
        }
    }
}
