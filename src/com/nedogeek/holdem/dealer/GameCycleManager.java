package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:36
 */
public class GameCycleManager {
    private final Desk desk;

    public GameCycleManager(Desk desk) {
        this.desk = desk;
    }


    void prepareNewGameCycle() {
        desk.setGameStatus(GameStatus.STARTED);
        int playersQuantity = desk.getPlayersQuantity();
        for (int i = 0; i < playersQuantity; i++) {
            desk.setPlayerAmount(i, GameSettings.COINS_AT_START);
        }
    }

    public void endGameCycle() {
    }
}
