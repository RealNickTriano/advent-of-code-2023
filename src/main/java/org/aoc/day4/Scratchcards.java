package org.aoc.day4;

import org.aoc.utils.ReadFiles;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Scratchcards {
    private static final String INPUT_FILE = "src/main/resources/inputs/day4.txt";
    public static void main(String[] args) {
        String data = "";
        try {
            data = ReadFiles.readFileToString(INPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Part One: " + partOne(data));
        System.out.println("Part Two: " + partTwo(data));
    }

    public static int partOne(String data) {

        // Construct winning numbers
        List<List<Integer>> winningNums = Arrays.stream(data.split("\n"))
                .map(line -> List.of(line.split(":")[1].split("\\|")[0].strip().split(" ")))
                .map(item -> item.stream().filter(x -> !x.isEmpty()).toList())
                .map(item -> item.stream().map(Integer::parseInt).toList())
                .toList();

        // Construct my numbers
        List<List<Integer>> myNums = Arrays.stream(data.split("\n"))
                .map(line -> List.of(line.split(":")[1].split("\\|")[1].strip().split(" ")))
                .map(item -> item.stream().filter(x -> !x.isEmpty()).toList())
                .map(item -> item.stream().map(Integer::parseInt).toList())
                .toList();

        //System.out.println(winningNums);
        //System.out.println(myNums);

        int result = 0;
        for (int i = 0; i < myNums.size(); i++) {
            int winnings = 0;
            for (int j  = 0; j < myNums.get(i).size(); j++) {
                if ((winningNums.get(i).contains(myNums.get(i).get(j)))) {
                    if (winnings > 0) winnings *= 2;
                    else winnings = 1;
                }
            }
            result += winnings;
            //System.out.println("Card: " + i + " winnings: " +  winnings);

        }
        return result;
    }

    public static int partTwo(String data) {

        // Construct winning numbers
        List<List<Integer>> winningNums = Arrays.stream(data.split("\n"))
                .map(line -> List.of(line.split(":")[1].split("\\|")[0].strip().split(" ")))
                .map(item -> item.stream().filter(x -> !x.isEmpty()).toList())
                .map(item -> item.stream().map(Integer::parseInt).toList())
                .toList();

        // Construct my numbers
        List<List<Integer>> myNums = Arrays.stream(data.split("\n"))
                .map(line -> List.of(line.split(":")[1].split("\\|")[1].strip().split(" ")))
                .map(item -> item.stream().filter(x -> !x.isEmpty()).toList())
                .map(item -> item.stream().map(Integer::parseInt).toList())
                .toList();

        //System.out.println(winningNums);
        //System.out.println(myNums);

        int[] numCopies = new int[myNums.size()];
        Arrays.fill(numCopies, 1);

        int result = 0;
        for (int i = 0; i < myNums.size(); i++) {
            int winnings = 0;
            int matches = 0;
            for (int j  = 0; j < myNums.get(i).size(); j++) {
                if ((winningNums.get(i).contains(myNums.get(i).get(j)))) {
                    matches++;
                    if (winnings > 0) winnings *= 2;
                    else winnings = 1;
                }
            }
            result += winnings * numCopies[i];
            //System.out.println("Card: " + i + " winnings: " +  winnings * numCopies[i]);

            // Set copies won
            for (int k = 1; k < matches + 1; k++) {
                numCopies[i + k] += numCopies[i];
            }
            //System.out.println(Arrays.toString(numCopies));
        }
        return Arrays.stream(numCopies).sum();
    }
}
