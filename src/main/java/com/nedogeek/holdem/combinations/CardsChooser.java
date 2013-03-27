package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;

import java.util.Iterator;

/**
 * User: Konstantin Demishev
 * Date: 05.12.12
 * Time: 20:46
 */
class CardsChooser implements Iterator<Card[]>, Iterable<Card[]> {
    private final Card[] cards;

    private boolean hasNext = true;
    private final int[] cardsToRemove;

    public CardsChooser(Card[] cards) {
        this.cards = cards;

        if (cards.length == 6) {
            cardsToRemove = new int[]{0};
        } else {
            cardsToRemove = new int[]{0, 1};
        }

    }


    public boolean hasNext() {
        return hasNext;
    }

    public Card[] next() {
        Card[] resultingCardArray = copyCardArray();

        changeCounter();
        return resultingCardArray;
    }

    private Card[] copyCardArray() {
        Card[] resultingCardArray = new Card[5];
        int cardNumber = 0;
        for (int i = 0; i < cards.length; i++) {
            if (canBeCopied(i)) {
                resultingCardArray[cardNumber] = cards[i];
                cardNumber++;
            }
        }
        return resultingCardArray;
    }

    private boolean canBeCopied(int cardNumber) {
        for (int cardToRemove : cardsToRemove) {
            if (cardNumber == cardToRemove) {
                return false;
            }
        }
        return true;
    }

    private void changeCounter() {
        if (cards.length == 6) {
            if (cardsToRemove[0] < cards.length - 1) {
                cardsToRemove[0]++;
            } else {
                hasNext = false;
            }
        } else {
            if (cardsToRemove[1] < cards.length - 1) {
                cardsToRemove[1]++;
            } else {
                if (cardsToRemove[0] < cards.length - 2) {
                    cardsToRemove[1] = ++cardsToRemove[0] + 1;
                } else {
                    hasNext = false;
                }
            }
        }
    }

    public void remove() {

    }

    public Iterator<Card[]> iterator() {
        return this;
    }
}
