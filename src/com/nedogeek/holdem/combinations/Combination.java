package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardValue;


/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 22:56
 */
enum Combination {
    ROYAL_FLASH("Royal flash", 5),
    STRAIGHT_FLASH("Straight flash on %s", 5),
    SMALLER_STRAIGHT_FLUSH("Straight flash on 5", 5),
    FOUR_OF_KIND("Four of %s with %s", 5),
    FULL_HOUSE("Full house on %s and %s", 5),
    FLASH("Flash on %s with %s, %s, %s and %s", 5),
    STRAIGHT("Straight on %s", 5),
    SMALLER_STRAIGHT("Straight on 5", 5),
    SET("Set of %s with %s and %s", 5),
    TWO_PAIRS("Two pairs of %s and %s with %s", 5),
    PAIR("Pair of %s with %s, %s and %s", 5),
    HIGH_CARD("High card %s with %s, %s, %s and %s", 5),
    PAIR_TWO_CARDS("Pair of %s", 2),

    HIGH_CARD_TWO_CARDS("High card %s with %s", 2);
    private final String combinationMessage;
    private final int combinationType;

    private Combination(String combinationMessage, int combinationType) {
        this.combinationMessage = combinationMessage;
        this.combinationType = combinationType;
    }

    static Combination getCombinationType(Card[] cards) {
        for (Combination combination : Combination.values()) {
            if (cards.length == combination.combinationType && hasCombination(cards, combination)) {
                return combination;
            }
        }
        return null;
    }

    static String cardsCombination(Card[] cards) {
        Combination combination = getCombinationType(cards);
        Card[] combinationCards = defineCombinationCards(cards, combination);
        return combination.generateMessage(combinationCards);
    }

    private String generateMessage(Card... cards) {
        String[] cardNames = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardNames[i] = cards[i].getCardValueName();
        }
        return String.format(combinationMessage, (Object[]) cardNames);
    }

    private static void downSort(Card[] cards) {
        for (int n = 0; n < cards.length; n++) {
            for (int i = 0; i < cards.length - n - 1; i++) {
                if (cards[i].compareTo(cards[i + 1]) < 0) {
                    Card tempCard = cards[i];
                    cards[i] = cards[i + 1];
                    cards[i + 1] = tempCard;
                }
            }
        }
    }

    private static boolean hasCombination(Card[] cards, Combination combination) {
        downSort(cards);
        switch (combination) {
            case HIGH_CARD_TWO_CARDS:
                return true;
            case PAIR_TWO_CARDS:
                return hasPairTwoCardsCombination(cards);
            case HIGH_CARD:
                return true;
            case PAIR:
                return hasPair(cards);
            case TWO_PAIRS:
                return hasTwoPairs(cards);
            case SET:
                return hasSet(cards);
            case STRAIGHT:
                return hasStraight(cards);
            case SMALLER_STRAIGHT:
                return hasSmallerStraight(cards);
            case FLASH:
                return hasFlash(cards);
            case FULL_HOUSE:
                return hasFullHouse(cards);
            case FOUR_OF_KIND:
                return hasFourOfKind(cards);
            case SMALLER_STRAIGHT_FLUSH:
                return hasSmallerStraightFlash(cards);
            case STRAIGHT_FLASH:
                return hasStraightFlash(cards);
            case ROYAL_FLASH:
                return hasRoyalFlash(cards);
        }
        return false;
    }

    private static boolean hasSmallerStraightFlash(Card[] cards) {
        return hasFlash(cards) && hasSmallerStraight(cards);
    }

    private static boolean hasSmallerStraight(Card[] cards) {
        return (cards[0].getCardValue() == CardValue.ACE) &&
                (cards[1].getCardValue() == CardValue.FIVE) &&
                (cards[2].getCardValue() == CardValue.FOUR) &&
                (cards[3].getCardValue() == CardValue.THREE) &&
                (cards[4].getCardValue() == CardValue.TWO);
    }

    private static Card[] defineCombinationCards(Card[] cards, Combination combination) {
        switch (combination) {
            case HIGH_CARD_TWO_CARDS:
                return DefineHighCardTwoCardsCards(cards);
            case PAIR_TWO_CARDS:
                return definePairTwoCardsCards(cards);
            case HIGH_CARD:
                return defineHighCardCards(cards);
            case PAIR:
                return definePairsCards(cards);
            case TWO_PAIRS:
                return defineTwoPairsCards(cards);
            case SET:
                return defineSetCards(cards);
            case STRAIGHT:
                return defineStraightCards(cards);
            case FLASH:
                return defineFlashCards(cards);
            case FULL_HOUSE:
                return defineFullHouseCards(cards);
            case FOUR_OF_KIND:
                return defineFourOfKindCards(cards);
            case STRAIGHT_FLASH:
                return defineStraightFlashCards(cards);
            case ROYAL_FLASH:
                return defineRoyalFlashCards();
        }
        return new Card[0];
    }

    private static Card[] definePairTwoCardsCards(Card[] cards) {
        return new Card[]{cards[0]};
    }

    private static Card[] DefineHighCardTwoCardsCards(Card[] cards) {
        return cards;
    }

    private static Card[] defineHighCardCards(Card[] cards) {
        return cards;
    }

    private static Card[] defineFlashCards(Card[] cards) {
        return cards;
    }

    private static Card[] defineStraightCards(Card[] cards) {
        return new Card[]{cards[0]};
    }

    private static Card[] defineFourOfKindCards(Card[] cards) {
        return (sameCardValues(cards, 0, 1)) ?
                new Card[]{cards[0], cards[4]} :
                new Card[]{cards[4], cards[0]};
    }

    private static Card[] defineStraightFlashCards(Card[] cards) {
        return new Card[]{cards[0]};
    }

    private static Card[] defineRoyalFlashCards() {
        return new Card[0];
    }

    private static Card[] defineFullHouseCards(Card[] cards) {
        return (sameCardValues(cards, 1, 2)) ? new Card[]{cards[0], cards[4]} :
                new Card[]{cards[4], cards[0]};
    }

    private static Card[] defineSetCards(Card[] cards) {
        Card[] setCards = new Card[3];
        setCards[0] = cards[2];

        if (sameCardValues(cards, 0, 1, 2)) {
            setCards[1] = cards[3];
            setCards[2] = cards[4];
        }

        if (sameCardValues(cards, 1, 2, 3)) {
            setCards[1] = cards[0];
            setCards[2] = cards[4];
        }

        if (sameCardValues(cards, 2, 3, 4)) {
            setCards[1] = cards[0];
            setCards[2] = cards[1];
        }

        return setCards;
    }

    private static Card[] defineTwoPairsCards(Card[] cards) {
        Card[] setCards = new Card[3];
        if (!sameCardValues(cards, 0, 1)) {
            setCards[0] = cards[2];
            setCards[1] = cards[4];
            setCards[2] = cards[0];
        } else if (!sameCardValues(cards, 3, 4)) {
            setCards[0] = cards[0];
            setCards[1] = cards[2];
            setCards[2] = cards[4];
        } else {
            setCards[0] = cards[0];
            setCards[1] = cards[4];
            setCards[2] = cards[2];
        }
        return setCards;
    }


    private static Card[] definePairsCards(Card[] cards) {
        Card[] pairCards = new Card[4];
        for (int i = 0; i < cards.length - 1; i++) {
            if (sameCardValues(cards, i, i + 1)) {
                pairCards[0] = cards[i];
                System.arraycopy(cards, 0, pairCards, 1, i);
                System.arraycopy(cards, i + 2, pairCards, i + 1, 3 - i);
                return pairCards;
            }
        }
        return new Card[0];
    }

    private static boolean hasPairTwoCardsCombination(Card[] cards) {
        return sameCardValues(cards, 0, 1);
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

    static Card[] getCombinationCards(Card[] cards) {
        Combination combination = getCombinationType(cards);
        return defineCombinationCards(cards, combination);
    }
}
