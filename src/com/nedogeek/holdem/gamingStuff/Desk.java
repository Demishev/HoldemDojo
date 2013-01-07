package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:21
 */
public class Desk {
    private GameRound gameRound;
    private GameStatus gameStatus;

    public Desk() {
        gameStatus = GameStatus.NOT_READY;
        gameRound = GameRound.INITIAL;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Deprecated
    public int getPlayersQuantity() {
        return 0;
    }

    public void resetCards() {
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setNextGameRound() {
        gameRound = GameRound.values()[gameRound.ordinal() + 1];
    }

    public void setGameEnded() {
    }

    public void setPlayerWin(Player player) {
    }

    public void addPlayer(Player player) {
    }

    public void setGameStarted() {
        gameStatus = GameStatus.STARTED;
    }

    public void setReady() {
    }

    public void giveCardsToPlayer(Player playerNumber) {

    }
}
