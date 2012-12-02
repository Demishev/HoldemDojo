package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

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
        int kickerNumber = (cards[0].compareTo(cards[1]) > 0) ? 1 : 0;

        return String.format("High card Ace with kicker %s.", cards[kickerNumber].getCardValueName());
    }
}
