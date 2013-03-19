package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 16.03.13
 * Time: 19:24
 */
public class CardsGivingEvent extends Event  implements  PrivateEvent{
    private final String owner;

    public CardsGivingEvent(Player player) {
        super("Your cards: " + player.getCardCombination());
        owner = player.getName();
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
