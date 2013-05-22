package com.nedogeek.holdem.dealer;

import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ConcurrentModificationException;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 14:32
 */
public class ConnectionsManagerTest {
    private static final String MESSAGE = "message";
    private static final String OTHER_MESSAGE = "other message";
    private static final String FIRST_CONNECTION_OWNER_NAME = "first owner";
    private static final String SECOND_CONNECTION_OWNER_NAME = "second owner";

    private static final String FIRST_GAME = "First game";
    private static final String SECOND_GAME = "Second game";

    private WebSocket.Connection firstConnectionMock;
    private WebSocket.Connection secondConnectionMock;
    private WebSocket.Connection thirdConnectionMock;
    private WebSocket.Connection fourthConnectionMock;
    private WebSocket.Connection fifthConnectionMock;
    private WebSocket.Connection sixthConnectionMock;


    private ConnectionsManager connectionsManager;

    @Before
    public void setUp() throws Exception {
        connectionsManager = new ConnectionsManager();

        resetConnections();
    }

    private void resetConnections() {
        firstConnectionMock = mock(WebSocket.Connection.class);
        when(firstConnectionMock.isOpen()).thenReturn(true);

        secondConnectionMock = mock(WebSocket.Connection.class);
        when(secondConnectionMock.isOpen()).thenReturn(true);

        thirdConnectionMock = mock(WebSocket.Connection.class);
        when(thirdConnectionMock.isOpen()).thenReturn(true);

        fourthConnectionMock = mock(WebSocket.Connection.class);
        when(fourthConnectionMock.isOpen()).thenReturn(true);

        fifthConnectionMock = mock(WebSocket.Connection.class);
        when(fifthConnectionMock.isOpen()).thenReturn(true);

        sixthConnectionMock = mock(WebSocket.Connection.class);
        when(sixthConnectionMock.isOpen()).thenReturn(true);
    }

    @Test
    public void shouldSendMessageToViewerConnectionWhenViewerAddedAndMessageSent() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(firstConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldSendOtherMessageToViewerConnectionWhenViewerAddedAndMessageSent() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, OTHER_MESSAGE);

        verify(firstConnectionMock).sendMessage(OTHER_MESSAGE);
    }

    @Test
    public void shouldNotSendSecondMessageWhenViewerDoThrow() throws Exception {
        doThrow(new IOException()).when(firstConnectionMock).sendMessage(MESSAGE);
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);
        connectionsManager.sendMessageToViewers(FIRST_GAME, OTHER_MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(OTHER_MESSAGE);
    }

    @Test
    public void shouldMessageSentToFirstViewerWhenAddedFirstMoverAddedSecondVieweer() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);
        connectionsManager.addViewer(FIRST_GAME, secondConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(firstConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldMessageSentToSecondViewerWhenAddedFirstMoverAddedSecondViewer() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);
        connectionsManager.addViewer(FIRST_GAME, secondConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(secondConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldNotSendMessageToClosedConnection() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        when(firstConnectionMock.isOpen()).thenReturn(false);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    @Test
    public void shouldClosedConnectionMustBeRemoved() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        when(firstConnectionMock.isOpen()).thenReturn(false).thenReturn(true);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);
        connectionsManager.sendMessageToViewers(FIRST_GAME, OTHER_MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(OTHER_MESSAGE);
    }

    @Test
    public void shouldNotifyOwnerConnectionWhenItAddedAndMessageSent() throws Exception {
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, firstConnectionMock);

        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);

        verify(firstConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldNotSendMessageToFirstConnectionWhenItAddedAsViewer() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    @Test
    public void shouldMessageSentToFirstPersonalConnectionWhenBothConnectionsArePersonal() throws Exception {
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, firstConnectionMock);
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, secondConnectionMock);

        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);

        verify(firstConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldMessageSentToSecondPersonalConnectionWhenBothConnectionsArePersonal() throws Exception {
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, firstConnectionMock);
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, secondConnectionMock);

        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);

        verify(secondConnectionMock).sendMessage(MESSAGE);
    }

    @Test
    public void shouldNoSendMessageToConnectionWhenSendingPersonalMessageToSecondOwnerAndConnectionsOwnerIsFirst() throws Exception {
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, firstConnectionMock);

        connectionsManager.sendPersonalMessage(SECOND_CONNECTION_OWNER_NAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    @Test
    public void shouldNotSendMessageToConnectionWhenItWasAddedAndRemovedBeforeSendingMessage() throws Exception {
        connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);

        connectionsManager.removeConnection(firstConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    @Test
    public void shouldNotSendMessageToPersonalConnectionWhenItWasAddedAndRemovedBeforeSendingMessage() throws Exception {
        connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, firstConnectionMock);

        connectionsManager.removeConnection(firstConnectionMock);

        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    static boolean concurrentModificationTestFailed = false;

    @Test
    public void shouldNoConcurrentModificationExceptionsWhen200TimesAddingRemovingConnectionsDuring200TimesMessageSending() throws Exception {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 200; i++) {
                    addConnections();
                    removeConnections();
                }
            }

            private void removeConnections() {
                connectionsManager.removeConnection(firstConnectionMock);
                connectionsManager.removeConnection(secondConnectionMock);
                connectionsManager.removeConnection(thirdConnectionMock);
                connectionsManager.removeConnection(fourthConnectionMock);
                connectionsManager.removeConnection(firstConnectionMock);
                connectionsManager.removeConnection(sixthConnectionMock);
            }

            private void addConnections() {
                connectionsManager.addViewer(FIRST_GAME, firstConnectionMock);
                connectionsManager.addViewer(FIRST_GAME, secondConnectionMock);

                connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, thirdConnectionMock);
                connectionsManager.addPersonalConnection(FIRST_CONNECTION_OWNER_NAME, fourthConnectionMock);

                connectionsManager.addPersonalConnection(SECOND_CONNECTION_OWNER_NAME, fifthConnectionMock);
                connectionsManager.addPersonalConnection(SECOND_CONNECTION_OWNER_NAME, sixthConnectionMock);
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 200; i++) {
                        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);
                        connectionsManager.sendPersonalMessage(FIRST_CONNECTION_OWNER_NAME, MESSAGE);
                        connectionsManager.sendPersonalMessage(SECOND_CONNECTION_OWNER_NAME, MESSAGE);
                    }
                } catch (ConcurrentModificationException e) {
                    ConnectionsManagerTest.concurrentModificationTestFailed = true;
                    e.printStackTrace();
                }
            }

        }.start();

        Thread.sleep(50);

        assertFalse(concurrentModificationTestFailed);
    }

    @Test
    public void shouldNotSendMessageToFirstViewerWhenItAddToSecondGameAndSendFirstGameInfo() throws Exception {
        connectionsManager.addViewer(SECOND_GAME, firstConnectionMock);

        connectionsManager.sendMessageToViewers(FIRST_GAME, MESSAGE);

        verify(firstConnectionMock, never()).sendMessage(MESSAGE);
    }

    @Test
    public void shouldSendMessageToFirstViewerWhenItAddToSecondGameAndSendSecondGameInfo() throws Exception {
        connectionsManager.addViewer(SECOND_GAME, firstConnectionMock);

        connectionsManager.sendMessageToViewers(SECOND_GAME, MESSAGE);

        verify(firstConnectionMock).sendMessage(MESSAGE);
    }
}
