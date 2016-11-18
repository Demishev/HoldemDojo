package com.nedogeek.holdem.dealer;

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
