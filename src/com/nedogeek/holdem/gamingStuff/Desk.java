package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:21
 */
public class Desk {
    private int dealerPlayerNumber;
    private GameRound gameRound;
    private GameStatus gameStatus;

    private List<Player> players = new ArrayList<Player>();
    private List<Player> waitingPlayers = new ArrayList<Player>();

    public Desk() {
        gameStatus = GameStatus.NOT_READY;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getPlayersQuantity() {
        return players.size();
    }

    public void setPlayerAmount(int playerNumber, int amount) {
    }

    public void setDealerPlayer(int playerNumber) {
        dealerPlayerNumber = playerNumber;
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
        if (gameStatus != GameStatus.STARTED) {
            players.add(player);
        } else {
            waitingPlayers.add(player);
        }
    }

    public void setGameStarted() {
        gameStatus = GameStatus.STARTED;
    }

    public void setReady() {
        if (players.size() > 1) {
            gameStatus = GameStatus.READY;
        }
    }

    public void setGameCycleEnded() {
        players.addAll(waitingPlayers);
        waitingPlayers.clear();

        gameStatus = GameStatus.CYCLE_ENDED;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        waitingPlayers.remove(player);

        if (players.size() < 2) {
            gameStatus = GameStatus.NOT_READY;
        }
    }

    public void giveCardsToPlayer(int playerNumber) {

    }
}
