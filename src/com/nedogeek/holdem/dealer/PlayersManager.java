package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
public class PlayersManager {
    private final Desk desk;
    private final int playersQuantity;

    public PlayersManager(Desk desk) {
        this.desk = desk;
        playersQuantity = desk.getPlayersQuantity();
    }

    int nextPlayer(int playerNumber) {
        if (playerNumber == playersQuantity - 1) {
            return 0;
        } else {
            return playerNumber + 1;
        }
    }

    boolean hasAvailableMovers() {
        for (int i = 0; i < desk.getPlayersQuantity(); i++) {
            PlayerStatus playerStatus = desk.getPlayerStatus(i);
            if (playerStatus.equals(PlayerStatus.NotMoved)) {
                return true;
            }
        }
        return false;
    }
}
