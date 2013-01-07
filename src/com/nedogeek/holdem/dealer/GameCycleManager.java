package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:36
 */
public class GameCycleManager {
    private final PlayersManager playersManager;
    private final Dealer dealer;

    public GameCycleManager(Dealer dealer, PlayersManager playersManager) {
        this.playersManager = playersManager;
        this.dealer = dealer;
    }


    void prepareNewGameCycle() {
        dealer.setGameStarted();

        for (Player player: playersManager.getPlayers()) {
            player.setBalance(GameSettings.COINS_AT_START);
        }
    }

    public void endGameCycle() {
    }
}
