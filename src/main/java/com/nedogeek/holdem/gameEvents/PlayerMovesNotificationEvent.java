package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:28
 */
public class PlayerMovesNotificationEvent extends Event {
    public PlayerMovesNotificationEvent(Player mover) {
        super(mover.getName() + " moves.");
    }
}
