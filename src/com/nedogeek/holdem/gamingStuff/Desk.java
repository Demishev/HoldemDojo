package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.connections.Player;
import com.nedogeek.holdem.connections.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:21
 */
public class Desk {
    private int dealerPlayerNumber;
    private GameRound gameRound;
	private int playerQuantity;
	private GameStatus  gameStatus = GameStatus.NOT_READY;
	
	
    public GameStatus getGameStatus() {
       
		return gameStatus;
    }

    //TODO Reduce visibility.
    public void setGameStatus(GameStatus started) {
		
    }

    public int getPlayersQuantity() {
        return playerQuantity;
    }

    public void setPlayerAmount(int playerNumber, int amount) {
    }

    public void setDealerPlayer(int playerNumber) {

    }

    public void shuffleCards() {
    }

    public void setPlayerBet(int playerNumber, int bet) {
    }

    public int getDealerPlayerNumber() {
        return dealerPlayerNumber;
    }

    public void addToPot(int bet) {
    }

    public int getPlayerAmount(int playerNumber) {
        return 0;
    }

    public PlayerAction getPlayersMove(int playerNumber) {
        return null;
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setNextGameRound() {

    }

    public int getLastMovedPlayer() {
        return 0;
    }

    public int getPlayerBet(int playerNumber) {
        return 0;
    }

    public void setLastMovedPlayer(int playerNumber) {
    }

    public int getCallValue() {
        return 0;
    }

    public void setCallValue(int callValue) {
    }

    public PlayerStatus getPlayerStatus(int playerNumber) {
        return null;
    }

    public void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
    }

    public void setGameEnded() {
    }

    public void setPlayerWin(int playerNumber) {
    }

    public PlayerCardCombination getPlayerCardCombination(int playerNumber) {
        return null;
    }

    public Card[] getDeskCards() {
        return new Card[0];
    }

    public boolean isFirstCombinationBiggerThanSecond(int firstCombination, int secondCombination) {
        return false;
    }

	public void addPlayer(Player player) {
		playerQuantity++;		
	}

	public void setReady() {
		if(playerQuantity == 2)
			gameStatus = GameStatus.READY;
	}
}
