package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameCenter;
import org.eclipse.jetty.websocket.WebSocket;

/**
 * User: Konstantin Demishev
 * Date: 30.05.13
 * Time: 2:43
 */
public class ViewerWebSocket implements WebSocket.OnTextMessage {
    private final GameCenter gameCenter;
    private final String gameID;

    public ViewerWebSocket(String gameId, GameCenter gameCenter) {
        this.gameID = gameId;
        this.gameCenter = gameCenter;
    }

    @Override
    public void onMessage(String data) {
    }

    @Override
    public void onOpen(Connection connection) {
        gameCenter.connectViewer(gameID, connection);
    }

    @Override
    public void onClose(int closeCode, String message) {
    }
}
