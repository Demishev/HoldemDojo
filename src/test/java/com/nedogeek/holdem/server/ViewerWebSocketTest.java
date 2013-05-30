package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameCenter;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 30.05.13
 * Time: 3:09
 */
public class ViewerWebSocketTest {
    private static final String FIRST_GAME_ID = "first game";
    private static final String SECOND_GAME_ID = "second game";

    GameCenter gameCenterMock;

    ViewerWebSocket socket;
    private WebSocket.Connection connectionMock;


    @Before
    public void setUp() throws Exception {
        gameCenterMock = mock(GameCenter.class);
        connectionMock = mock(WebSocket.Connection.class);
    }

    private void createSocket(String gameId) {
        socket = new ViewerWebSocket(gameId, gameCenterMock);
    }

    @Test
    public void shouldGameCenterConnectViewerToFirstGameWhenOpenWerSocket() throws Exception {
        createSocket(FIRST_GAME_ID);

        socket.onOpen(connectionMock);

        verify(gameCenterMock).connectViewer(FIRST_GAME_ID, connectionMock);
    }

    @Test
    public void shouldGameCenterConnectViewerToSecondGameWhenOpenWerSocket() throws Exception {
        createSocket(SECOND_GAME_ID);

        socket.onOpen(connectionMock);

        verify(gameCenterMock).connectViewer(SECOND_GAME_ID, connectionMock);
    }
}

