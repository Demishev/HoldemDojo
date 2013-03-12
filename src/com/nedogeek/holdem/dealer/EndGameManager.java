package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gameEvents.PlayerWinEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import java.util.Arrays;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:35
 */
public class EndGameManager {
    private final Dealer dealer;
    private final PlayersList playersList;
    private final EventManager eventManager;


    public EndGameManager(Dealer dealer, PlayersList playersList) {
        this.dealer = dealer;
        this.playersList = playersList;
        eventManager = EventManager.getInstance();
    }

    public EndGameManager(Dealer dealer, PlayersList playersList, EventManager eventManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.eventManager = eventManager;
    }

    public void endGame() {
        rewardWinners();

        endGameSleep();

        dealer.setInitialGameRound();
    }

    private void endGameSleep() {
        try {
            Thread.sleep(GameSettings.END_GAME_DELAY_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void rewardWinners() {
        Object[] winCandidates = playersList.toArray();
        Arrays.sort(winCandidates);

        for (int i = winCandidates.length - 1; i != -1; i--) {
            Player winPlayer = (Player) winCandidates[i];
            giveMoneyToWinner(winPlayer);
        }
        checkZeroBalance();
    }

    private void checkZeroBalance() {
        for (Player player : playersList) {
            if (player.getBalance() == 0) {
                player.setBalance(GameSettings.COINS_AT_START);
            }
        }
    }

    private void giveMoneyToWinner(Player winner) {
        int prize = 0;
        for (Player player : playersList) {
            if (player != winner) {
                prize += getChipsFromPlayer(player, winner.getBet());
            }
        }
        prize += winner.getBet();
        winner.setBet(0);
        winner.setBalance(winner.getBalance() + prize);
        if (prize != 0) {
            eventManager.addEvent(new PlayerWinEvent(winner, prize));
        }
    }

    private int getChipsFromPlayer(Player player, int chipsCount) {
        int playerBet = player.getBet();
        int chipsFromPlayer = (playerBet < chipsCount) ?
                playerBet : chipsCount;

        player.setBet(playerBet -= chipsFromPlayer);

        return chipsFromPlayer;
    }
}
