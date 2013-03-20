package com.nedogeek.holdem.gameEvents;

import com.nedogeek.holdem.gamingStuff.Player;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 18:07
 */
public class PlayerWinEvent extends Event {
    public PlayerWinEvent(Player winner, int prize) {
        super(generateToString(winner, prize), EventType.PLAYER_WIN, generateJSON(winner));
    }

    private static String generateJSON(Player winner) {
        Map<String, Serializable> data = new HashMap<>();
        data.put("winner", winner.getName());
        data.put("cards", winner.getCards());

        return JSONObject.fromMap(data).toString();
    }

    private static String generateToString(Player winner, int prize) {
        return winner.getName() + " win " + prize +
                " chips with " + winner.getCardCombination() + '.';
    }
}
