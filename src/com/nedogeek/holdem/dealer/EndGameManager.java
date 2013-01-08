package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:35
 */
public class EndGameManager {
    private final Dealer dealer;
    private final PlayersList playersList;


    public EndGameManager(Dealer dealer, PlayersList playersList) {
        this.dealer = dealer;
        this.playersList = playersList;
    }

    public void endGame() {
        dealer.setPlayerWin(findWinner());
        dealer.setGameEnded();
    }

    private Player findWinner() {
        Player winner = null;
        for (Player player: playersList) {
            if (isActivePlayer(player)) {
                if (winner == null) {
                    winner = player;
                } else {
                    if (winner.getCardCombination().compareTo(player.getCardCombination()) < 0) {
                        winner = player;
                    }
                }
            }
        }
        return winner;
    }

    private boolean isActivePlayer(Player player) {
        return player.getStatus() != PlayerStatus.Fold && player.getStatus() != PlayerStatus.Lost;
    }
}
