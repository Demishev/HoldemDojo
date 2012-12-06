package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {
    private final int DEFAULT_AMOUNT = 100;

    private EndGameManager endGameManager;

    private Desk desk;

    @Before
    public void setUp() throws Exception {
        resetDeskMock();

        endGameManager = new EndGameManager(desk);
    }

    private void resetDeskMock() {
        makeDefault2PlayersDesk();
    }

    private void makeDefault2PlayersDesk() {
        desk = mock(Desk.class);
        setPlayersQuantity(2);
        setPlayerBet(0,DEFAULT_AMOUNT);
        setPlayerBet(1, DEFAULT_AMOUNT);
        setPlayerStatus(0, PlayerStatus.NotMoved);
        setPlayerStatus(1, PlayerStatus.NotMoved);
    }

    private void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
        when(desk.getPlayerStatus(playerNumber)).thenReturn(playerStatus);
    }

    private void setPlayerBet(int playerNumber, int playerBet) {
        when(desk.getPlayerBet(playerNumber)).thenReturn(playerBet);
    }

    private void setPlayersQuantity(int playersQuantity) {
        when(desk.getPlayersQuantity()).thenReturn(playersQuantity);
    }

    private void setPlayerFold(int playerNumber) {
        when(desk.getPlayerStatus(playerNumber)).thenReturn(PlayerStatus.Fold);
    }

    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(desk).setGameEnded();
    }

    @Test
    public void shouldSecondPlayerWinWhenFirstPlayerFolds() throws Exception {
        setPlayerFold(0);

        endGameManager.endGame();

        verify(desk).setPlayerWin(1);
    }

    @Test
    public void shouldFirstPlayerWinWhenSecondPlayerFolds() throws Exception {
        setPlayerFold(1);

        endGameManager.endGame();

        verify(desk).setPlayerWin(0);
    }
}
