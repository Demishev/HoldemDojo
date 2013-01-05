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
    private final PlayersManager playersManager;


    public EndGameManager(Desk desk, PlayersManager playersManager) {
        this.desk = desk;
        this.playersManager = playersManager;
    }

    public void endGame() {
        int winnerNumber = findWinner();

        desk.setPlayerWin(winnerNumber);
        desk.setGameEnded();
    }

    private int findWinner() {
        int winnerNumber = -1;
        for (int i = 0; i < playersManager.getPlayersQuantity(); i++) {
            if (playersManager.getPlayerStatus(i) != PlayerStatus.Fold) {
                if (winnerNumber == -1) {
                    winnerNumber = i;
                } else {
                    if (playersManager.isFirstCombinationBiggerThanSecond(i, winnerNumber)) {
                        winnerNumber = i;
                    }
                }
            }
        }

        return winnerNumber;
    }
}
