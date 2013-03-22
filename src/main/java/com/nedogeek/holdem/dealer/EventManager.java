package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import net.sf.json.JSONObject;
import org.eclipse.jetty.websocket.WebSocket.Connection;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * User: Konstantin Demishev
 * Date: 08.02.13
 * Time: 22:10
 */
public class EventManager implements Serializable {
    private Map<String, String> userData = new HashMap<>();  //TODO Maybe move field?

    {
        userData.put("User", "Password");   //TODO remove it later.
    }

    private static final EventManager eventManager = new EventManager();

    private PlayersList playersList;
    private Dealer dealer;
    private Event event;

    private Map<String, List<Connection>> connections = new Hashtable<>();

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
        for (String connectionName : connections.keySet()) {
            String message = gameToJSON(connectionName);
            sendMessageToPlayer(message, connectionName);
        }


//        for (List<Connection> connectionList : connections.values()) {
//            for (Connection connection : connectionList) {
//                try {
//                    connection.sendMessage(gameToJSON(connectionName));
//                } catch (IOException e) {
//                    connection.close();
//                }
//            }
//            removeClosedConnections(connectionList);
//        }
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

    public String gameToJSON(String connectionName) {


        String playersJSON = generatePlayersJSON(connectionName);

        String gameStatus = dealer.getGameStatus().toString();
        String gameRound = dealer.getGameRound().toString();
        int pot = playersList.getPot();
        String deskCards = Arrays.toString(dealer.getDeskCards());
        String event = this.event.toString();

        String dealerName = playersList.getDealerName();
        String moverName = playersList.getMoverName();


        Map<String, Serializable> gameData = new HashMap<>();
        gameData.put("gameStatus", gameStatus);
        gameData.put("gameRound", gameRound);

        gameData.put("players", playersJSON);
        gameData.put("mover", moverName);
        gameData.put("dealer", dealerName);

        gameData.put("deskCards", deskCards);
        gameData.put("deskPot", pot);

        gameData.put("event", event);
//
//        gameData.put("dealerNumber", playersList.getDealerNumber());
//        gameData.put("moverNumber", moverNumber);
//        gameData.put("gameStatus", dealer.getGameStatus());
//
//        gameData.put("events", events.toArray());
//        gameData.put("lastEvent", event.toJSON());

        return JSONObject.fromMap(gameData).toString();
    }

    private String generatePlayersJSON(String connectionName) {
        List<String> playersWithCards = new ArrayList<>();

        if (!connectionName.equals("public")) {
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

    public Map<String, String> getUserData() {
        return userData;
    }
}
