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



import com.nedogeek.holdem.GameCenter;
import com.nedogeek.holdem.PlayerCommand;
import com.nedogeek.holdem.PlayerCommandsParser;
import org.eclipse.jetty.websocket.WebSocket;


/**
 * User: Konstantin Demishev
 * Date: 06.08.12
 * Time: 16:41
 */
class PlayerWebSocket implements WebSocket.OnTextMessage {
    private final PlayerCommandsParser playerCommandParser;
    private final GameCenter gameCenter;

    private String login;

    PlayerWebSocket(PlayerCommandsParser playerCommandParser, GameCenter gameCenter, String login) {
        this.playerCommandParser = playerCommandParser;
        this.gameCenter = gameCenter;
        this.login = login;
    }

    @Override
    public void onMessage(String message) {
        PlayerCommand playerCommand = playerCommandParser.parseCommand("login", message); //TODO stub!!!!
        gameCenter.performPlayerCommand(playerCommand);
    }


    @Override
    public void onOpen(Connection connection) {
        gameCenter.connectPlayer(login, connection);
    }

    @Override
    public void onClose(int i, String s) {
    }
}