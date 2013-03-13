package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:07
 */
public class PlayerWinEvent extends Event {
    public PlayerWinEvent(Player winner, int prize) {
        super(winner.getName() + " win " + prize +
                " chips with " + winner.getCardCombination() + '.');
    }
}
