package com.nedogeek.holdem.dealer;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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
class EndGameManager {
    private final Dealer dealer;
    private final PlayersList playersList;
    private final EventManager eventManager;

    public EndGameManager(Dealer dealer, PlayersList playersList, EventManager eventManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.eventManager = eventManager;
    }

    public void endGame() {
        Map<Player, Integer> winners = rewardWinners();

        eventManager.addEvent(new GameEndedEvent(winners));
        endGameSleep();

        dealer.setInitialGameRound();
    }

    private void endGameSleep() {
        try {
            Thread.sleep(GameSettings.getEndGameDelayValue());
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
        checkZeroBalance();//TODO Need to create new SystemEvent to remove players without chips and connections

        return winners;
    }

    private void checkZeroBalance() {
        for (Player player : playersList) {
            if (player.getBalance() == 0) {
                player.setBalance(GameSettings.getCoinsAtStart());
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
