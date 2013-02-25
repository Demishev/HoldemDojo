package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.Dealer;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 08.01.13
 * Time: 3:23
 */
public class PlayerTest {
    private static final String NAME = "Player name";

    private PlayersList playersListMock;
    private Dealer dealerMock;

    private Player player;

    @Before
    public void setUp() throws Exception {
        playersListMock = mock(PlayersList.class);
        dealerMock = mock(Dealer.class);

        player = new Player(NAME, dealerMock);

        player.registerList(playersListMock);
        player.setBalance(1000);
    }

    @Test
    public void shouldNameSavedWhenPlayerCreates() throws Exception {
        assertEquals(NAME, player.getName());
    }

    @Test
    public void shouldMakeMoveToPlayersListWhenPlayerMakesBet() throws Exception {
        player.makeBet(500);

        verify(playersListMock).playerMoved(player);
    }

    @Test
    public void shouldNotMovedWhenNewPlayerGetStatus() throws Exception {
        assertEquals(PlayerStatus.NotMoved, player.getStatus());
    }

    @Test
    public void shouldFoldWhenNewPlayerSetStatusFoldAndGetStatus() throws Exception {
        player.setStatus(PlayerStatus.Fold);

        assertEquals(PlayerStatus.Fold, player.getStatus());
    }

    @Test
    public void should100SendToPotWhenNewPlayerSetBalance1000AndBet100() throws Exception {
        player.makeBet(100);

        verify(dealerMock).sendToPot(100);
    }

    @Test
    public void should200SendToPotWhenNewPlayerSetBalance1000AndBet200() throws Exception {
        player.makeBet(200);

        verify(dealerMock).sendToPot(200);
    }

    @Test
    public void should1000SendToPotWhenNewPlayerSetBalance1000AndBet2000() throws Exception {
        player.makeBet(2000);

        verify(dealerMock).sendToPot(1000);
    }

    @Test
    public void shouldBalance0WhenNewPlayerSetBalance1000AndBet2000() throws Exception {
        player.makeBet(2000);

        assertEquals(0, player.getBalance());
    }

    @Test
    public void shouldBalance800WhenNewPlayerSetBalance1000AndBet200() throws Exception {
        player.makeBet(200);

        assertEquals(800, player.getBalance());
    }

    @Test
    public void shouldBet200WhenNewPlayerSetBalance1000AndBet200() throws Exception {
        player.makeBet(200);

        assertEquals(200, player.getBet());
    }

    @Test
    public void shouldBet1000WhenNewPlayerSetBalance1000AndBet2000() throws Exception {
        player.makeBet(2000);

        assertEquals(1000, player.getBet());
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
}
