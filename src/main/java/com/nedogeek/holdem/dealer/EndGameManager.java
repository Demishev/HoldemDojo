package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<Player, Integer> winners = rewardWinners();

        endGameSleep();

        eventManager.addEvent(new GameEndedEvent(winners));

        dealer.setInitialGameRound();
    }

    private void endGameSleep() {
        try {
            Thread.sleep(GameSettings.END_GAME_DELAY_VALUE);
        } catch (InterruptedException ignored) {

        }
    }

    private Map<Player, Integer> rewardWinners() {
        Map<Player, Integer> winners = new LinkedHashMap<>();

        Object[] winCandidates = playersList.toArray();
        Arrays.sort(winCandidates);

        for (int i = winCandidates.length - 1; i != -1; i--) {
            Player winPlayer = (Player) winCandidates[i];
            int prize = giveMoneyToWinner(winPlayer);
            if (prize > 0) {
                winners.put(winPlayer, prize);
            }
        }
        checkZeroBalance();//TODO Need to create new SystemEvent

        return winners;
    }

    private void checkZeroBalance() {
        for (Player player : playersList) {
            if (player.getBalance() == 0) {
                player.setBalance(GameSettings.COINS_AT_START);
            }
        }
    }

    private int giveMoneyToWinner(Player winner) {
        int prize = 0;
        for (Player player : playersList) {
            if (player != winner) {
                prize += getChipsFromPlayer(player, winner.getBet());
            }
        }
        prize += winner.getBet();
        winner.setBet(0);
        winner.setBalance(winner.getBalance() + prize);
        return prize;
    }

    private int getChipsFromPlayer(Player player, int chipsCount) {
        int playerBet = player.getBet();
        int chipsFromPlayer = (playerBet < chipsCount) ?
                playerBet : chipsCount;

        player.setBet(playerBet -= chipsFromPlayer);

        return chipsFromPlayer;
    }
}
