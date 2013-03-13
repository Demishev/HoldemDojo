package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:47
 */
public class RemovePlayerEvent extends Event {
    public RemovePlayerEvent(Player player) {
        super(player.getName() + " disconnected.");
    }
}
