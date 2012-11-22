package com.nedogeek.holdem.gamingStuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 14:08
 */
public class CardDeck {
    private final List<Card> cards;
    private final Random random;

    CardDeck(int randomSeed) {
        random = new Random(randomSeed);
        cards = new ArrayList<Card>();

        for (CardValue cardValue : CardValue.values()) {
            for (CardSuit cardSuit : CardSuit.values()) {
                cards.add(new Card(cardSuit, cardValue));
            }
        }
    }

    public Card getCard() {
        Card card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }
}
