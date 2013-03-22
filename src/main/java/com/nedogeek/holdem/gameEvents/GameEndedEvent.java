package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 22.03.13
 * Time: 12:01
 */
public class GameEndedEvent extends Event {//TODO test toString()
    private final Map<Player, Integer> winners;

    public GameEndedEvent(Map<Player, Integer> winners) {
        super("Game ended");
        this.winners = winners;
    }

    public List<Player> getWinners() {
        return new ArrayList<>(winners.keySet());
    }
}
