package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:21
 */
public class Desk {
    private GameRound gameRound;
    private GameStatus gameStatus;

    private List<Player> players = new ArrayList<Player>();
    private List<Player> waitingPlayers = new ArrayList<Player>();

    public Desk() {
        gameStatus = GameStatus.NOT_READY;
        gameRound = GameRound.INITIAL;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Deprecated
    public int getPlayersQuantity() {
        return players.size();
    }

    public void resetCards() {
    }

    public void setPlayerBet(int playerNumber, int bet) {
    }

    @Deprecated
    public int getDealerPlayerNumber() {
        return 0;
    }

    public int getPlayerBallance(int playerNumber) {
        return 0;
    }

    public PlayerAction getPlayersMove(int playerNumber) {
        return null;
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setNextGameRound() {
        gameRound = GameRound.values()[gameRound.ordinal() + 1];
    }

    public int getLastMovedPlayer() {
        return 0;
    }

    public int getPlayerBet(int playerNumber) {
        return 0;
    }

    public int getCallValue() {
        return 0;
    }

    public PlayerStatus getPlayerStatus(int playerNumber) {
        return null;
    }

    public void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
    }

    public void setGameEnded() {
    }

    public void setPlayerWin(Player player) {
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

    public void giveCardsToPlayer(Player playerNumber) {

    }
}
