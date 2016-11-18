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


import com.nedogeek.holdem.bot.Bots;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:34
 */
public interface AdminModel {
    void addBot(Bots botType, String botName);

    void kick(String playerName);

    void setInitialCoins(int coinsCount);

    void setMinimumBlind(int minimumBlind);

    void setGameDelay(int gameDelay);

    void setEndGameDelay(int endGameDelay);

    void start();

    void stop();

    void pause();

    void changePassword(String newPassword);

    GameDataBean getGameData();

    boolean passwordCorrect(String password);
}
