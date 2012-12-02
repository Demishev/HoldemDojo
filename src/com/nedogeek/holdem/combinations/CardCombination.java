package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Arrays;

/**
 * User: Konstantin Demishev
 * Date: 27.11.12
 * Time: 14:23
 */
public class CardCombination implements Comparable<CardCombination>{
    private Card[] cards;

    public CardCombination(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public int compareTo(CardCombination o) {
        return 0;
    }

    public String getCombination() {
        if (cards[0].compareTo(cards[1]) == 0) {
            return String.format(Combinations.PAIR.toString(), cards[0].getCardValueName());
        }

        int kickerNumber = (cards[0].compareTo(cards[1]) > 0) ? 1 : 0;

        return String.format("High card Ace with kicker %s.", cards[kickerNumber].getCardValueName());
    }

    @Override
    public String toString() {
        return getCombination() + ": " + Arrays.asList(cards);
    }
}
