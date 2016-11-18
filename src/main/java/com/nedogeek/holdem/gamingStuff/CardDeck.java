package com.nedogeek.holdem.gamingStuff;

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
