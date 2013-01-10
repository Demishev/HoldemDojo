package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class Dealer implements Runnable {
    private final MoveManager moveManager;
    private final NewGameSetter newGameSetter;
    private final PlayersList playersList;
    private final GameCycleManager gameCycleManager;
    private final EndGameManager endGameManager;

    private GameStatus gameStatus;
    private GameRound gameRound;


    public Dealer(PlayersList playersList) {
        this.playersList = playersList;

        moveManager = new MoveManager(this, playersList);

        newGameSetter = new NewGameSetter(this, playersList, moveManager);
        endGameManager = new EndGameManager(this, playersList);
        gameCycleManager = new GameCycleManager(this, playersList);
    }

    Dealer(MoveManager moveManagerMock, NewGameSetter newGameSetterMock,
           PlayersList playersManagerMock, GameCycleManager gameCycleManagerMock,
           EndGameManager endGameManagerMock, GameStatus gameStatus, GameRound gameRound) {
        moveManager = moveManagerMock;
        newGameSetter = newGameSetterMock;
        playersList = playersManagerMock;
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
                if (playersList.hasAvailableMovers()) {
                    moveManager.makeMove(playersList.getMover());
                } else {
                    setNextGameRound();
                }
                break;
        }
    }

    public void setGameStarted() {

    }

    boolean riseNeeded(Player player) {
        return false;
    }

    void addToPot(int betValue) {

    }

    int getCallValue() {
        return 0;
    }

    void setCallValue(int playerBet) {

    }

    void resetCards() {

    }

    void setNextGameRound() {

    }

    void giveCardsToPlayer(Player player) {

    }

    void setPlayerWin(Player winner) {

    }

    void setGameEnded() {

    }

    public void sendToPot(int i) {

    }
}
