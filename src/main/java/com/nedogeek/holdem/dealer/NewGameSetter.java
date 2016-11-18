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


import com.nedogeek.holdem.gameEvents.NewGameStartedEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:42
 */
class NewGameSetter {
    private final Dealer dealer;
    private final MoveManager moveManager;
    private final PlayersList playersList;
    private final EventManager eventManager;

    public NewGameSetter(Dealer dealer, PlayersList playersList, MoveManager moveManager,
                         EventManager eventManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.moveManager = moveManager;
        this.eventManager = eventManager;
    }

    void setNewGame() {
        if (playersList.size() > 1) {
            dealer.setCallValue(0);
            playersList.setNewGame();

            resetCards();
            makeInitialBets();


            dealer.setNextGameRound();
            eventManager.addEvent(new NewGameStartedEvent());
        }
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
