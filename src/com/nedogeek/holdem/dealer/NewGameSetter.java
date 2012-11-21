package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:42
 */
public class NewGameSetter {
    private final Desk desk;
    private final MoveManager moveManager;
    private final PlayersManager playersManager;

    public NewGameSetter(Desk desk) {
        this.desk = desk;
        moveManager = new MoveManager(desk);
        playersManager = new PlayersManager(desk);
    }

    void setNewGame() {
        desk.shuffleCards();

        int dealerPlayerNumber = playersManager.nextPlayer(desk.getDealerPlayerNumber());
        desk.setDealerPlayer(dealerPlayerNumber);

        int smallBlindPlayerNumber = playersManager.nextPlayer(dealerPlayerNumber);
        makeStartBet(smallBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START);

        int bigBlindPlayerNumber = playersManager.nextPlayer(smallBlindPlayerNumber);
        makeStartBet(bigBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START * 2);

        desk.setNextGameRound();
    }

    private void makeStartBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        if (playerAmount > bet) {
            moveManager.makeInitialBet(playerNumber, bet);
        } else {
            moveManager.makeInitialBet(playerNumber, playerAmount);
        }
    }
}
