package com.nedogeek.holdem;

import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.server.GameDataBean;
import org.eclipse.jetty.websocket.WebSocket;

import java.util.List;
import java.util.Set;

/**
 * User: Konstantin Demishev
 * Date: 19.05.13
 * Time: 20:02
 */
public interface GameCenter {
    List<String> getLobbyPlayers();

    Set<String> getGames();

    GameDataBean getGameData(String gameID);

    void performAdminCommand(String commandName, String[] params);

    void performPlayerCommand(PlayerCommand command);

    void notifyViewers(String gameID, String message);

    void notifyPlayer(String playerName, String message);

    void connectPlayer(String login, WebSocket.Connection connection);

    void setPlayerMove(String login, PlayerAction move);
}
