package com.nedogeek.holdem.combinations;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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
