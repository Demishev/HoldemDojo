package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Arrays;

/**
 * User: Konstantin Demishev
 * Date: 27.11.12
 * Time: 14:23
 */
public class PlayerCardCombination implements Comparable<PlayerCardCombination> {
    private Card[] cards;

    public PlayerCardCombination(Card... cards) {
        this.cards = cards;
    }

    @Override
    public int compareTo(PlayerCardCombination o) {
        return 0;
    }

    public String getCombination() {
        return Combination.cardsCombination(cards);
    }

    @Override
    public String toString() {
        return getCombination() + ": " + Arrays.asList(cards);
    }
}
