package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;


/**
 * User: Konstantin Demishev
 * Date: 12.03.13
 * Time: 20:41
 */
public class PlayerConnectedEvent extends Event {
    public PlayerConnectedEvent(Player player) {
        super(player.getName() + " connected.");
    }
}
