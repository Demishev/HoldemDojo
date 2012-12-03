package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 22:56
 */
public enum Combinations {
    HIGH_CARD_TWO_CARDS("High card %s with %s"),
    PAIR_TWO_CARDS("Pair of %s"),
    HIGH_CARD("High card %s with %s, %s, %s and %s"),
    PAIR("Pair of %s with %s, %s and %s"),
    TWO_PAIRS("Pairs of %s with %s"),
    SET("Set of %s with %s and %s"),
    STRAIGHT("Straight on %s"),
    FLASH("Flash on %s with %s, %s, %s and %s"),
    FULL_HOUSE("Full house on %s and %s"),
    FOUR_OF_KIND("Four of %s"),
    STRAIGHT_FLASH("Straight flash on %s"),
    ROYAL_FLASH("Royal flash");

    private final String combinationMessage;

    Combinations(String combinationMessage) {
        this.combinationMessage = combinationMessage;
    }

    public String generateMessage(Card... cards) {
        String[] cardNames = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardNames[i] = cards[i].getCardValueName();
        }

        return String.format(combinationMessage, (Object[]) cardNames);
    }
}
