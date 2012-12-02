package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 22:56
 */
public enum Combinations {
    KICKER ("with kicker %s"),
    TWO_KICKERS ("with kickers %s and %s"),
    THREE_KICKERS ("with kickers: %s, %s and %s"),
    FOUR_KICKERS ("with kickers: %s, %s, %s and %s"),
    HIGH_CARD ("High card %s"),
    PAIR ("Pair of %s"),
    TWO_PAIRS ("Pairs of %s and %s"),
    SET ("Set of %s"),
    STRAIGHT ("Straight on %s"),
    FLASH ("Flash on %s with %s, %s, %s and %s"),
    FULL_HOUSE ("Full house on %s and %s"),
    FOUR_OF_KIND ("Four of %s"),
    STRAIGHT_FLASH ("Straight flash on %s"),
    ROYAL_FLASH ("Royal flash");

    private final String combinationMessage;

    Combinations(String combinationMessage) {
        this.combinationMessage = combinationMessage;
    }

    public String generateMessage(Card card) {
        return String.format(combinationMessage, card.getCardValueName());
    }

    public String generateMessage(Card[] cards) {
        String[] cardNames = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardNames[i] = cards[i].getCardValueName();
        }

        return String.format(combinationMessage,(Object[]) cardNames);
    }
}
