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

