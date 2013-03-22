package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;


/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:43
 */
public class AddPlayerEvent extends Event {
    public AddPlayerEvent(Player player) {
        super(player.getName() + " set to desk.");
    }
}
