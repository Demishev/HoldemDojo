package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardValue;

/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 22:56
 */
enum Combination {
    HIGH_CARD_TWO_CARDS("High card %s with %s"),
    PAIR_TWO_CARDS("Pair of %s"),
    HIGH_CARD("High card %s with %s, %s, %s and %s"),
    PAIR("Pair of %s with %s, %s and %s"),
    TWO_PAIRS("Two pairs of %s and %s with %s"),
    SET("Set of %s with %s and %s"),
    STRAIGHT("Straight on %s"),
    FLASH("Flash on %s with %s, %s, %s and %s"),
    FULL_HOUSE("Full house on %s and %s"),
    FOUR_OF_KIND("Four of %s"),
    STRAIGHT_FLASH("Straight flash on %s"),
    ROYAL_FLASH("Royal flash");

    private final String combinationMessage;

    Combination(String combinationMessage) {
        this.combinationMessage = combinationMessage;
    }

    public String generateMessage(Card... cards) {
        String[] cardNames = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardNames[i] = cards[i].getCardValueName();
        }
        return String.format(combinationMessage, (Object[]) cardNames);
    }


    static boolean hasCombination(Card[] cards, Combination combination) {
        switch (combination) {
            case HIGH_CARD_TWO_CARDS:
                break;
            case PAIR_TWO_CARDS:
                break;
            case HIGH_CARD:
                break;
            case PAIR:
                return hasPair(cards);
            case TWO_PAIRS:
                return hasTwoPairs(cards);
            case SET:
                return hasSet(cards);
            case STRAIGHT:
                return hasStraight(cards);
            case FLASH:
                return hasFlash(cards);
            case FULL_HOUSE:
                return hasFullHouse(cards);
            case FOUR_OF_KIND:
                return hasFourOfKind(cards);
            case STRAIGHT_FLASH:
                return hasStraightFlash(cards);
            case ROYAL_FLASH:
                return hasRoyalFlash(cards);
        }
        return false;
    }

    private static boolean hasPair(Card[] cards) {
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].sameValue(cards[i + 1])) {
                return true;
            }
        }
        return false;
    }


    private static boolean hasFullHouse(Card[] cards) {
        return sameCardValues(cards, 0, 1) && sameCardValues(cards, 3, 4) &&
                (sameCardValues(cards, 1, 2) || sameCardValues(cards, 2, 3));
    }

    private static boolean hasSet(Card[] cards) {
        return sameCardValues(cards, 0, 1, 2) || sameCardValues(cards, 1, 2, 3) || sameCardValues(cards, 2, 3, 4);
    }

    private static boolean hasFlash(Card[] cards) {
        for (Card card : cards) {
            if (!card.sameSuit(cards[0])) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasTwoPairs(Card[] cards) {
        return sameCardValues(cards, 0, 1) && sameCardValues(cards, 2, 3) ||
                sameCardValues(cards, 1, 2) && sameCardValues(cards, 3, 4) ||
                sameCardValues(cards, 0, 1) && sameCardValues(cards, 3, 4);
    }

    private static boolean hasStraight(Card[] cards) {
        for (int i = 0; i < cards.length - 1; i++) {
            if (!cards[i].isNear(cards[i + 1])) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasStraightFlash(Card[] cards) {
        return hasFlash(cards) && hasStraight(cards);
    }

    private static boolean hasRoyalFlash(Card[] cards) {
        return hasStraightFlash(cards) && cards[0].getCardValue() == CardValue.ACE;
    }

    private static boolean hasFourOfKind(Card[] cards) {
        return sameCardValues(cards, 0, 1, 2, 3) || sameCardValues(cards, 1, 2, 3, 4);
    }

    private static boolean sameCardValues(Card[] cards, int... cardNumbers) {
        CardValue valueNeeded = cards[cardNumbers[0]].getCardValue();
        for (int cardNumber : cardNumbers) {
            if (cards[cardNumber].getCardValue() != valueNeeded) {
                return false;
            }
        }
        return true;
    }
}
