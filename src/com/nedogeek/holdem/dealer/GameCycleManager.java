package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Bank;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:36
 */
public class GameCycleManager {
    private final PlayersManager playersManager;
    private final Dealer dealer;
    private final Bank bank;

    public GameCycleManager(Dealer dealer, PlayersManager playersManager, Bank bank) {
        this.playersManager = playersManager;
        this.dealer = dealer;
        this.bank = bank;
    }


    void prepareNewGameCycle() {
        dealer.setGameStarted();
        int playersQuantity = playersManager.getPlayers().size();
        for (int i = 0; i < playersQuantity; i++) {
            bank.setPlayerAmount(i, GameSettings.COINS_AT_START);
        }
    }

    public void endGameCycle() {
    }
}
