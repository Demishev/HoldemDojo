package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.connections.PlayerAction;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class Dealer implements Runnable {
    private final Desk desk;

    private final MoveManager moveManager;
    private final NewGameSetter newGameSetter;
    private final PlayersManager playersManager;

    Dealer(Desk desk) {
        this.desk = desk;
        moveManager = new MoveManager(desk);
        newGameSetter = new NewGameSetter(desk);
        playersManager = new PlayersManager(desk);
    }

    public void run() {
        //TODO not codded yet
    }

    void tick() {
        if (desk.getGameStatus().equals(GameStatus.Ready)) {
            prepareNewGameCycle();
        }
        if (desk.getGameStatus().equals(GameStatus.Started)) {
            if (playersManager.hasAvailableMovers()) {
                makeMove();
            } else {
                desk.setNextGameRound();
            }
        }
    }

    private void makeMove() {
        GameRound gameRound = desk.getGameRound();
        switch (gameRound) {
            case INITIAL:
                newGameSetter.setNewGame();
                break;
            case BLIND:
                PlayerAction answer = desk.getPlayersMove(playersManager.getMoverNumber());
                moveManager.makeMove(playersManager.getMoverNumber(), answer);
                break;
        }
    }

    private void prepareNewGameCycle() {
        desk.setGameStatus(GameStatus.Started);
        int playersQuantity = desk.getPlayersQuantity();
        for (int i = 0; i < playersQuantity; i++) {
            desk.setPlayerAmount(i, GameSettings.COINS_AT_START);
        }
    }
}
