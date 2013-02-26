package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
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
        dealer.resetCards();
        playersList.changeDealer();
        resetPlayers();
        makeInitialBets();

        dealer.setNextGameRound();
    }

    private void resetPlayers() {
        for (Player player : playersList) {
            dealer.giveCardsToPlayer(player);
            player.setStatus(PlayerStatus.NotMoved);
        }
    }

    private void makeInitialBets() {
        moveManager.makeSmallBlind(playersList.smallBlindPlayer());
        System.out.println("Small blind player is: " + playersList.smallBlindPlayer());
        moveManager.makeBigBlind(playersList.bigBlindPlayer());
        System.out.println("Big blind player is: " + playersList.bigBlindPlayer());
    }
}
