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
        moveManager.makeInitialBet(playersManager.smallBlindPlayer(), GameSettings.SMALL_BLIND_AT_START);

        moveManager.makeInitialBet(playersManager.bigBlindPlayer(), 2 * GameSettings.SMALL_BLIND_AT_START);
    }
}
