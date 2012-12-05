package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Arrays;

/**
 * User: Konstantin Demishev
 * Date: 27.11.12
 * Time: 14:23
 */
public class PlayerCardCombination implements Comparable<PlayerCardCombination>{
    private Card[] cards;

    public PlayerCardCombination(Card... cards) {
        this.cards = cards;
    }

    public String getCombination() {
        return Combination.cardsCombination(cards);
    }

    public Combination getCombinationType() {
        return Combination.getCombinationType(cards);
    }

    public Card[] getCombinationCards() {
        return Combination.getCombinationCards(cards);
    }

    @Override
    public String toString() {
        return getCombination() + ": " + Arrays.asList(cards);
    }

    @Override
    public int compareTo(PlayerCardCombination o) {
        final Combination firstCombinationType = getCombinationType();
        final Combination secondCombinationType = o.getCombinationType();
        final Card[] firstCombinationCards = getCombinationCards();
        final Card[] secondCombinationCards = o.getCombinationCards();

        if (firstCombinationType != secondCombinationType) {
            return -1*firstCombinationType.compareTo(secondCombinationType);
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
