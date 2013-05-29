package com.nedogeek.holdem;

import com.nedogeek.holdem.bot.Bots;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.server.GameDataBean;

import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 09.04.13
 * Time: 23:08
 */
public interface Game {
    void start();

    void pause();

    void stop();

    Player addPlayer(String name);

    void removePlayer(String playerName);

    void addBot(Bots botType, String name);

    GameDataBean getGameData();

    void setMove(String login, PlayerAction move);

    List<String> getPlayerNames();
}
