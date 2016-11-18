package com.nedogeek.holdem;

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

    void createGame(String gameID);

    void removeGame(String gameID);

    void connectViewer(String gameId, WebSocket.Connection connection);
}
