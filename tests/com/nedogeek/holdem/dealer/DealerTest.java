package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:52
 */
public class DealerTest {
    private final GameStatus DEFAULT_GAME_STATUS = GameStatus.NOT_READY;
    private final GameRound DEFAULT_GAME_ROUND = GameRound.INITIAL;

    private Dealer dealer;

    private MoveManager moveManagerMock;
    private NewGameSetter newGameSetterMock;
    private PlayersList playersManagerMock;
    private EndGameManager endGameManagerMock;

    private Player mover;

    @Before
    public void setUp() throws Exception {
        moveManagerMock = mock(MoveManager.class);
        newGameSetterMock = mock(NewGameSetter.class);
        resetPlayerManager();
        endGameManagerMock = mock(EndGameManager.class);

        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                endGameManagerMock, DEFAULT_GAME_STATUS, DEFAULT_GAME_ROUND);
    }

    private void resetPlayerManager() {
        playersManagerMock = mock(PlayersList.class);

        mover = mock(Player.class);

        when(playersManagerMock.getMover()).thenReturn(mover);
        Iterator<Player> playerMockIterator = mock(Iterator.class);
        when(playersManagerMock.iterator()).thenReturn(playerMockIterator);
    }

    private void setGameData(GameStatus gameStatus, GameRound gameRound) {
        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                endGameManagerMock, gameStatus, gameRound);
    }

    @Test
    public void shouldNoPlayerManagerHasAvailableMovesWhenGameStatusSTARTEDAndGameRoundINITIAL() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.INITIAL);

        dealer.tick();

        verify(playersManagerMock, never()).hasAvailableMovers();
    }

    @Test
    public void shouldMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundTHREE_CARDS() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.THREE_CARDS);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock).makeMove(mover);
    }

    @Test
    public void shouldNoMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundFINAL() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.FINAL);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock, never()).makeMove(mover);
    }

    @Test
    public void shouldEndGameEndGameManagerWhenGameStatusStartedAndGameRoundFinal() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.FINAL);

        dealer.tick();

        verify(endGameManagerMock).endGame();
    }

    @Test
    public void shouldNoNullPointerExceptionWhenNewDealerTick() throws Exception {
        dealer = new Dealer(playersManagerMock);
        try {
            dealer.tick();
        } catch (NullPointerException e) {
            Assert.fail("Check is new Dealer has initial status.");
        }
    }

    @Test
    public void shouldNewGameSetterSetNewGameWhenDealerTick() throws Exception {
        setGameData(GameStatus.READY, GameRound.INITIAL);

        dealer.tick();

        verify(newGameSetterMock).setNewGame();
    }
}
