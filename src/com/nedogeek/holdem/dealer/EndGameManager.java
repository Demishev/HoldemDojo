package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import com.nedogeek.holdem.gamingStuff.Player;

import java.util.List;

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
        desk.setPlayerWin(findWinner());
        desk.setGameEnded();
    }

    private Player findWinner() {
        List<Player> players = playersManager.getPlayers();
        Player winner = null;
        for (Player player: players) {
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
