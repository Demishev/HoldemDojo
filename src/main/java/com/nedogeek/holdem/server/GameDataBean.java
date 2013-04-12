package com.nedogeek.holdem.server;

import com.nedogeek.holdem.GameStatus;

import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:47
 */
public class GameDataBean {
    private List<String> players;
    private List<String> botTypes;

    private long coinsAtStart;
    private long minimumBind;

    private long gameDelay;
    private long endGameDelay;

    private GameStatus gameStatus;

    public GameDataBean() {
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<String> getBotTypes() {
        return botTypes;
    }

    public void setBotTypes(List<String> botTypes) {
        this.botTypes = botTypes;
    }

    public long getCoinsAtStart() {
        return coinsAtStart;
    }

    public void setCoinsAtStart(int coinsAtStart) {
        this.coinsAtStart = coinsAtStart;
    }

    public long getMinimumBind() {
        return minimumBind;
    }

    public void setMinimumBind(int minimumBind) {
        this.minimumBind = minimumBind;
    }

    public long getGameDelay() {
        return gameDelay;
    }

    public void setGameDelay(long gameDelay) {
        this.gameDelay = gameDelay;
    }

    public long getEndGameDelay() {
        return endGameDelay;
    }

    public void setEndGameDelay(long endGameDelay) {
        this.endGameDelay = endGameDelay;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
