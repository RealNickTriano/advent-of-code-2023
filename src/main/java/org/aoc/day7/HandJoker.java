package org.aoc.day7;

import java.util.Arrays;

public class HandJoker extends Hand {

    public HandJoker(String hand, int bet) {
        this.cards = hand.toCharArray();
        this.bet = bet;
        this.type = getTypeOfHand(hand);
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

    // Have to change values J is now 0
    public static int getCardNum(char c) {
        int value = 0;
        switch (c) {
            case 'T':
                value = 9;
                break;
            case 'J':
                value = 0;
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
                value = Character.getNumericValue(c) - 1;
                break;
        }
        return value;
    }

    // Make type an enum?
    // 0 - 7 where 7 is highest value type
    // I have no clue how to deal with multiple J cards
    private Type getTypeOfHand(String cards) {
        int[] cardValue = new int[13];
        for (int i = 0; i < cards.length(); i++) {
            int index = getCardNum(cards.charAt(i));
            cardValue[index]++;
        }
        //System.out.println(Arrays.toString(cardValue));
        /*
            Five of a kind
                iterate, look for count of 5, anything > 0 and < 5 break
                4 and 1 j? 3 and 2 j? 2 and 3 j? and so on...
                check for how many J? then update conditions based on J?
                so look for count of 5 OR count of 5 - J!
            Four of a kind
                iterate, look for count of 4
                OR count of 4 - J
            Full house
                iterate, look for count 3 and 2
                OR count 3 - J and 2
                OR count 3 and 2 - J
            Three of a kind
                iterate, look for count 3
                OR count 3 - J
            Two pair
                iterate, look for count 2 and 2
                OR 2 - J and 2
                OR 2 and 2 - J
            One pair
                iterate, look for count 2
                OR 2 - J
            High card
                otherwise return this
         */
        int jokers = cardValue[0];
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 5
                    || cardValue[i] + jokers == 5) {
                System.out.println("Five of a kind: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.FIVE;
            }
        }
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 4
                    || cardValue[i] + jokers == 4) {
                System.out.println("Four of a kind: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.FOUR;
            }
        }
        boolean three = false;
        boolean two = false;
        int fcurJokers = jokers;
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 3 || cardValue[i] + fcurJokers == 3){
                three = true;
                fcurJokers = 0;

            } else if (cardValue[i] == 2) two = true;

            if (two && three) {
                System.out.println("full house: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.FULL;
            }
        }
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 3 || cardValue[i] + jokers == 3) {
                System.out.println("Three of a kind: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.THREE;
            }
        }
        int twoCount = 0;
        int curJokers = jokers;
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 2 || cardValue[i] + curJokers == 2) {
                twoCount++;
                curJokers = 0;
            }
            if (twoCount == 2) {
                System.out.println("Two pair: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.TWO;
            }
        }
        for (int i = 1; i < cardValue.length; i++) {
            if (cardValue[i] == 2 || cardValue[i] + jokers == 2) {
                System.out.println("One pair: "
                        + Arrays.toString(cardValue) + " Cards: "  + cards);
                return Type.ONE;
            }
        }
        System.out.println("High card: "
                + Arrays.toString(cardValue) + " Cards: "  + cards);
        return Type.HIGH;
    }
}
