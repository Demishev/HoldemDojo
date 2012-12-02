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

    public PlayerCardCombination(Card[] cards) {
        this.cards = cards;
        downSort(cards);
    }

    private void downSort(Card[] cards) {
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

    @Override
    public int compareTo(PlayerCardCombination o) {
        return 0;
    }

    public String getCombination() {
        switch (cards.length) {
            case 2:
                return twoCardsCombination();
            case 5:
                return fiveCardsCombination();
            default:
                return null;
        }
    }

    private String fiveCardsCombination() {
        return (hasFlash()) ? Combinations.FLASH.generateMessage(cards) :
                Combinations.HIGH_CARD.generateMessage(cards[0]) + " " +
                        Combinations.FOUR_KICKERS.generateMessage(Arrays.copyOfRange(cards, 1, cards.length));

    }

    private boolean hasFlash() {
        boolean hasFlash = true;
        for (Card card:cards) {
            if (!card.sameSuit(cards[0])){
                hasFlash = false;
            }
        }
        return hasFlash;
    }

    private String twoCardsCombination() {
        if (cards[0].compareTo(cards[1]) == 0) {
            return Combinations.PAIR.generateMessage(cards[0]);
        }

        return Combinations.HIGH_CARD.generateMessage(cards[0]) + " " +
                Combinations.KICKER.generateMessage(cards[1]);
    }

    @Override
    public String toString() {
        return getCombination() + ": " + Arrays.asList(cards);
    }
}
