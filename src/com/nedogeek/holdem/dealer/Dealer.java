package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gamingStuff.Bank;
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
    private final GameCycleManager gameCycleManager;
    private final EndGameManager endGameManager;
    private final Bank bank;

    Dealer(Desk desk) {
        bank = new Bank();
        this.desk = desk;
        playersManager = new PlayersManager(bank);
        moveManager = new MoveManager(bank, playersManager);
        newGameSetter = new NewGameSetter(desk, playersManager, bank);
        gameCycleManager = new GameCycleManager(this, playersManager, bank);
        endGameManager = new EndGameManager(desk);
    }

    Dealer(Desk deskMock, MoveManager moveManagerMock, NewGameSetter newGameSetterMock,
           PlayersManager playersManagerMock, GameCycleManager gameCycleManagerMock,
           EndGameManager endGameManagerMock) {
        desk = deskMock;
        bank = new Bank();
        moveManager = moveManagerMock;
        newGameSetter = newGameSetterMock;
        playersManager = playersManagerMock;
        gameCycleManager = gameCycleManagerMock;
        endGameManager = endGameManagerMock;
    }

    public void run() {
        //TODO not codded yet
    }

    void tick() {
        switch (desk.getGameStatus()) {
            case READY:
                gameCycleManager.prepareNewGameCycle();
                break;
            case STARTED:
                makeGameAction();
                break;
            case CYCLE_ENDED:
                gameCycleManager.endGameCycle();
                break;
        }
    }

    private void makeGameAction() {
        switch (desk.getGameRound()) {
            case INITIAL:
                newGameSetter.setNewGame();
                break;
            case FINAL:
                endGameManager.endGame();
                break;
            default:
                if (playersManager.hasAvailableMovers()) {
                    moveManager.makeMove(playersManager.getMoverNumber(), playersManager.getPlayerMove());
                } else {
                    desk.setNextGameRound();
                }
                break;
        }
    }

    public void setGameStarted() {

    }
}
