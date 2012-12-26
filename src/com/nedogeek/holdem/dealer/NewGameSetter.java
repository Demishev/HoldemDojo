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

    public NewGameSetter(Desk desk) {
        this.desk = desk;
        moveManager = new MoveManager(new Bank(), new PlayersManager(new Bank()));
        playersManager = new PlayersManager(new Bank());
    }

    void setNewGame() {
        desk.resetCards();
        int dealerPlayerNumber = defineDealer();
        makeInitialBets(dealerPlayerNumber);

        setInitialPlayerStatuses();
        giveCardsToPlayers();

        desk.setNextGameRound();
    }

    private void giveCardsToPlayers() {
        for (int i = 0; i < desk.getPlayersQuantity(); i++) {
            if (desk.getPlayerStatus(i) != PlayerStatus.Lost) {
                desk.giveCardsToPlayer(i);
            }
        }
    }

    private void setInitialPlayerStatuses() {
        for (int i = 0; i < desk.getPlayersQuantity(); i++) {
            if (desk.getPlayerStatus(i) != PlayerStatus.Lost) {
                desk.setPlayerStatus(i, PlayerStatus.NotMoved);
            }
        }
    }

    private int defineDealer() {
        int dealerPlayerNumber = playersManager.nextPlayer(desk.getDealerPlayerNumber());
        desk.setDealerPlayer(dealerPlayerNumber);
        return dealerPlayerNumber;
    }

    private void makeInitialBets(int dealerPlayerNumber) {
        int smallBlindPlayerNumber = playersManager.nextPlayer(dealerPlayerNumber);
        makeStartBet(smallBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START);

        int bigBlindPlayerNumber = playersManager.nextPlayer(smallBlindPlayerNumber);
        makeStartBet(bigBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    private void makeStartBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerBallance(playerNumber);
        if (playerAmount > bet) {
            moveManager.makeInitialBet(playerNumber, bet);
        } else {
            moveManager.makeInitialBet(playerNumber, playerAmount);
        }
    }
}
