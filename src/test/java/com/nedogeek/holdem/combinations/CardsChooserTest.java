package com.nedogeek.holdem.combinations;

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


import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardSuit;
import com.nedogeek.holdem.gamingStuff.CardValue;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: Konstantin Demishev
 * Date: 05.12.12
 * Time: 20:48
 */
public class CardsChooserTest {
    private CardsChooser sixCardsChooser;
    private CardsChooser sevenCardsChooser;

    private final Card FIRST_CARD = new Card(CardSuit.HEARTS, CardValue.TWO);
    private final Card SECOND_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);
    private final Card THIRD_CARD = new Card(CardSuit.HEARTS, CardValue.FOUR);
    private final Card FORTH_CARD = new Card(CardSuit.HEARTS, CardValue.FIVE);
    private final Card FIFTH_CARD = new Card(CardSuit.HEARTS, CardValue.SIX);
    private final Card SIXTH_CARD = new Card(CardSuit.HEARTS, CardValue.SEVEN);
    private final Card SEVENTH_CARD = new Card(CardSuit.HEARTS, CardValue.EIGHT);

    private final Card[] sixCards = new Card[]
            {FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD};
    private final Card[] sevenCards = new Card[]
            {FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD, SEVENTH_CARD};

    @Before
    public void setUp() throws Exception {
        sixCardsChooser = new CardsChooser(sixCards);
        sevenCardsChooser = new CardsChooser(sevenCards);
    }

    @Test
    public void shouldSecondToSixthCardWhenIteratorNext() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD
        };

        assertTrue(Arrays.deepEquals(secondToSixthCards, sixCardsChooser.next()));
    }

    @Test
    public void shouldTrueWhenNewIteratorHasNext() throws Exception {
        assertTrue(sixCardsChooser.hasNext());
    }


    @Test
    public void shouldNoSecondCardWhenIteratorNext2Times() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                FIRST_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD
        };

        sixCardsChooser.next();

        assertTrue(Arrays.deepEquals(secondToSixthCards, sixCardsChooser.next()));
    }

    @Test
    public void shouldNoExceptionWhenCallNext7Times() throws Exception {
        iterate6CardsChooserTimes(7);
    }

    private void iterate6CardsChooserTimes(int times) {
        for (int i = 0; i < times; i++) {
            sixCardsChooser.next();
        }
    }

    private void iterate7CardsChooserTimes(int times) {
        for (int i = 0; i < times; i++) {
            sevenCardsChooser.next();
        }
    }

    @Test
    public void shouldNoSixthCardWhenIteratorNext7Times() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD
        };

        iterate6CardsChooserTimes(6);

        assertTrue(Arrays.deepEquals(secondToSixthCards, sixCardsChooser.next()));
    }

    @SuppressWarnings("UnusedDeclaration")
    @Test (timeout = 100)
    public void shouldIterationsGoes6TimesWhenIteratingWithForeEach() throws Exception {
        int times = 0;
        for (Card[] cards1: sixCardsChooser) {
            times++;
        }

        assertEquals(6, times);
    }

    @Test
    public void should5CardsWhen7CardsChooser() throws Exception {
        assertEquals(5, sevenCardsChooser.next().length);
    }

    @Test
    public void shouldWithoutFirstAndSecondCardsWhenCardsChooserNext() throws Exception {
        final Card[] cardsExpected = new Card[] {
                THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD, SEVENTH_CARD
        };

        assertTrue(Arrays.deepEquals(cardsExpected, sevenCardsChooser.next()));
    }

    @Test
    public void shouldWithoutFirstAndThirdCardsWhenCardsChooserNext2Times() throws Exception {
        final Card[] cardsExpected = new Card[] {
                SECOND_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD, SEVENTH_CARD
        };

        iterate7CardsChooserTimes(1);

        assertTrue(Arrays.deepEquals(cardsExpected, sevenCardsChooser.next()));
    }

    @Test
    public void shouldWithoutSecondAndThirdCardsWhenCardsChooserNext7Times() throws Exception {
        final Card[] cardsExpected = new Card[] {
                FIRST_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD, SEVENTH_CARD
        };

        iterate7CardsChooserTimes(6);

        assertTrue(Arrays.deepEquals(cardsExpected, sevenCardsChooser.next()));
    }

    @SuppressWarnings("UnusedDeclaration")
    @Test
    public void shouldNoSixAndSeventhCardsExceptionWhenForEachGoes() throws Exception {
        final Card[] cardsExpected = new Card[] {
                FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD
        };
        for (Card[] cards:sevenCardsChooser) {
        }
        assertTrue(Arrays.deepEquals(cardsExpected, sevenCardsChooser.next()));
    }
}
