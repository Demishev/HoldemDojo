package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:42
 */
public class NewGameSetter {
    private final Dealer dealer;
    private final MoveManager moveManager;
    private final PlayersList playersList;

    public NewGameSetter(Dealer dealer, PlayersList playersList, MoveManager moveManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.moveManager = moveManager;
    }

    void setNewGame() {
        resetCards();
        makeInitialBets();

        playersList.setNewGame();

        dealer.setNextGameRound();
    }

    private void resetCards() {
        dealer.resetCards();

        for (Player player : playersList) {
            dealer.giveCardsToPlayer(player);
        }
    }

    private void makeInitialBets() {
        moveManager.makeSmallBlind(playersList.smallBlindPlayer());
        moveManager.makeBigBlind(playersList.bigBlindPlayer());
    }
}
