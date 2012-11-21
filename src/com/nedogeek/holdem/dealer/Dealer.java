package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.connections.PlayersAction;
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
        tick();
    }

    void tick() {
        if (desk.getGameStatus().equals(GameStatus.Ready)) {
            prepareNewGameCycle();
        }
        if (desk.getGameStatus().equals(GameStatus.Started)) {
            if (playersManager.hasAvailableMovers()) {
                makeMove();
            } else {
                setNextGameRound();
            }
        }
    }

    private void setNextGameRound() {
        desk.setNextGameRound();
    }

    private void makeMove() {
        GameRound gameRound = desk.getGameRound();
        switch (gameRound) {
            case INITIAL:
                newGameSetter.setNewGame();
                break;
            case BLIND:
                int lastMovedPlayer = desk.getLastMovedPlayer();
                int moverNumber;
                if (lastMovedPlayer != -1) {
                    moverNumber = playersManager.nextPlayer(lastMovedPlayer);
                } else {
                    moverNumber = playersManager.nextPlayer(desk.getDealerPlayerNumber());
                }
                PlayersAction answer = desk.getPlayersMove(moverNumber);
                makeMove(moverNumber, answer);
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


    private void makeMove(int playerNumber, PlayersAction playerMove) {
        switch (playerMove.getActionType()) {
            case Fold:
                moveManager.makeFold(playerNumber);
                break;
            case Check:
                break;
            case Call:
                moveManager.makeCall(playerNumber);
                break;
            case Rise:
                moveManager.makeRise(playerNumber, playerMove.getBetQuantity());
                break;
            case AllIn:
                moveManager.makeAllIn(playerNumber);
                break;
        }
        desk.setLastMovedPlayer(playerNumber);
    }
}
