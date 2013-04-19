package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
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
    private EventManager eventManager;
    private GameImpl game;
    private final WebSocket.Connection connectionMock = mock(WebSocket.Connection.class);
    private PlayersList playersList;
    private final List<String> playersNames = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        eventManager = mock(EventManager.class);
        resetPlayersListMock();

        game = new GameImpl(eventManager, playersList);
    }

    private void resetPlayersListMock() {
        playersList = mock(PlayersList.class);

        when(playersList.getPlayerNames()).thenReturn(playersNames);
    }

    @Test
    public void shouldGameStatusNotEnoughPlayersWhenNewGameImpl() throws Exception {
        assertEquals(GameStatus.NOT_ENOUGH_PLAYERS, game.getGameStatus());
    }

    @Test
    public void shouldEventManagerMockConnectionAddViewerWhenGameAddViewerConnection() throws Exception {
        game.addViewer(connectionMock);

        verify(eventManager).addViewer(connectionMock);
    }

    @Test
    public void shouldEventManagerMockConnectionAddPlayerWhenGameAddPlayerConnection() throws Exception {
        final String playerName = "Player";

        game.addPlayer(playerName, connectionMock);

        verify(eventManager).addPlayer(connectionMock, playerName);
    }

    @Test
    public void shouldEventManagerMockConnectionRemoveViewerWhenGameRemoveViewerConnection() throws Exception {
        game.removeConnection(connectionMock);

        verify(eventManager).closeConnection(connectionMock);
    }

    @Test
    public void shouldPlayersListRemovePlayerWhenGameRemovePlayerByName() throws Exception {
        game.removePlayer("Some player");

        verify(playersList).kickPlayer("Some player");
    }

    @Test
    public void shouldReturnedListOfPlayersWhenGameGetPlayers() throws Exception {


        assertEquals(playersNames, game.getPlayers());
    }
}
