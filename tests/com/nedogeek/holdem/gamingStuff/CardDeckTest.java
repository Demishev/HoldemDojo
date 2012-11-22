package com.nedogeek.holdem.gamingStuff;

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
}
