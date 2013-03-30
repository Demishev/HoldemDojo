package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;
import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket.Connection;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: Konstantin Demishev
 * Date: 08.02.13
 * Time: 22:10
 */
public class EventManager implements Serializable {
    private final String PUBLIC = "public";
    private static final EventManager eventManager = new EventManager();

    private PlayersList playersList;
    private Dealer dealer;
    private Event event;

    private final Map<String, List<Connection>> connections = new ConcurrentHashMap<>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public static EventManager getTestInstance() {
        return new EventManager();
    }

    private EventManager() {

    }

    public void addViewer(Connection viewer) {
        addConnection(PUBLIC, viewer);
    }

    private void addConnection(String owner, Connection connection) {
        if (!connections.containsKey(owner)) {
            connections.put(owner, new CopyOnWriteArrayList<Connection>());
        }
        connections.get(owner).add(connection);
    }

    private void notifyConnections() {
        for (String connectionName : connections.keySet()) {
            String message = gameToJSON(connectionName);
            sendMessageToPlayer(message, connectionName);
            removeClosedConnections(connectionName);
        }

    }

    public void addEvent(Event event) {
        processEvents(event);

        this.event = event;

        notifyConnections();
    }


    private void sendMessageToPlayer(String message, String playerName) {
        List<Connection> playerConnections = connections.get(playerName);
        for (Connection connection : playerConnections) {
            try {
                connection.sendMessage(message);
            } catch (IOException e) {
                connection.close();
            }
        }
    }

    private void processEvents(Event event) {
        if (event instanceof AddPlayerEvent || event instanceof RemovePlayerEvent) {
            dealer.calculateGameStatus();
        }
    }

    private void removeClosedConnections(String connectionsName) {
        for (Connection connection : connections.get(connectionsName)) {
            if (!connection.isOpen()) {
                connections.get(connectionsName).remove(connection);
                removeClosedConnections(connectionsName);
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

    String gameToJSON(String connectionName) {
        String playersJSON = generatePlayersJSON(connectionName);

        String gameStatus = dealer.getGameStatus().toString();
        String gameRound = dealer.getGameRound().toString();
        int pot = playersList.getPot();
        String deskCards = generateCardsJSON();
        String event = this.event.toJSON();

        String dealerName = playersList.getDealerName();
        String moverName = playersList.getMoverName();

        String message = "{";
        message += "\"gameRound\":\"" + gameRound + "\"";
        message += "," + "\"dealer\":\"" + dealerName + "\"";
        message += "," + "\"mover\":\"" + moverName + "\"";
        message += "," + "\"event\":" + event;
        message += "," + "\"players\":" + playersJSON;
        if (!connectionName.equals(PUBLIC)) {
            message += "," + "\"combination\":\"" + playersList.getPlayerCardCombination(connectionName) + "\"";
        }
        message += "," + "\"gameStatus\":\"" + gameStatus + "\"";

        message += "," + "\"deskCards\":" + deskCards;
        message += "," + "\"deskPot\":" + pot;


        message += "}";
        return message;
    }

    private String generateCardsJSON() {
        String cardsJSON = "[";

        if (dealer.getDeskCards().length > 0) {
            for (Card card : dealer.getDeskCards()) {
                cardsJSON += card.toJSON() + ",";
            }
            cardsJSON = cardsJSON.substring(0, cardsJSON.length() - 1);
        }
        cardsJSON += "]";

        return cardsJSON;
    }

    private String generatePlayersJSON(String connectionName) {
        List<String> playersWithCards = new ArrayList<>();

        if (!connectionName.equals(PUBLIC)) {
            playersWithCards.add(connectionName);
        }

        if (event instanceof GameEndedEvent) {
            List<Player> winners = ((GameEndedEvent) event).getWinners();
            for (Player winner : winners) {
                String winnerName = winner.getName();
                if (!connectionName.equals(winnerName)) {
                    playersWithCards.add(winnerName);
                }
            }
        }

        String[] playerNamesArray = new String[playersWithCards.size()];
        for (int i = 0; i < playersWithCards.size(); i++) {
            playerNamesArray[i] = playersWithCards.get(i);
        }

        return playersList.generatePlayersJSON(playerNamesArray);
    }

    public Player addPlayer(Connection connection, String login) {
        addConnection(login, connection);

        return playersList.getPlayerByName(login, dealer);
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setPlayersList(PlayersList playersList) {
        this.playersList = playersList;
    }
}
