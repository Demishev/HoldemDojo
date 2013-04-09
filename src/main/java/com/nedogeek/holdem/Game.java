package com.nedogeek.holdem;

import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import org.eclipse.jetty.websocket.WebSocket;
import com.nedogeek.holdem.gamingStuff.Player;

import java.lang.String;

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

    void removeConnection(WebSocket.Connection connection);

    GameStatus getGameStatus();
}
