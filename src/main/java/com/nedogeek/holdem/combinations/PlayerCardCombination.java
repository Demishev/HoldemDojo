package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Arrays;

/**
 * User: Konstantin Demishev
 * Date: 27.11.12
 * Time: 14:23
 */
public class PlayerCardCombination implements Comparable<PlayerCardCombination> {
    private final Card[] cards;


    public PlayerCardCombination(Card... cards) {
        if (cards.length > 5) {
            this.cards = bestFromCards(new CardsChooser(cards));
        } else {
            this.cards = cards;
        }
    }


    private Card[] bestFromCards(Iterable<Card[]> cardsChooser) {
        Card[] bestCards = null;

        for (Card[] currentCards : cardsChooser) {
            if (bestCards == null) {
                bestCards = currentCards;
            } else {
                Combination currentCombinationType = Combination.getCombinationType(currentCards);
                Card[] currentCombinationCards = Combination.getCombinationCards(currentCards);

                Combination bestCombinationType = Combination.getCombinationType(bestCards);
                Card[] bestCombinationCards = Combination.getCombinationCards(bestCards);

                if (compareCombinations(currentCombinationType, bestCombinationType,
                        currentCombinationCards, bestCombinationCards) > 0) {
                    bestCards = currentCards;
                }
            }
        }
        return bestCards;
    }

    public String getCombination() {
        return Combination.cardsCombination(cards);
    }

    public Combination getCombinationType() {
        return Combination.getCombinationType(cards);
    }

    Card[] getCombinationCards() {
        return Combination.getCombinationCards(cards);
    }

    @Override
    public String toString() {
        return getCombination() + ": " + Arrays.asList(cards);
    }


    public int compareTo(PlayerCardCombination o) {
        final Combination firstCombinationType = getCombinationType();
        final Combination secondCombinationType = o.getCombinationType();
        final Card[] firstCombinationCards = getCombinationCards();
        final Card[] secondCombinationCards = o.getCombinationCards();

        return compareCombinations(
                firstCombinationType, secondCombinationType, firstCombinationCards, secondCombinationCards);
    }

    private int compareCombinations(Combination firstCombinationType, Combination secondCombinationType, Card[] firstCombinationCards, Card[] secondCombinationCards) {
        if (firstCombinationType != secondCombinationType) {
            return -1 * firstCombinationType.compareTo(secondCombinationType);
        } else {
            for (int i = 0; i < firstCombinationCards.length; i++) {
                int firstCardRelatedToSecond = firstCombinationCards[i].compareTo(secondCombinationCards[i]);
                if (firstCardRelatedToSecond != 0) {
                    return firstCardRelatedToSecond;
                }
            }
        }
        return 0;
    }
}
