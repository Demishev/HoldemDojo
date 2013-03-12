package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.PlayerMovesEvent;
import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import net.sf.json.JSONObject;
import org.eclipse.jetty.websocket.WebSocket.Connection;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 08.02.13
 * Time: 22:10
 */
public class EventManager {
    private static final EventManager eventManager = new EventManager();

    private PlayersList playersList;
    private Dealer dealer;
    private int moverNumber = -1;

    private final List<String> events = new ArrayList<>();

    private Map<String, List<Connection>> connections = new HashMap<>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public static EventManager getTestInstance() {
        return new EventManager();
    }

    private EventManager() {

    }

    public void addViewer(Connection viewer) {
        addConnection("public", viewer);
    }

    private void addConnection(String owner, Connection connection) {
        if (!connections.containsKey(owner)) {
            connections.put(owner, new ArrayList<Connection>());
        }
        connections.get(owner).add(connection);
    }

    private void notifyConnections() {
        for (List<Connection> connectionList : connections.values()) {
            for (Connection connection : connectionList) {
                try {
                    connection.sendMessage(gameToJSON());
                } catch (IOException e) {
                    connection.close();
                }
            }
            removeClosedConnections(connectionList);
        }
    }

    public void addEvent(Event event) {
        processEvents(event);

        events.add(event.toString());
        if (events.size() > GameSettings.MAX_EVENTS_COUNT)
            events.remove(0);

        notifyConnections();
    }

    private void processEvents(Event event) {
        if (event instanceof PlayerMovesEvent) {
            moverNumber = ((PlayerMovesEvent) event).getMoverNumber();
        }

        if (event instanceof AddPlayerEvent || event instanceof RemovePlayerEvent) {
            dealer.calculateGameStatus();
        }
    }

    private void removeClosedConnections(List<Connection> connections) {
        for (Connection connection : connections) {
            if (!connection.isOpen()) {
                connections.remove(connection);
                removeClosedConnections(connections);
                return;
            }
        }
    }

    public void closeConnection(Connection connection) {
        connection.close();

        for (List<Connection> connectionList : connections.values()) {
            if (connectionList.contains(connection)) {
                connectionList.remove(connection);
            }
        }
    }

    public String gameToJSON() {
        Map<String, Serializable> gameData = new HashMap<>();
        gameData.put("gameStatus", dealer.getGameStatus());
        gameData.put("deskCards", dealer.getDeskCards());
        gameData.put("players", playersList.toJSON());
        gameData.put("dealerNumber", playersList.getDealerNumber());
        gameData.put("gameStatus", dealer.getGameStatus());
        gameData.put("gameRound", dealer.getGameRound());
        gameData.put("events", events.toArray());
        gameData.put("moverNumber", moverNumber);

        return JSONObject.fromMap(gameData).toString();
    }

    public Player addPlayer(Connection connection, String login) {
        addConnection(login, connection);

        Player player = new Player(login, dealer);
        if (playersList.contains(player))
            return player;   //TODO Ересь!
        return null;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setPlayersList(PlayersList playersList) {
        this.playersList = playersList;
    }
}
