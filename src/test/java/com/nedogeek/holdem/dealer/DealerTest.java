package com.nedogeek.holdem.dealer;

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
    private final GameStatus DEFAULT_GAME_STATUS = GameStatus.NOT_ENOUGH_PLAYERS;
    private final GameRound DEFAULT_GAME_ROUND = GameRound.INITIAL;

    private Dealer dealer;

    private MoveManager moveManagerMock;
    private NewGameSetter newGameSetterMock;
    private PlayersList playersManagerMock;
    private EndGameManager endGameManagerMock;
    private EventManager eventManagerMock;

    private Player mover;

    @Before
    public void setUp() throws Exception {
        moveManagerMock = mock(MoveManager.class);
        newGameSetterMock = mock(NewGameSetter.class);
        resetPlayerManager();
        endGameManagerMock = mock(EndGameManager.class);
        eventManagerMock = mock(EventManager.class);

        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                endGameManagerMock, DEFAULT_GAME_STATUS, DEFAULT_GAME_ROUND, eventManagerMock);
    }

    @SuppressWarnings("unchecked")
    private void resetPlayerManager() {
        playersManagerMock = mock(PlayersList.class);

        mover = mock(Player.class);

        when(playersManagerMock.getMover()).thenReturn(mover);
        Iterator playerMockIterator = mock(Iterator.class);
        when(playersManagerMock.iterator()).thenReturn(playerMockIterator);
    }

    private void setGameData(GameStatus gameStatus, GameRound gameRound) {
        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                endGameManagerMock, gameStatus, gameRound, eventManagerMock);
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
        dealer = new Dealer(playersManagerMock, eventManagerMock);
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

    @Test
    public void shouldNoMoveManagerMakesMoveWhenGameStatusPAUSEDHasAvailableMovesAndGameRoundTHREE_CARDS() throws Exception {
        setGameData(GameStatus.PAUSED, GameRound.THREE_CARDS);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock, never()).makeMove(mover);
    }
}
