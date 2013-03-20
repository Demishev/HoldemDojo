package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:28
 */
public class PlayerMovesNotificationEvent extends Event {
    private final int moverNumber;
    private final Player player;

    public PlayerMovesNotificationEvent(Player mover, PlayersList players) {
        super(mover.getName() + " moves.", EventType.MOVER_IS, generateJSON(mover, players));

        moverNumber = players.indexOf(mover);
        player = mover;
    }

    private static String generateJSON(Player mover, PlayersList players) {
        Map<String , Serializable> data = new HashMap<>();
        data.put("mover", mover.getName());
        data.put("moverNumber", players.indexOf(mover));

        return JSONObject.fromMap(data).toString();
    }

    public int getMoverNumber() {
        return moverNumber;
    }

    public Player getPlayer() {
        return player;
    }
}
