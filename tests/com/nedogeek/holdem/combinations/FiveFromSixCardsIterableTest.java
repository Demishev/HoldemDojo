package com.nedogeek.holdem.combinations;

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
public class FiveFromSixCardsIterableTest {
    private FiveFromSixCardsIterable iterable;

    private final Card FIRST_CARD = new Card(CardSuit.HEARTS, CardValue.TWO);
    private final Card SECOND_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);
    private final Card THIRD_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);
    private final Card FORTH_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);
    private final Card FIFTH_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);
    private final Card SIXTH_CARD = new Card(CardSuit.HEARTS, CardValue.THREE);

    private final Card[] cards = new Card[]
            {FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD};

    @Before
    public void setUp() throws Exception {
        iterable = new FiveFromSixCardsIterable(cards);
    }

    @Test
    public void shouldSecondToSixthCardWhenIteratorNext() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD
        };

        assertTrue(Arrays.deepEquals(secondToSixthCards, iterable.next()));
    }

    @Test
    public void shouldTrueWhenNewIteratorHasNext() throws Exception {
        assertTrue(iterable.hasNext());
    }


    @Test
    public void shouldNoSecondCardWhenIteratorNext2Times() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                FIRST_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD, SIXTH_CARD
        };

        iterable.next();

        assertTrue(Arrays.deepEquals(secondToSixthCards, iterable.next()));
    }

    @Test
    public void shouldNoExceptionWhenCallNext7Times() throws Exception {
        iterateTimes(7);
    }

    private void iterateTimes(int times) {
        for (int i = 0; i < times; i++) {
            iterable.next();
        }
    }

    @Test
    public void shouldNoSixthCardWhenIteratorNext7Times() throws Exception {
        final Card[] secondToSixthCards = new Card[] {
                FIRST_CARD, SECOND_CARD, THIRD_CARD, FORTH_CARD, FIFTH_CARD
        };

        iterateTimes(6);

        assertTrue(Arrays.deepEquals(secondToSixthCards, iterable.next()));
    }

    @Test (timeout = 100)
    public void shouldIterationsGoes6TimesWhenIteratingWithForeEach() throws Exception {
        int times = 0;
        for (Card[] cards1: iterable) {
            times++;
        }

        assertEquals(6, times);
    }
}
