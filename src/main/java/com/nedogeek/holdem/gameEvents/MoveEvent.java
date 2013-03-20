package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:55
 */
public class MoveEvent extends Event {
    public MoveEvent(Player player) {
        super(generateMessage(player, player.getMove().getActionType(), player.getMove().getRiseAmount()),
                EventType.MOVE_EVENT, generateJSON(player));
    }

    private static String generateJSON(Player player) {
        Map<String , Serializable> moveData = new HashMap<>();
        moveData.put("mover", player.getName());
        moveData.put("moveType", player.getMove().getActionType());
        moveData.put("riseAmount", player.getMove().getRiseAmount());

        return JSONObject.fromMap(moveData).toString();
    }

    private static String generateMessage(Player player, PlayerAction.ActionType moveType, int bet) {
        switch (moveType) {
            case AllIn:
                return player.getName() + " goes All in.";
            case Call:
                return player.getName() + "makes call.";
            case Fold:
                return player.getName() + "folds.";
            case Check:
                return player.getName() + "checks.";
            case Rise:
                return player.getName() + "makes bet " + bet;
            default:
                return "NULL message.";
        }
    }
}
