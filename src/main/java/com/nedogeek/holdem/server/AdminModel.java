package com.nedogeek.holdem.server;

import com.nedogeek.holdem.bot.Bots;

/**
 * User: Konstantin Demishev
 * Date: 12.04.13
 * Time: 2:34
 */
public interface AdminModel {
    boolean login(String password);

    void addBot(Bots botType, String botName, String password);

    boolean kick(String playerName, String password);

    boolean setInitialCoins(int coinsCount, String password);

    boolean setMinimumBlind(int minimumBlind, String password);

    boolean setGameDelay(int gameDelay, String password);

    boolean setEndGameDelay(int endGameDelay, String password);

    boolean start(String password);

    boolean stop(String password);

    boolean pause(String password);

    boolean changePassword(String oldPassword, String newPassword);

    GameDataBean getGameData(String password);
}
