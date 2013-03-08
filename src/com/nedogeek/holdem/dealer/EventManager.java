package com.nedogeek.holdem.dealer;

import org.eclipse.jetty.websocket.WebSocket.Connection;

import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import net.sf.json.JSONObject;

import java.io.IOException;
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

    private Connection viewer;
    private PlayersList playersList;
    private Dealer dealer;
    private List<String> events = new ArrayList<String>();

    public static EventManager getInstance() {
        return eventManager;
    }

    public void addEvent(Event event) {
    	events.add(event.toString());
    	if(events.size() > 10 )
    		events.remove(0);
     
        if (viewer != null)
        try {
			viewer.sendMessage(event + "\n"+ gameToJSON());
			
		} catch (IOException e) {

			e.printStackTrace();
		}
    }  

    public void setPlayersList(PlayersList playersList) {
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

	public void setViewer(Connection connection) {
		viewer = connection;		
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;		
	}
}
