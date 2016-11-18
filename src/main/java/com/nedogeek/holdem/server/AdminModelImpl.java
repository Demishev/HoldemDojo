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


import com.nedogeek.holdem.Game;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.bot.Bots;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:52
 */
public class AdminModelImpl implements AdminModel {
    static final String DEFAULT_PASSWORD = "1234";

    private String adminPassword = DEFAULT_PASSWORD;

    private Game game;

    public AdminModelImpl() {
//        game = GameImpl.getInstance(); TODO fix admin model
    }

    AdminModelImpl(Game game) {
        this.game = game;
    }

    @Override
    public void addBot(Bots botType, String botName) {
        game.addBot(botType, botName);
    }

    @Override
    public void kick(String playerName) {
        game.removePlayer(playerName);
    }

    @Override
    public void setInitialCoins(int coinsCount) {
        GameSettings.setCoinsAtStart(coinsCount);
    }

    @Override
    public void setMinimumBlind(int minimumBlind) {
        GameSettings.setSmallBlind(minimumBlind);
    }

    @Override
    public void setGameDelay(int gameDelay) {
        GameSettings.setGameDelayValue(gameDelay);
    }

    @Override
    public void setEndGameDelay(int endGameDelay) {
        GameSettings.setEndGameDelayValue(endGameDelay);
    }

    @Override
    public void start() {
        game.start();
    }

    @Override
    public void stop() {
        game.stop();
    }

    @Override
    public void pause() {
        game.pause();
    }

    @Override
    public void changePassword(String newPassword) {
        adminPassword = newPassword;
    }

    @Override
    public GameDataBean getGameData() {
        return game.getGameData();
    }

    @Override
    public boolean passwordCorrect(String password) {
        return adminPassword.equals(password);
    }
}
