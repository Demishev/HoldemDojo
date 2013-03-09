package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.PlayerMovesEvent;
import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;
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
    private final List<Connection> viewers = new ArrayList<>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public static EventManager getTestInstance() {
        return new EventManager();
    }

    private EventManager() {

    }

    public void addViewer(Connection viewer) {
        viewers.add(viewer);
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setPlayersList(PlayersList playersList) {
        this.playersList = playersList;
    }

    public void addEvent(Event event) {
        processEvents(event);

        events.add(event.toString());
        final int MAX_EVENTS_COUNT = 10;
        if (events.size() > MAX_EVENTS_COUNT)
            events.remove(0);

        notifyViewer();
    }

    private void processEvents(Event event) {
        if (event instanceof PlayerMovesEvent) {
            moverNumber = ((PlayerMovesEvent) event).getMoverNumber();
        }

        if (event instanceof AddPlayerEvent || event instanceof RemovePlayerEvent) {
            dealer.calculateGameStatus();
        }
    }

    private void notifyViewer() {
        for (Connection viewer : viewers) {
            try {
                viewer.sendMessage(gameToJSON());
            } catch (IOException e) {
                viewer.close();
            }
        }
        removeClosedConnections();
    }

    private void removeClosedConnections() {
        for (Connection viewer : viewers) {
            if (!viewer.isOpen()) {
                viewers.remove(viewer);
                removeClosedConnections();
                return;
            }
        }
    }

    public void closeConnection(Connection viewer) {
        viewer.close();
        viewers.remove(viewer);
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
}
