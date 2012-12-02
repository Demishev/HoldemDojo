package com.nedogeek.holdem.combinations;

import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardSuit;
import com.nedogeek.holdem.gamingStuff.CardValue;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 27.11.12
 * Time: 14:23
 */
public class PlayerCardCombinationTest {
    private static final Card HEARTS_ACE = new Card(CardSuit.HEARTS, CardValue.ACE);
    private static final Card HEARTS_KING = new Card(CardSuit.HEARTS, CardValue.KING);
    private static final Card HEARTS_QUEEN = new Card(CardSuit.HEARTS, CardValue.QUEEN);
    private static final Card HEARTS_JACK = new Card(CardSuit.HEARTS, CardValue.JACK);
    private static final Card HEARTS_SIX = new Card(CardSuit.HEARTS, CardValue.SIX);

    private static final Card DIAMONDS_ACE = new Card(CardSuit.DIAMONDS, CardValue.ACE);
    private static final Card DIAMONDS_KING = new Card(CardSuit.DIAMONDS, CardValue.KING);

    private void assertCardsEquals(Card[] firstCards, Card[] secondCards) {
        PlayerCardCombination firstCombination = new PlayerCardCombination(firstCards);
        PlayerCardCombination secondCombination = new PlayerCardCombination(secondCards);

        assertEquals(0, firstCombination.compareTo(secondCombination));
    }

    @Test
    public void shouldCardCombinationsEqualsWhenHeartsAceHeartsKingDiamondsAceDiamondsKing() throws Exception {
        assertCardsEquals(new Card[]{HEARTS_ACE, HEARTS_KING}, new Card[]{DIAMONDS_ACE, DIAMONDS_KING});
    }

    @Test
    public void shouldHighCardAceWithKickerKingWhenGetCombinationHeartsAceHeartsKing() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_ACE, HEARTS_KING});

        assertEquals("High card Ace with kicker King", cardCombination.getCombination());
    }

    @Test
    public void shouldHighCardAceWithKickerQueenWhenGetCombinationHeartsAceHeartsQueen() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_ACE, HEARTS_QUEEN});

        assertEquals("High card Ace with kicker Queen", cardCombination.getCombination());
    }

    @Test
    public void shouldHighCardAceWithKickerKingWhenGetCombinationHeartsKingHeartsAce() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_KING, HEARTS_ACE});

        assertEquals("High card Ace with kicker King", cardCombination.getCombination());
    }

    @Test
    public void shouldPairOfAceWhenHeartsAceAndDiamondsAceGetCombination() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_ACE, DIAMONDS_ACE});

        assertEquals("Pair of Ace", cardCombination.getCombination());
    }

    @Test
    public void shouldPairOfAceHAceDAceWhenHeartsAceAndDiamondsAceToString() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_ACE, DIAMONDS_ACE});

        assertEquals("Pair of Ace: [♥A, ♦A]", cardCombination.toString());
    }

    @Test
    public void shouldFlashWithHAKQL6WhenHeartsAceKingQueenJackAnd6GetCombination() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(
                new Card[] {HEARTS_ACE, HEARTS_KING, HEARTS_QUEEN, HEARTS_JACK, HEARTS_SIX});

        assertEquals("Flash on Ace with King, Queen, Jack and 6", cardCombination.getCombination());
    }

    @Test
    public void shouldFlashWithHAKQL6WhenHearts6JackQueenKingAndAceGetCombination() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(
                new Card[] {HEARTS_SIX, HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, HEARTS_ACE});

        assertEquals("Flash on Ace with King, Queen, Jack and 6", cardCombination.getCombination());
    }

    @Test
    public void shouldHighCardAceWithKickersKQL6WhenHearts6JackQueenKingAndDiamondAceGetCombination() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(
                new Card[] {HEARTS_SIX, HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, DIAMONDS_ACE});

        assertEquals("High card Ace with kickers: King, Queen, Jack and 6", cardCombination.getCombination());
    }

    @Test
    public void shouldHighCardKingWithKickerQueenWhenGetCombinationHeartsKingHeartsQueen() throws Exception {
        PlayerCardCombination cardCombination = new PlayerCardCombination(new Card[] {HEARTS_KING, HEARTS_QUEEN});

        assertEquals("High card King with kicker Queen", cardCombination.getCombination());
    }
}
