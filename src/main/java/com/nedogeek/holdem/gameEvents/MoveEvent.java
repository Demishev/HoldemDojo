package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:55
 */
public class MoveEvent extends Event {
    public MoveEvent(Player player) {
        super(generateMessage(player, player.getMove().getActionType(), player.getMove().getRiseAmount()));
    }

    private static String generateMessage(Player player, PlayerAction.ActionType moveType, int bet) {
        switch (moveType) {
            case AllIn:
                return player.getName() + " goes All in.";
            case Call:
                return player.getName() + " makes call.";
            case Fold:
                return player.getName() + " folds.";
            case Check:
                return player.getName() + " checks.";
            case Rise:
                return player.getName() + " makes bet " + bet;
            default:
                return "NULL message.";
        }
    }
}
