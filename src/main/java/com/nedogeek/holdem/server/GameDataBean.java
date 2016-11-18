package com.nedogeek.holdem.server;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.nedogeek.holdem.GameStatus;

import java.io.Serializable;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:47
 */
public class GameDataBean implements Serializable {
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
