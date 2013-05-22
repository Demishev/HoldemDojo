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
    private Map<String, List<WebSocket.Connection>> viewers = new HashMap<>();

    private Map<String, List<WebSocket.Connection>> personalConnections = new HashMap<>();


    public void addViewer(String gameID, WebSocket.Connection connection) {
        addConnection(gameID, connection, viewers);
    }

    public void sendMessageToViewers(String gameID, String message) {
        sendMessage(gameID, message, viewers);
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
        addConnection(ownerName, connection, personalConnections);
    }

    private void addConnection(String ownerName, WebSocket.Connection connection, Map<String, List<WebSocket.Connection>> connectionsList) {
        if (!connectionsList.containsKey(ownerName)) {
            addConnectionsList(ownerName, connectionsList);
        }

        connectionsList.get(ownerName).add(connection);
    }

    private void addConnectionsList(String ownerName, Map<String, List<WebSocket.Connection>> connectionsList) {
        connectionsList.put(ownerName, new CopyOnWriteArrayList<WebSocket.Connection>());
    }

    public void sendPersonalMessage(String ownerName, String message) {
        sendMessage(ownerName, message, personalConnections);
    }

    private void sendMessage(String ownerName, String message, Map<String, List<WebSocket.Connection>> listMap) {
        if (listMap.containsKey(ownerName)) {
            sendMessageToConnectionList(message, listMap.get(ownerName));
        }
    }

    public void removeConnection(WebSocket.Connection connection) {
        removeConnectionFromCollection(connection, personalConnections);
        removeConnectionFromCollection(connection, viewers);
    }

    private void removeConnectionFromCollection(WebSocket.Connection connection, Map<String, List<WebSocket.Connection>> listMap) {
        for (String owner : listMap.keySet()) {
            listMap.get(owner).remove(connection);
        }
    }
}
