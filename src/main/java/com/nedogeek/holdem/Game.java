package com.nedogeek.holdem;

import com.nedogeek.holdem.gamingStuff.Player;
import org.eclipse.jetty.websocket.WebSocket;

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

    Player addPlayer(String name, WebSocket.Connection connection);

    void addViewer(WebSocket.Connection connection);

    void addBot(Player bot);

    void removeConnection(WebSocket.Connection connection);

    void removePlayer(String playerName);

    GameStatus getGameStatus();

    List<String> getPlayers();
}
