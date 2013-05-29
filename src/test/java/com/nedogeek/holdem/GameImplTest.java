package com.nedogeek.holdem;

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
