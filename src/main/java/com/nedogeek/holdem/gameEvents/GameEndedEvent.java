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
public class GameEndedEvent extends Event {
    private final Map<Player, Integer> winners;

    public GameEndedEvent(Map<Player, Integer> winners) {
        super(generateEventMessage(winners));
        this.winners = winners;

    }

    private static String generateEventMessage(Map<Player, Integer> winners) {
        String message = "Game ended";

        for (Player winner : winners.keySet()) {
            message += '\n';
            message += winner.getName();
            message += " win $";
            message += winners.get(winner);
            message += " with ";
            message += winner.getCardCombination().toString();
        }

        return message;
    }

    public List<Player> getWinners() {
        return new ArrayList<>(winners.keySet());
    }
}
