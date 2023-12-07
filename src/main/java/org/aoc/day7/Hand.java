package org.aoc.day7;

import java.util.Arrays;
import java.util.List;

public class Hand implements Comparable<Hand>{
    public Type type;
    public char[] cards;
    public int bet;
    public enum Type {
        HIGH,
        ONE,
        TWO,
        THREE,
        FULL,
        FOUR,
        FIVE
    }

    public Hand() {

    }
    public Hand(String hand, int bet) {
        this.cards = hand.toCharArray();
        this.bet = bet;
        this.type = getTypeOfHand(hand);
    }

    public String toString() {
        return "Cards: " + Arrays.toString(cards)
                + " Bet: "  + bet
                + " Type: " + this.type;
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
        if (this.type.ordinal() < otherHand.type.ordinal()) return -1;
        if (this.type.ordinal() > otherHand.type.ordinal()) return 1;

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

    private static Type getTypeOfHand(String cards) {
        int[] cardValue = new int[13];
        for (int i = 0; i < cards.length(); i++) {
            int index = getCardNum(cards.charAt(i));
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
            if (value == 5) return Type.FIVE;
        }
        for (int value : cardValue) {
            if (value == 4) return Type.FOUR;
        }
        boolean three = false;
        boolean two = false;
        for (int value : cardValue) {
            if (value == 3) three = true;
            if (value == 2) two = true;

            if (two && three) return Type.FULL;
        }
        for (int value : cardValue) {
            if (value == 3) return Type.THREE;
        }
        int twoCount = 0;
        for (int value : cardValue) {
            if (value == 2) twoCount++;
            if (twoCount == 2) return Type.TWO;
        }
        for (int value : cardValue) {
            if (value == 2) return Type.ONE;
        }
        return Type.HIGH;
    }
}
