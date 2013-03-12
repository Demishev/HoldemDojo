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
    private final List<Card> cards = new ArrayList<>();
    private final Random random;

    CardDeck(int randomSeed) {
        random = new Random(randomSeed);
        generateCards();
    }

    private void generateCards() {
        for (CardValue cardValue : CardValue.values()) {
            for (CardSuit cardSuit : CardSuit.values()) {
                cards.add(new Card(cardSuit, cardValue));
            }
        }
    }

    public CardDeck() {
        random = new Random();
        generateCards();
    }

    public Card getCard() {
        Card card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }
}
