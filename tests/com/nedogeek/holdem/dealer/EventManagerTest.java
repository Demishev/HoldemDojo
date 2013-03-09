package com.nedogeek.holdem.dealer;


import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 14:29
 */
public class EventManagerTest {
    private EventManager eventManager = EventManager.getInstance();

    private WebSocket.Connection firstViewerMock;
    private Dealer dealerMock;
    private PlayersList playersListMock;
    private Event eventMock;


    @Before
    public void setUp() throws Exception {
        firstViewerMock = mock(WebSocket.Connection.class);
        dealerMock = mock(Dealer.class);
        playersListMock = mock(PlayersList.class);
        eventMock = mock(Event.class);

        eventManager.setDealer(dealerMock);
        eventManager.setPlayersList(playersListMock);
        eventManager.addViewer(firstViewerMock);
    }


    @Test
    public void shouldFirstViewerMockSendMessageWhenAddGameEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(firstViewerMock).sendMessage(anyString());
    }

    @Test
    public void shouldConnectionCloseWhenViewerThrowsIOException() throws Exception {
        Mockito.doThrow(new IOException()).when(firstViewerMock).sendMessage(anyString());

        eventManager.addEvent(eventMock);

        verify(firstViewerMock).close();
    }
}
