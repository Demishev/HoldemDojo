package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {

    private Desk deskMock;
    private PlayersManager playersManagerMock;
    private Bank bankMock;

    private EndGameManager endGameManager;



    @Before
    public void setUp() throws Exception {
        resetBankMock();
        resetDeskMock();
        resetPlayerManagerMock();

        endGameManager = new EndGameManager(deskMock, playersManagerMock);
    }

    private void resetDeskMock() {
        deskMock = mock(Desk.class);
    }

    private void resetBankMock() {
        bankMock = mock(Bank.class);
    }

    private void resetPlayerManagerMock() {
        playersManagerMock = mock(PlayersManager.class);
        setPlayersQuantity(2);

        int DEFAULT_AMOUNT = 100;
        setPlayerBet(0, DEFAULT_AMOUNT);
        setPlayerBet(1, DEFAULT_AMOUNT);
        setPlayerStatus(0, PlayerStatus.NotMoved);
        setPlayerStatus(1, PlayerStatus.NotMoved);

        setCombinationsRelations(0,1);
    }

    private void setCombinationsRelations(int biggerCombination, int smallerCombination) {
        when(playersManagerMock.isFirstCombinationBiggerThanSecond(biggerCombination, smallerCombination)).thenReturn(true);
        when(playersManagerMock.isFirstCombinationBiggerThanSecond(smallerCombination, biggerCombination)).thenReturn(false);
    }

    private void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
        when(playersManagerMock.getPlayerStatus(playerNumber)).thenReturn(playerStatus);
    }

    private void setPlayerBet(int playerNumber, int playerBet) {
        when(bankMock.getPlayerBet(playerNumber)).thenReturn(playerBet);
    }

    private void setPlayersQuantity(int playersQuantity) {
        when(playersManagerMock.getPlayersQuantity()).thenReturn(playersQuantity);
    }

    private void setPlayerFold(int playerNumber) {
        when(playersManagerMock.getPlayerStatus(playerNumber)).thenReturn(PlayerStatus.Fold);
    }

    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(deskMock).setGameEnded();
    }

    @Test
    public void shouldSecondPlayerWinWhenFirstPlayerFolds() throws Exception {
        setPlayerFold(0);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(1);
    }

    @Test
    public void shouldFirstPlayerWinWhenSecondPlayerFolds() throws Exception {
        setPlayerFold(1);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(0);
    }

    @Test
    public void shouldSecondPlayerWinWhenNoPlayerFoldsAndFirstHasWinningCombination() throws Exception {
        endGameManager.endGame();

        verify(deskMock).setPlayerWin(0);
    }

    @Test
    public void shouldFirstPlayerWinWhenNoPlayerFoldsAndSecondHasWinningCombination() throws Exception {
        setCombinationsRelations(1,0);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(1);
    }
}
