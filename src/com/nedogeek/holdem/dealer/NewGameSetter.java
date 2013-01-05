package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import com.nedogeek.holdem.gamingStuff.Player;

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

        resetPlayers();
        makeInitialBets();

        desk.setNextGameRound();
    }

    private void resetPlayers() {
        for (Player player: playersManager.getPlayers()) {
            if (player.getStatus() != PlayerStatus.Lost) {
                desk.giveCardsToPlayer(player);
                player.setStatus(PlayerStatus.NotMoved);
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
