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

    private GameStatus gameStatus = GameStatus.NOT_READY;
    private GameRound gameRound;
    private boolean isStopped;

    private int tickNumber = 0;
	private int callValue;

    public Dealer(PlayersList playersList) {
        this.playersList = playersList;
        EventManager.getInstance().collectPlayers(playersList);
        moveManager = new MoveManager(this, playersList);

        newGameSetter = new NewGameSetter(this, playersList, moveManager);
        endGameManager = new EndGameManager(this, playersList);
        gameCycleManager = new GameCycleManager(this, playersList);
        gameRound = GameRound.INITIAL;
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
        while (!isStopped) {
            tick();
        }
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
        tickNumber++;
        for(Player player: playersList){
        	System.out.println(tickNumber +":" +" Game status: " + gameStatus +" Game round: " + gameRound + " " + player);
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
                    if (playersList.moreThanOnePlayerDoNotFoldsOrLost())
                    	setNextGameRound();
                    else
                    	gameRound = GameRound.FINAL;
                }
                break;
        }
    }

    void setGameStarted() {
    	gameStatus= GameStatus.STARTED;
    }

    boolean riseNeeded(Player player) {
        return false;
    }

    void addToPot(int betValue) {

    }

    int getCallValue() {
        return callValue;
    }

    void setCallValue(int callValue) {
		this.callValue = callValue;

    }

    void resetCards() {

    }

    void setNextGameRound() {
    	
    	gameRound = GameRound.values()[gameRound.ordinal() + 1];
    }

    void giveCardsToPlayer(Player player) {

    }

    void setPlayerWin(Player winner) {
    	
    }

    void setGameEnded() {
    	gameRound = GameRound.INITIAL;
    }

    public void sendToPot(int i) {

    }

    public void stop() {
        isStopped = true;
    }

    @Deprecated
	public void setGameReady() {
		gameStatus = GameStatus.READY;		
	}

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public GameRound getGameRound() {
        return gameRound;
    }
}
