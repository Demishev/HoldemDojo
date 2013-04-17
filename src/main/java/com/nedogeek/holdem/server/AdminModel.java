package com.nedogeek.holdem.server;

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
