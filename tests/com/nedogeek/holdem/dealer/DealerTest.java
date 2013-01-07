package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:52
 */
public class DealerTest {
    private Dealer dealer;

    private Desk deskMock;
    private MoveManager moveManagerMock;
    private NewGameSetter newGameSetterMock;
    private PlayersManager playersManagerMock;
    private GameCycleManager gameCycleManagerMock;
    private EndGameManager endGameManagerMock;

    @Before
    public void setUp() throws Exception {
        deskMock = mock(Desk.class);
        moveManagerMock = mock(MoveManager.class);
        newGameSetterMock = mock(NewGameSetter.class);
        playersManagerMock = mock(PlayersManager.class);
        gameCycleManagerMock = mock(GameCycleManager.class);
        endGameManagerMock = mock(EndGameManager.class);

        dealer = new Dealer(deskMock, moveManagerMock, newGameSetterMock, playersManagerMock,
                gameCycleManagerMock, endGameManagerMock);
    }

    @Test
    public void shouldGameCycleManagerEndCycleWhenGameStatusCYCLE_ENDED() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.CYCLE_ENDED);

        dealer.tick();

        verify(gameCycleManagerMock).endGameCycle();
    }

    @Test
    public void shouldNoPlayerManagerHasAvailableMovesWhenGameStatusSTARTEDAndGameRoundINITIAL() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.STARTED);
        when(deskMock.getGameRound()).thenReturn(GameRound.INITIAL);

        dealer.tick();

        verify(playersManagerMock, never()).hasAvailableMovers();
    }

    @Test
    public void shouldMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundTHREE_CARDS() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.STARTED);
        when(deskMock.getGameRound()).thenReturn(GameRound.THREE_CARDS);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock).makeMove(0);
    }

    @Test
    public void shouldNoMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundFINAL() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.STARTED);
        when(deskMock.getGameRound()).thenReturn(GameRound.FINAL);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock, never()).makeMove(0);
    }

    @Test
    public void shouldEndGameEndGameManagerWhenGameStatusStartedAndGameRoundFinal() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.STARTED);
        when(deskMock.getGameRound()).thenReturn(GameRound.FINAL);

        dealer.tick();

        verify(endGameManagerMock).endGame();
    }

    @Test
    public void shouldGameCycleManagerPrepareNewGameCycleWhenGameStatusReady() throws Exception {
        when(deskMock.getGameStatus()).thenReturn(GameStatus.READY);

        dealer.tick();

        verify(gameCycleManagerMock).prepareNewGameCycle();
    }
}
