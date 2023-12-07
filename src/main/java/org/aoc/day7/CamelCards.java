package org.aoc.day7;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.*;

public class CamelCards {
    public static final String INPUT_FILE = "src/main/resources/inputs/day7.txt";
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

    public static long partOne(String data) {
        List<String> lines = Arrays.asList(data.split(System.lineSeparator()));
        List<Hand> hands = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .map(hand -> new Hand(hand.get(0), Integer.parseInt(hand.get(1))))
                .sorted()
                .toList();

        System.out.println(hands);

        int result = 0;
        int rank = 0;
        for (Hand h: hands) {
            result += h.bet * ++rank;
        }

        return result;
    }
    public static long partTwo(String data) {
        List<String> lines = Arrays.asList(data.split(System.lineSeparator()));
        List<HandJoker> hands = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .map(hand -> new HandJoker(hand.get(0), Integer.parseInt(hand.get(1))))
                .sorted()
                .toList();

        for (HandJoker h: hands) {
            System.out.println(h);
        }

        int result = 0;
        int rank = 0;
        for (HandJoker h: hands) {
            result += h.bet * ++rank;
        }

        return result;
    }
}
