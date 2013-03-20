package com.nedogeek.holdem.dealer;


import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 14:29
 */
public class EventManagerTest {
    private EventManager eventManager;

    private volatile WebSocket.Connection firstViewerMock;
    private WebSocket.Connection secondViewerMock;
    private Event eventMock;


    @Before
    public void setUp() throws Exception {
        firstViewerMock = mock(WebSocket.Connection.class);
        secondViewerMock = mock(WebSocket.Connection.class);
        Dealer dealerMock = mock(Dealer.class);
        PlayersList playersListMock = mock(PlayersList.class);
        when(playersListMock.iterator()).thenReturn(new ArrayList<Player>().iterator());
        eventMock = mock(Event.class);

        eventManager = EventManager.getTestInstance();
        eventManager.setDealer(dealerMock);
        eventManager.setPlayersList(playersListMock);
        eventManager.addViewer(firstViewerMock);
    }

    @After
    public void tearDown() throws Exception {
        eventManager.closeConnection(firstViewerMock);
        eventManager.closeConnection(secondViewerMock);
    }

    @Test
    public void shouldFirstViewerMockSendMessageWhenAddGameEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(firstViewerMock).sendMessage(anyString());
    }

    @Test
    public void shouldConnectionCloseWhenThirdViewerThrowsIOException() throws Exception {
        WebSocket.Connection throwingConnection = mock(WebSocket.Connection.class);
        Mockito.doThrow(new IOException()).when(throwingConnection).sendMessage(anyString());
        eventManager.addViewer(throwingConnection);

        eventManager.addEvent(eventMock);

        verify(throwingConnection).close();
    }

    @Test
    public void shouldSecondViewerMockSendMessageWhenItAddsToEventManager() throws Exception {
        eventManager.addViewer(secondViewerMock);

        eventManager.addEvent(eventMock);

        verify(secondViewerMock).sendMessage(anyString());
    }

    @Test
    public void shouldFirstViewerMockSendMessageWhenSecondViewerAddsToEventManager() throws Exception {
        eventManager.addViewer(secondViewerMock);

        eventManager.addEvent(eventMock);

        verify(firstViewerMock).sendMessage(anyString());
    }

    @Test
    public void shouldNotNotifyFirstViewerWhenConnectionClosed() throws Exception {
        eventManager.closeConnection(firstViewerMock);

        eventManager.addEvent(eventMock);

        verify(firstViewerMock, never()).sendMessage(anyString());
    }
}
