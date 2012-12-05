package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Iterator;

/**
 * User: Konstantin Demishev
 * Date: 05.12.12
 * Time: 20:46
 */
public class FiveFromSixCardsIterable implements Iterator<Card[]>, Iterable<Card[]> {
    private final Card[] cards;

    private boolean hasNext = true;
    private int cardToRemove = 0;

    public FiveFromSixCardsIterable(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Card[] next() {
        Card[] resultingCardArray = copyCardArray();

        changeCounter();
        return resultingCardArray;
    }

    private Card[] copyCardArray() {
        Card[] resultingCardArray = new Card[5];
        int cardNumber = 0;
        for (int i = 0; i < cards.length; i++) {
            if (i != cardToRemove) {
                resultingCardArray[cardNumber] = cards[i];
                cardNumber++;
            }
        }
        return resultingCardArray;
    }

    private void changeCounter() {
        if (cardToRemove < cards.length - 1) {
            cardToRemove++;
        } else {
            hasNext = false;
        }
    }

    @Override
    public void remove() {

    }

    @Override
    public Iterator<Card[]> iterator() {
        return this;
    }
}
