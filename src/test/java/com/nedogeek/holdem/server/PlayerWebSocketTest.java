package com.nedogeek.holdem.server;

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
