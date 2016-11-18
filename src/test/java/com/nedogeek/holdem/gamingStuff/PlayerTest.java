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


import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.Dealer;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 08.01.13
 * Time: 3:23
 */
public class PlayerTest {
    private static final String NAME = "Player name";
    private static final Card CLUBS_ACE = new Card(CardSuit.CLUBS, CardValue.ACE);
    private static final Card CLUBS_KING = new Card(CardSuit.CLUBS, CardValue.KING);
    private static final Card CLUBS_QUEEN = new Card(CardSuit.CLUBS, CardValue.QUEEN);
    private static final Card CLUBS_JACK = new Card(CardSuit.CLUBS, CardValue.JACK);
    private static final Card CLUBS_TEN = new Card(CardSuit.CLUBS, CardValue.TEN);

    private Dealer dealerMock;

    private Player player;

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        when(dealerMock.getDeskCards()).thenReturn(new Card[]{});

        player = new Player(NAME, dealerMock);

        player.setBalance(1000);
    }

    @Test
    public void shouldNameSavedWhenPlayerCreates() throws Exception {
        assertEquals(NAME, player.getName());
    }

    @Test
    public void shouldNotMovedWhenNewPlayerGetStatus() throws Exception {
        assertEquals(PlayerStatus.NotMoved, player.getStatus());
    }

    @Test
    public void shouldSecondPlayerNameWhenSecondPlayerGetName() throws Exception {
        String secondPlayerName = "Second player";
        Player secondPlayer = new Player(secondPlayerName, dealerMock);

        assertEquals(secondPlayerName, secondPlayer.getName());
    }

    @Test
    public void shouldFoldWhenNewPlayerSetStatusFoldAndGetStatus() throws Exception {
        player.setStatus(PlayerStatus.Fold);

        assertEquals(PlayerStatus.Fold, player.getStatus());
    }

    @Test
    public void shouldActiveNotRisePlayerWhenPlayerStatusSmallBlind() throws Exception {
        player.setStatus(PlayerStatus.SmallBLind);

        assertTrue(player.isActiveNotRisePlayer());
    }

    @Test
    public void shouldActiveRisePlayerWhenPlayerStatusBigBlindPlayerBet20CallValue20() throws Exception {
        player.setStatus(PlayerStatus.BigBlind);
        player.setBet(20);
        setCallValue(20);

        assertTrue(player.isActiveNotRisePlayer());
    }

    @Test
    public void shouldActiveNotRisePlayerWhenPlayerStatusCallPlayerBet20CallValue20() throws Exception {
        player.setStatus(PlayerStatus.Call);
        player.setBet(20);
        setCallValue(20);

        assertFalse(player.isActiveNotRisePlayer());
    }

    private void setCallValue(int callValue) {
        when(dealerMock.getCallValue()).thenReturn(callValue);
    }

    @Test
    public void shouldProperToStringWhenNoCardsOnDeskAndClubsAceClubsKingInPlayer() throws Exception {
        player.setCards(new Card[]{CLUBS_ACE, CLUBS_KING});

        assertEquals("High card Ace with King: [A♣, K♣]", player.getCardCombination().toString());
    }

    @Test
    public void shouldProperToStringWhenClubQueenClubJackClubTenOnDeskAndClubsAceClubsKingInPlayer() throws Exception {
        player.setCards(new Card[]{CLUBS_ACE, CLUBS_KING});
        when(dealerMock.getDeskCards()).thenReturn(new Card[]{CLUBS_QUEEN, CLUBS_JACK, CLUBS_TEN});

        assertEquals("Royal flash: [A♣, K♣, Q♣, J♣, 10♣]", player.getCardCombination().toString());
    }

    @Test
    public void shouldBalanceCOINS_AT_STARTWhenNewPlayer() throws Exception {
        assertEquals(GameSettings.getCoinsAtStart(), new Player(null, null).getBalance());
    }

    @Test
    public void shouldPlayerWithCardsSerializedToJSONProperly() throws Exception {
        String json = player.toJSON();

        assertTrue(json.contains("\"balance\":1000"));
        assertTrue(json.contains("\"status\":\"NotMoved\""));
        assertTrue(json.contains("\"name\":\"Player name\""));
        assertTrue(json.contains("\"pot\":0"));
    }

    @Test
    public void shouldPlayerWithCardsSerializedToJSONWithCardsProperly() throws Exception {
        Card firstCardMock = mock(Card.class);
        Card secondCardMock = mock(Card.class);

        when(firstCardMock.toJSON()).thenReturn("FirstCardJSON");
        when(secondCardMock.toJSON()).thenReturn("SecondCardJSON");

        player.setCards(new Card[]{firstCardMock, secondCardMock});

        assertTrue(player.toJSONWithCards().contains("\"cards\":[\"FirstCardJSON\",\"SecondCardJSON\"]"));
        assertTrue(player.toJSONWithCards().contains("\"name\":\"Player name\""));
    }

    @Test
    public void shouldNoNullPointerWhenNewPlayerGenerateJSONWithCards() throws Exception {
        new Player("New player", dealerMock).toJSONWithCards();
    }
}
