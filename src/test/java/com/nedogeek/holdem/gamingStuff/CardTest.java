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
