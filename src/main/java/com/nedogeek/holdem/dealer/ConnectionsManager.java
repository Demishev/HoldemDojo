package com.nedogeek.holdem.dealer;

import org.eclipse.jetty.websocket.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: Konstantin Demishev
 * Date: 22.04.13
 * Time: 14:33
 */
public class ConnectionsManager {
    private List<WebSocket.Connection> viewers = new CopyOnWriteArrayList<>();

    private Map<String, List<WebSocket.Connection>> personalConnections = new HashMap<>();


    public void addViewer(WebSocket.Connection connection) {
        viewers.add(connection);
    }

    public void sendMessageToViewers(String message) {
        sendMessageToConnectionList(message, viewers);
    }

    private void sendMessageToConnectionList(String message, List<WebSocket.Connection> connectionList) {
        for (WebSocket.Connection connection : connectionList) {
            try {
                if (connection.isOpen()) {
                    connection.sendMessage(message);
                } else {
                    connectionList.remove(connection);
                }
            } catch (IOException ignored) {
                connectionList.remove(connection);
            }
        }
    }

    public void addPersonalConnection(String ownerName, WebSocket.Connection connection) {
        if (!personalConnections.containsKey(ownerName)) {
            addConnectionsList(ownerName);
        }

        personalConnections.get(ownerName).add(connection);
    }

    private void addConnectionsList(String ownerName) {
        personalConnections.put(ownerName, new CopyOnWriteArrayList<WebSocket.Connection>());
    }

    public void sendPersonalMessage(String ownerName, String message) {
        if (personalConnections.containsKey(ownerName)) {
            sendMessageToConnectionList(message, personalConnections.get(ownerName));
        }
    }

    public void removeConnection(WebSocket.Connection connection) {
        for (String owner : personalConnections.keySet()) {
            personalConnections.get(owner).remove(connection);
        }
        viewers.remove(connection);
    }
}
