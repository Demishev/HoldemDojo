package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;
import net.sf.json.JSONArray;


/**
 * User: Konstantin Demishev
 * Date: 16.03.13
 * Time: 19:24
 */
public class CardsGivingEvent extends Event  implements  PrivateEvent{
    private final String owner;

    public CardsGivingEvent(Player player) {
        super("Your cards: " + player.getCardCombination(), EventType.CARDS_GIVEN,
                JSONArray.fromArray(player.getCards()).toString());
        owner = player.getName();
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
