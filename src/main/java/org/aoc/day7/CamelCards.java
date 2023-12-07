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
        List<List<String>> hands = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .toList();

        System.out.println(hands);

        Map<String, List<List<String>>> types = new HashMap<>();
        types.put("High card", new ArrayList<>());
        types.put("One pair", new ArrayList<>());
        types.put("Two pair", new ArrayList<>());
        types.put("Three of a kind", new ArrayList<>());
        types.put("Full house", new ArrayList<>());
        types.put("Four of a kind", new ArrayList<>());
        types.put("Five of a kind", new ArrayList<>());

        Map<String, List<Hand>> typesHands = new HashMap<>();
        types.put("High card", new ArrayList<>());
        types.put("One pair", new ArrayList<>());
        types.put("Two pair", new ArrayList<>());
        types.put("Three of a kind", new ArrayList<>());
        types.put("Full house", new ArrayList<>());
        types.put("Four of a kind", new ArrayList<>());
        types.put("Five of a kind", new ArrayList<>());

        for (List<String> hand : hands) {
            String cards = hand.get(0);
            String bet = hand.get(1);

            String handType = getTypeOfHand(cards);

            List<List<String>> newList = types.get(handType);
            newList.add(hand);
            types.put(handType, newList);
        }
        System.out.println(types);
        List<String> handTypes = Arrays.asList("High card", "One pair",
                "Two pair", "Three of a kind", "Full house", "Four of a kind", "Five of a kind");

        // map to hand objects

        int result = 0;
        int rank = 0;
        for (String key : handTypes) {
            List<Hand> handsForType = new ArrayList<>(types.get(key).stream()
                    .map(item -> new Hand(item.get(0), Integer.parseInt(item.get(1)))).toList());
            handsForType.sort(null);

            for (Hand h : handsForType) {
                rank++;
                result += h.bet * rank;
            }
        }
        System.out.println(typesHands);
        return result;
    }
    public static long partTwo(String data) {
        List<String> lines = Arrays.asList(data.split(System.lineSeparator()));
        List<List<String>> hands = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .toList();

        System.out.println(hands);

        List<HandJoker> myHands = hands.stream().map(item -> new HandJoker(item.get(0), Integer.parseInt(item.get(1)))).toList();
        System.out.println(myHands);
        Map<String, List<HandJoker>> groups = new HashMap<>();
        for (HandJoker h : myHands) {
            if (groups.get(h.type) != null) {
                List<HandJoker> newList = groups.get(h.type);
                newList.add(h);
                groups.put(h.type, newList);
            } else {
                List<HandJoker> newList = new ArrayList<>();
                newList.add(h);
                groups.put(h.type, newList);
            }
        }
        System.out.println(groups);
        List<String> handTypes = Arrays.asList("High card", "One pair",
                "Two pair", "Three of a kind", "Full house", "Four of a kind", "Five of a kind");
        int rank = 0;
        int result = 0;
        for (String key : handTypes) {
            if (groups.get(key) != null) {
                List<HandJoker> handsForType = groups.get(key);
                handsForType.sort(null);
                System.out.println("Sorted: " + handsForType);
                for (HandJoker h : handsForType) {
                    ++rank;
                    result += (h.bet * rank);
                    System.out.println("Rank: " + rank + " Bet:  " + h.bet);
                }
            }
        }
        return result;
    }
    public static int getCardNum(char c) {
        int value = 0;
        switch (c) {
            case 'T':
                value = 8;
                break;
            case 'J':
                value = 9;
                break;
            case 'Q':
                value = 10;
                break;
            case 'K':
                value = 11;
                break;
            case 'A':
                value = 12;
                break;
            default:
                value = Character.getNumericValue(c) - 2;
                break;
        }
        return value;
    }
    private static String getTypeOfHand(String cards) {
        int[] cardValue = new int[13];
        for (int i = 0; i < cards.length(); i++) {
            int index = 0;
            switch (cards.charAt(i)) {
                case 'T':
                    index = 8;
                    break;
                case 'J':
                    index = 9;
                    break;
                case 'Q':
                    index = 10;
                    break;
                case 'K':
                    index = 11;
                    break;
                case 'A':
                    index = 12;
                    break;
                default:
                    index = Character.getNumericValue(cards.charAt(i)) - 2;
                    break;
            }
            cardValue[index]++;
        }
        System.out.println(Arrays.toString(cardValue));

        /*
            Five of a kind
                iterate, look for count of 5, anything > 0 and < 5 break
            Four of a kind
                iterate, look for count of 4
            Full house
                iterate, look for count 3 and 2
            Three of a kind
                iterate, look for count 3
            Two pair
                iterate, look for count 2 and 2
            One pair
                iterate, look for count 2
            High card
                otherwise return this
         */

        for (int value : cardValue) {
            if (value == 5) return "Five of a kind";
        }
        for (int value : cardValue) {
            if (value == 4) return "Four of a kind";
        }
        boolean three = false;
        boolean two = false;
        for (int value : cardValue) {
            if (value == 3) three = true;
            if (value == 2) two = true;

            if (two && three) return "Full house";
        }
        for (int value : cardValue) {
            if (value == 3) return "Three of a kind";
        }
        int twoCount = 0;
        for (int value : cardValue) {
            if (value == 2) twoCount++;
            if (twoCount == 2) return "Two pair";
        }
        for (int value : cardValue) {
            if (value == 2) return "One pair";
        }
        return "High card";
    }
}
