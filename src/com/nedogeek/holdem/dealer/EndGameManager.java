package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:35
 */
public class EndGameManager {
    private final Desk desk;

    public EndGameManager(Desk desk) {
        this.desk = desk;
    }

    public void endGame() {
        int winnerNumber = findWinner();

        desk.setPlayerWin(winnerNumber);
        desk.setGameEnded();
    }

    private int findWinner() {
        if (desk.getPlayerStatus(1) == PlayerStatus.Fold) {
            return 0;
        } else {
            return 1;
        }
    }
}
