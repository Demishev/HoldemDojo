package com.nedogeek.holdem.gamingStuff;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: Konstantin Demishev
 * Date: 02.12.12
 * Time: 20:51
 */
public class CardTest {

    @Test
    public void shouldHeartsAceToStringProperlyWhenCreatingNewCard() throws Exception {
        assertEquals("A♥", new Card(CardSuit.HEARTS, CardValue.ACE).toString());
    }

    @Test
    public void shouldHeartsAceToJSONProperlyWhenCreatingNewCard() throws Exception {
        String json = new Card(CardSuit.HEARTS, CardValue.ACE).toJSON();

        assertTrue(json.contains("\"cardValue\":\"A\""));
        assertTrue(json.contains("\"cardSuit\":\"♥\""));
    }
}
