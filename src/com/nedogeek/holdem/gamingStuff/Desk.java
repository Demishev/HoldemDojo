package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.connections.PlayersAction;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:21
 */
public class Desk {
    private int dealerPlayerNumber;
    private int gameRound;

    public GameStatus getGameStatus() {
        return null;
    }

    public void setGameStatus(GameStatus started) {
    }

    public int getPlayersQuantity() {
        return 0;
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

    public PlayersAction getPlayersMove(int playerNumber) {
        return null;
    }

    public int getGameRound() {
        return gameRound;
    }

    @Deprecated
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

    public void setPlayerFold(int playerNumber) {
    }

    public int getCallValue() {
        return 0;
    }

    public void setCallValue(int callValue) {
    }

    public PlayerStatus getPlayerStatus(int i) {
        return null;
    }

    public void setGameRound(int roundNumber) {
    }
}
