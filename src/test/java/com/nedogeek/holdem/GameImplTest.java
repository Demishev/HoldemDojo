package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:17
 */
public class GameImplTest {
    private EventManager eventManager;
    private GameImpl game;
    private final WebSocket.Connection connectionMock = mock(WebSocket.Connection.class);


    @Before
    public void setUp() throws Exception {
        PlayersList playersList = mock(PlayersList.class);
        eventManager = mock(EventManager.class);

        game = new GameImpl(eventManager, playersList);
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
}
