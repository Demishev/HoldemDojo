package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameCenter;
import com.nedogeek.holdem.PlayerCommand;
import com.nedogeek.holdem.PlayerCommandsParser;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 28.05.13
 * Time: 20:33
 */
public class PlayerWebSocketTest {
    private final String LOGIN = "login";
    private final String MESSAGE = "message";

    private WebSocket.Connection connectionMock;
    private GameCenter gameCenterMock;
    private PlayerCommandsParser playerCommandsParserMock;
    private PlayerCommand playerCommandMock;


    private PlayerWebSocket webSocket;

    @Before
    public void setUp() throws Exception {
        resetMocks();

        webSocket = new PlayerWebSocket(playerCommandsParserMock, gameCenterMock, LOGIN);
    }

    private void resetMocks() {
        gameCenterMock = mock(GameCenter.class);
        connectionMock = mock(WebSocket.Connection.class);

        playerCommandMock = mock(PlayerCommand.class);
        resetPlayerCommandsParserMock();
    }

    private void resetPlayerCommandsParserMock() {
        playerCommandsParserMock = mock(PlayerCommandsParser.class);

        when(playerCommandsParserMock.parseCommand("login", MESSAGE)).thenReturn(playerCommandMock);
    }

    @Test
    public void shouldGameCenterInvokePlayerCommandMockWhenOnMessageMESSAGE() throws Exception {
        webSocket.onMessage(MESSAGE);

        verify(gameCenterMock).performPlayerCommand(playerCommandMock);
    }

    @Test
    public void shouldAddPlayerWhenOnOpen() throws Exception {
        webSocket.onOpen(connectionMock);

        verify(gameCenterMock).connectPlayer(LOGIN, connectionMock);
    }
}
