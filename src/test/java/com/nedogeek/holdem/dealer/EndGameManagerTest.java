package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {
    private Dealer dealerMock;
    private PlayersList playersListMock;
    private EventManager eventManagerMock;

    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    private EndGameManager endGameManager;


    @Before
    public void setUp() throws Exception {
        resetDeskMock();
        resetPlayersMocks();
        resetPlayerListMock();

        GameSettings.END_GAME_DELAY_VALUE = 0;

        eventManagerMock = mock(EventManager.class);

        endGameManager = new EndGameManager(dealerMock, playersListMock, eventManagerMock);
    }

    private void resetPlayersMocks() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        thirdPlayerMock = mock(Player.class);

        players = new ArrayList<>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);
        players.add(thirdPlayerMock);

        setPlayersRelations();

        for (Player player : players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
        }

    }

    private void setPlayersRelations() {
        when(firstPlayerMock.compareTo(secondPlayerMock)).thenReturn(1);
        when(secondPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(firstPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(secondPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(secondPlayerMock)).thenReturn(-1);
    }

    private void resetDeskMock() {
        dealerMock = mock(Dealer.class);
    }

    private void resetPlayerListMock() {
        playersListMock = mock(PlayersList.class);

        when(playersListMock.iterator()).thenReturn(players.iterator()).thenReturn(players.iterator());
        when(playersListMock.toArray()).thenReturn(players.toArray());
        when(playersListMock.get(0)).thenReturn(firstPlayerMock);
    }

    private void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        when(player.getStatus()).thenReturn(playerStatus);
    }

    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(dealerMock).setInitialGameRound();
    }

    @Test
    public void shouldFirstPlayerSetBalance500WhenHisBet250AndSecondBet500() throws Exception {
        setPlayerBet(firstPlayerMock, 250);
        setPlayerBet(secondPlayerMock, 500);

        endGameManager.endGame();

        verify(firstPlayerMock).setBalance(500);
    }

    @Test
    public void shouldSecondPlayerSetBalance250WhenHisBet250AndSecondBet750() throws Exception {
        setPlayerBet(firstPlayerMock, 250);
        setPlayerBet(secondPlayerMock, 500);

        endGameManager.endGame();

        verify(secondPlayerMock).setBalance(750);
    }

    private void setPlayerBet(Player player, int bet) {
        when(player.getBet()).thenReturn(bet);
    }
}
