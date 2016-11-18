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


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 14:10
 */
public class CardDeckTest {
    private static final int RANDOM_SEED = 8;

    private CardDeck cardDeck;

    @Before
    public void setUp() throws Exception {
        cardDeck = new CardDeck(RANDOM_SEED);
    }

    @Test
    public void shouldGetSpadesQueenCardWhenCardDeckGetCard() throws Exception {
        assertEquals(new Card(CardSuit.SPADES, CardValue.QUEEN), cardDeck.getCard());
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionExceptionWhenGetting53Card() throws Exception {
        for (int i = 0; i < 53; i++) {
            cardDeck.getCard();
        }
    }

    @Test
    public void shouldNoThrowIllegalArgumentExceptionExceptionWhenGetting52Card() throws Exception {
        for (int i = 0; i < 52; i++) {
            cardDeck.getCard();
        }
    }
}
