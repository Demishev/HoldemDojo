package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
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

    public NewGameSetter(Desk desk, PlayersManager playersManager, MoveManager moveManager) {
        this.desk = desk;
        this.playersManager = playersManager;
        this.moveManager = moveManager;
    }

    void setNewGame() {
        desk.resetCards();
        playersManager.changeDealer();
        makeInitialBets();

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

    private void makeInitialBets() {
        makeStartBet(playersManager.smallBlindPlayerNumber(), GameSettings.SMALL_BLIND_AT_START);

        makeStartBet(playersManager.bigBlindPlayerNumber(), GameSettings.SMALL_BLIND_AT_START * 2);
    }

    private void makeStartBet(int playerNumber, int bet) {
        moveManager.makeInitialBet(playerNumber, bet);
    }
}
