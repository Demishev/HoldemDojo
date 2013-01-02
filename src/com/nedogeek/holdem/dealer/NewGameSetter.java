package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
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
    private final Bank bank;

    public NewGameSetter(Desk desk, PlayersManager playersManager, Bank bank) {
        this.desk = desk;
        this.playersManager = playersManager;
        this.bank = bank;
        moveManager = new MoveManager(bank, new PlayersManager(bank));
    }

    void setNewGame() {
        desk.resetCards();
        makeInitialBets(defineDealer());

        setInitialPlayerStatuses();
        giveCardsToPlayers();

        desk.setNextGameRound();
    }

    private void giveCardsToPlayers() {
        for (int i = 0; i < playersManager.getPlayersQuantity(); i++) {
            if (playersManager.getPlayerStatus(i) != PlayerStatus.Lost) {
                desk.giveCardsToPlayer(i);
            }
        }
    }

    private void setInitialPlayerStatuses() {
        for (int i = 0; i < playersManager.getPlayersQuantity(); i++) {
            if (playersManager.getPlayerStatus(i) != PlayerStatus.Lost) {
                playersManager.setPlayerStatus(i, PlayerStatus.NotMoved);
            }
        }
    }

    private int defineDealer() {
        playersManager.changeDealer();

        return playersManager.getDealerNumber();
    }

    private void makeInitialBets(int dealerPlayerNumber) {
        int smallBlindPlayerNumber = playersManager.nextPlayer(dealerPlayerNumber);
        makeStartBet(smallBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START);

        int bigBlindPlayerNumber = playersManager.nextPlayer(smallBlindPlayerNumber);
        makeStartBet(bigBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    private void makeStartBet(int playerNumber, int bet) {
        final int playerAmount = bank.getPlayerBalance(playerNumber);
        if (playerAmount > bet) {
            moveManager.makeInitialBet(playerNumber, bet);
        } else {
            moveManager.makeInitialBet(playerNumber, playerAmount);
        }
    }
}
