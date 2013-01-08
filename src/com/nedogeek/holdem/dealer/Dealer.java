package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Player;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class Dealer implements Runnable {
    private final MoveManager moveManager;
    private final NewGameSetter newGameSetter;
    private final PlayersList playersManager;
    private final GameCycleManager gameCycleManager;
    private final EndGameManager endGameManager;

    private GameStatus gameStatus;
    private GameRound gameRound;


    Dealer(MoveManager moveManagerMock, NewGameSetter newGameSetterMock,
           PlayersList playersManagerMock, GameCycleManager gameCycleManagerMock,
           EndGameManager endGameManagerMock, GameStatus gameStatus, GameRound gameRound) {
        moveManager = moveManagerMock;
        newGameSetter = newGameSetterMock;
        playersManager = playersManagerMock;
        gameCycleManager = gameCycleManagerMock;
        endGameManager = endGameManagerMock;

        this.gameStatus = gameStatus;
        this.gameRound = gameRound;
    }

    public void run() {
        //TODO not codded yet
    }

    void tick() {
        switch (gameStatus) {
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
        switch (gameRound) {
            case INITIAL:
                newGameSetter.setNewGame();
                break;
            case FINAL:
                endGameManager.endGame();
                break;
            default:
                if (playersManager.hasAvailableMovers()) {
                    moveManager.makeMove(playersManager.getMover());
                } else {
                    setNextGameRound();
                }
                break;
        }
    }

    public void setGameStarted() {

    }

    public boolean riseNeeded(Player player) {
        return false;
    }

    public void addToPot(int betValue) {

    }

    public int getCallValue() {
        return 0;
    }

    public void setCallValue(int playerBet) {

    }

    public void resetCards() {

    }

    public void setNextGameRound() {

    }

    public void giveCardsToPlayer(Player player) {

    }

    public void setPlayerWin(Player winner) {

    }

    public void setGameEnded() {

    }

    public void sendToPot(int i) {

    }
}
