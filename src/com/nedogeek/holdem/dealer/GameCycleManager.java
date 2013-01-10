package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:36
 */
public class GameCycleManager {
    private final PlayersList playersManager;
    private final Dealer dealer;

    public GameCycleManager(Dealer dealer, PlayersList playersManager) {
        this.playersManager = playersManager;
        this.dealer = dealer;
    }


    void prepareNewGameCycle() {
        dealer.setGameStarted();

        for (Player player: playersManager) {
            player.setBalance(GameSettings.COINS_AT_START);
        }
    }

    public void endGameCycle() {
    }
}
