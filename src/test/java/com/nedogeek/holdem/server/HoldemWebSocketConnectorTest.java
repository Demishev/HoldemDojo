package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameCenter;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 30.05.13
 * Time: 1:48
 */
public class HoldemWebSocketConnectorTest {
    private static final String FIRST_PLAYER = "first player";
    private static final String SECOND_PLAYER = "second player";
    private static final String FIRST_PLAYER_PASSWORD = "first player password";
    private static final String SECOND_PLAYER_PASSWORD = "second player password";

    private static final PlayerWebSocket FIRST_PLAYER_SOCKET = mock(PlayerWebSocket.class);
    private static final PlayerWebSocket SECOND_PLAYER_SOCKET = mock(PlayerWebSocket.class);

    private static final ViewerWebSocket FIRST_GAME_VIEWER_SOCKET = mock(ViewerWebSocket.class);
    private static final ViewerWebSocket SECOND_GAME_VIEWER_SOCKET = mock(ViewerWebSocket.class);

    private static final String FIRST_GAME_ID = "first game";
    private static final String SECOND_GAME_ID = "second game";

    HoldemWebSocketFactory factoryMock;
    GameCenter gameCenterMock;
    Map<String, String> userData;

    HoldemWebSocketConnector connector;

    @Before
    public void setUp() throws Exception {
        resetMocks();

        userData = new HashMap<>();

        createConnector();
    }

    private void createConnector() {
        connector = new HoldemWebSocketConnector();

        connector.injectTestDependencies(factoryMock, userData);
    }

    private void resetMocks() {
        factoryMock = mock(HoldemWebSocketFactory.class);
        when(factoryMock.getUserSocket(FIRST_PLAYER)).thenReturn(FIRST_PLAYER_SOCKET);
        when(factoryMock.getUserSocket(SECOND_PLAYER)).thenReturn(SECOND_PLAYER_SOCKET);

        when(factoryMock.getViewerSocket(FIRST_GAME_ID)).thenReturn(FIRST_GAME_VIEWER_SOCKET);
        when(factoryMock.getViewerSocket(SECOND_GAME_ID)).thenReturn(SECOND_GAME_VIEWER_SOCKET);

        gameCenterMock = mock(GameCenter.class);
    }


    private WebSocket connect(String user, String password, String gameId) {
        return connector.connect(user, password, gameId);
    }

    @Test
    public void shouldFactoryGetWebSocketForFirstPlayerWhenConnectWithFirstPlayer() throws Exception {
        connect(FIRST_PLAYER, FIRST_PLAYER_PASSWORD, null);

        verify(factoryMock).getUserSocket(FIRST_PLAYER);
    }

    @Test
    public void shouldFirstPlayerMockWhenConnectFirstPlayerFirstPassword() throws Exception {
        assertEquals(FIRST_PLAYER_SOCKET, connect(FIRST_PLAYER, FIRST_PLAYER_PASSWORD, null));
    }

    @Test
    public void shouldSecondPlayerMockWhenConnectFirstPlayerSecondPassword() throws Exception {
        assertEquals(SECOND_PLAYER_SOCKET, connect(SECOND_PLAYER, FIRST_PLAYER_PASSWORD, null));
    }

    @Test
    public void shouldFirstPlayerMockWhenConnectFirstPlayerFirstPasswordUserInDB() throws Exception {
        userData.put(FIRST_PLAYER, FIRST_PLAYER_PASSWORD);

        assertEquals(FIRST_PLAYER_SOCKET, connect(FIRST_PLAYER, FIRST_PLAYER_PASSWORD, null));
    }

    @Test
    public void shouldSecondPlayerMockWhenConnectSecondPlayerSecondPasswordUserInDB() throws Exception {
        userData.put(SECOND_PLAYER, SECOND_PLAYER_PASSWORD);

        assertEquals(SECOND_PLAYER_SOCKET, connect(SECOND_PLAYER, SECOND_PLAYER_PASSWORD, null));
    }

    @Test
    public void shouldNullWhenConnectFirstPlayerSecondPasswordUserInDB() throws Exception {
        userData.put(FIRST_PLAYER, FIRST_PLAYER_PASSWORD);

        assertEquals(null, connect(FIRST_PLAYER, SECOND_PLAYER_PASSWORD, null));
    }

    @Test
    public void shouldUserInDBWhenConnectFirstPlayerFirstPassword() throws Exception {
        connect(FIRST_PLAYER, FIRST_PLAYER_PASSWORD, null);

        assertEquals(FIRST_PLAYER_PASSWORD, userData.get(FIRST_PLAYER));
    }

    @Test
    public void shouldFirstViewerSocketWhenNullNullFirstGameID() throws Exception {
        assertEquals(FIRST_GAME_VIEWER_SOCKET, connect(null, null, FIRST_GAME_ID));
    }

    @Test
    public void shouldSecondViewerSocketWhenNullNullSecondGameID() throws Exception {
        assertEquals(SECOND_GAME_VIEWER_SOCKET, connect(null, null, SECOND_GAME_ID));
    }
}
