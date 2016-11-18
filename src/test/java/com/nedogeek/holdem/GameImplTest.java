package com.nedogeek.holdem;

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


import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import com.nedogeek.holdem.server.GameDataBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:17
 */
public class GameImplTest {
    private GameImpl game;
    private PlayersList playersList;
    private final List<String> playersNames = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        resetPlayersListMock();

        game = new GameImpl(playersList);
    }

    private void resetPlayersListMock() {
        playersList = mock(PlayersList.class);

        when(playersList.getPlayerNames()).thenReturn(playersNames);
    }

    @Test
    public void shouldPlayersListRemovePlayerWhenGameRemovePlayerByName() throws Exception {
        game.removePlayer("Some player");

        verify(playersList).kickPlayer("Some player");
    }

    @Test
    public void shouldNotEnoughPlayersWhenGameDataGetGameStatus() throws Exception {
        assertEquals(GameStatus.NOT_ENOUGH_PLAYERS, getGameData().getGameStatus());
    }

    private GameDataBean getGameData() {
        return game.getGameData();
    }

    @Test
    public void shouldPlayersWhenGameDataGetPlayers() throws Exception {
        assertEquals(playersNames, getGameData().getPlayers());
    }

    @Test
    public void shouldPlayersListSetPlayerMoveWhenGameSetMove() throws Exception {
        String login = "Some player";
        PlayerAction move = mock(PlayerAction.class);

        game.setMove(login, move);

        verify(playersList).setPlayerMove(login, move);
    }
}
