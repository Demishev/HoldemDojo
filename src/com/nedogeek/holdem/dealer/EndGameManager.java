package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

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
        Player winner = findWinner();
        dealer.setPlayerWin(winner);
        giveMoneyToWinner(winner);
        dealer.setGameEnded();
    }

    private void giveMoneyToWinner(Player winner) {

        int prize = 0;
        for (Player player : playersList) {
            prize += player.getBet();
            player.setBet(0);
            if (player.getBalance() == 0) {
                player.setBalance(GameSettings.COINS_AT_START);
            }
        }
        winner.setBalance(winner.getBalance() + prize);
        System.out.println("Winner: " + winner + " prize " + prize);
    }

    private Player findWinner() {
        Player winCandidate = null;
        for (Player player : playersList) {
            if (isActivePlayer(player)) {
                if (winCandidate == null) {
                    winCandidate = player;
                } else {
                    if (winCandidate.getCardCombination().compareTo(player.getCardCombination()) < 0) {
                        winCandidate = player;
                    }
                }
            }
        }
        System.out.println(winCandidate.getCardCombination());
        return winCandidate;
    }

    private boolean isActivePlayer(Player player) {
        return player.getStatus() != PlayerStatus.Fold && player.getStatus() != PlayerStatus.Lost;
    }
}
