package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 22.03.13
 * Time: 12:01
 */
public class GameEndedEvent extends Event {         //TODO test it in EndGameManager
    private final List<Player> winners;

    public GameEndedEvent(List<Player> winners) {
        super("Game ended");
        this.winners = winners;
    }

    public List<Player> getWinners() {
        return winners;
    }
}
