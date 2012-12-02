package com.nedogeek.holdem.combinations;

/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 22:56
 */
public enum Combinations {
    KICKER ("with kicker %s"),
    HIGH_CARD ("High card %s"),
    PAIR ("Pair of %s"),
    TWO_PAIRS ("Pairs of %s and %s"),
    SET ("Set of %s"),
    STRAIGHT ("Straight on %s"),
    FLASH ("Flash on %s"),
    FULL_HOUSE ("Full house on %s and %s"),
    FOUR_OF_KIND ("Four of %s"),
    STRAIGHT_FLASH ("Straight flash on %s"),
    ROYAL_FLASH ("Royal flash");

    private final String combinationMessage;

    Combinations(String combinationMessage) {
        this.combinationMessage = combinationMessage;
    }

    @Override
    public String toString() {
        return combinationMessage;
    }
}
