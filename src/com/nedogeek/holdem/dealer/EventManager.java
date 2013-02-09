package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 08.02.13
 * Time: 22:10
 */
public class EventManager {
    private static final EventManager eventManager = new EventManager();
    private PlayersList playersList;

    public static EventManager getInstance() {

        return eventManager;
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
}
