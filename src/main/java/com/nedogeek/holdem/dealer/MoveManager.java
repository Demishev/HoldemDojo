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
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gameEvents.MoveEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
class MoveManager {
    private final Dealer dealer;
    private final PlayersList playersList;
    private final EventManager eventManager;

    MoveManager(Dealer dealer, PlayersList playersList, EventManager eventManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.eventManager = eventManager;
    }

    void makeMove(Player mover) {
        delay();
        PlayerAction playerMove = mover.getMove();
        playersList.playerMoved(mover);
        eventManager.addEvent(new MoveEvent(mover));
        switch (playerMove.getActionType()) {
            case Fold:
                makeFold(mover);
                break;
            case Check:
                makeCheck(mover);
                break;
            case Call:
                makeCall(mover);
                break;
            case Rise:
                makeRise(mover);
                break;
            case AllIn:
                makeAllIn(mover);
                break;
            default:
                makeFold(mover);
        }
    }

    private void delay() {
        try {
            Thread.sleep(GameSettings.getGameDelayValue());
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    private void makeCheck(Player player) {
        if (dealer.getCallValue() <= player.getBet()) {
            player.setStatus(PlayerStatus.Check);
        } else {
            player.setStatus(PlayerStatus.Fold);
        }
    }

    private void trySendToPot(Player player, int betValue) {
        final int playerAmount = player.getBalance();
        if (playerAmount <= betValue) {
            sendToPot(player, playerAmount);
            player.setStatus(PlayerStatus.AllIn);
        } else {
            sendToPot(player, betValue);
        }
    }

    private void sendToPot(Player player, int chips) {
        final int playerAmount = player.getBalance();
        final int playerBet = chips + player.getBet();

        player.setBet(playerBet);
        player.setBalance(playerAmount - chips);
        checkCallValue(playerBet);
    }


    private void checkCallValue(int playerBet) {
        if (dealer.getCallValue() < playerBet) {
            dealer.setCallValue(playerBet);
        }
    }

    private void makeFold(Player player) {
        player.setStatus(PlayerStatus.Fold);

    }

    private void makeCall(Player player) {
        player.setStatus(PlayerStatus.Call);
        trySendToPot(player, dealer.getCallValue() - player.getBet());
    }

    private void makeAllIn(Player player) {
        trySendToPot(player, player.getBalance());
    }

    private void makeRise(Player player) {
        int riseValue = calculateRiseValue(player);
        player.setStatus(PlayerStatus.Rise);
        trySendToPot(player, riseValue);
    }

    private int calculateRiseValue(Player player) {
        int playerWantRise = player.getMove().getRiseAmount();
        int minimumRiseValue = dealer.getCallValue() + 2 * GameSettings.getSmallBlind();

        return (playerWantRise < minimumRiseValue) ? minimumRiseValue : playerWantRise;
    }

    public void makeSmallBlind(Player player) {
        player.setStatus(PlayerStatus.SmallBLind);

        trySendToPot(player, GameSettings.getSmallBlind());
        playersList.playerMoved(player);
    }

    public void makeBigBlind(Player player) {
        player.setStatus(PlayerStatus.BigBlind);

        trySendToPot(player, 2 * GameSettings.getSmallBlind());
        playersList.playerMoved(player);
    }
}
