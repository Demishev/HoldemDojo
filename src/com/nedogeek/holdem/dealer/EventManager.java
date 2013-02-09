package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import net.sf.json.JSONObject;

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
    private List<String> events = new ArrayList<String>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public void addEvent(Event event) {
        events.add(event.toString());
    }

    public String getPlayersNames() {
        String names = null;
        for (Player player : playersList) {
            if (names == null) {
                names = player.getName();
            } else {
                names += "," + player.getName();
            }
        }
        return names;
    }

    public void collectPlayers(PlayersList playersList) {
        this.playersList = playersList;
    }

    public String gameToJSON() {
        Map gameData = new HashMap();
        gameData.put("players", playersList.toJSON());
        gameData.put("dealerNumber", playersList.getDealerNumber());
        gameData.put("gameStatus", dealer.getGameStatus());
        gameData.put("gameRound", dealer.getGameRound());
        gameData.put("events", events.toArray());

        return JSONObject.fromMap(gameData).toString();
    }
}
