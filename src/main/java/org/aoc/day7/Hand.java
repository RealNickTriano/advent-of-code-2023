package org.aoc.day7;

import java.util.Arrays;
import java.util.List;

public class Hand implements Comparable<Hand>{
    public char[] cards;
    public int bet;
    public String type;

    public Hand(String hand, int bet) {
        this.cards = hand.toCharArray();
        this.bet = bet;
        this.type = getTypeOfHand(hand);
    }

    public String toString() {
        return "Cards: " + Arrays.toString(cards) + "\tBet: "  + bet;
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

    @Override
    public int compareTo(Hand otherHand) {
        for (int i = 0; i < this.cards.length; i++) {
            if (this.cards[i] != otherHand.cards[i]) {
                if (getCardNum(this.cards[i]) < getCardNum(otherHand.cards[i]))
                    return -1;
                else
                    return 1;
            }
        }
        return 0;
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
