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


import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.server.AdminCommandsPerformer;
import com.nedogeek.holdem.server.GameDataBean;
import org.eclipse.jetty.websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Konstantin Demishev
 * Date: 19.05.13
 * Time: 20:44
 */
public class GameCenterImpl implements GameCenter {
    private static final GameCenter instance = new GameCenterImpl();

    private List<String> lobbyPlayers = new ArrayList<>();

    private final ConnectionsManager connectionsManager;

    private final AdminCommandsPerformer adminCommandsPerformer;
    private final PlayerCommandPerformer playerCommandPerformer;

    private final Map<String, Game> games;

    public static GameCenter getInstance() {
        return instance;
    }

    /**
     * Test constructor.
     */
    GameCenterImpl(ConnectionsManager connectionsManager,
                   AdminCommandsPerformer adminCommandsPerformer,
                   PlayerCommandPerformer playerCommandPerformer,
                   Map<String, Game> games) {
        this.connectionsManager = connectionsManager;
        this.adminCommandsPerformer = adminCommandsPerformer;
        this.playerCommandPerformer = playerCommandPerformer;

        this.games = games;
    }

    private GameCenterImpl() {
        connectionsManager = null;     //TODO stub!
        adminCommandsPerformer = null;
        playerCommandPerformer = null;

        games = null;
    }

    @Override
    public List<String> getLobbyPlayers() {
        return lobbyPlayers;
    }

    @Override
    public Set<String> getGames() {
        return games.keySet();
    }

    @Override
    public GameDataBean getGameData(String gameID) {
        return games.get(gameID).getGameData();
    }

    @Override
    public void performAdminCommand(String commandName, String[] params) {
        adminCommandsPerformer.performCommand(commandName, params);
    }

    @Override
    public void performPlayerCommand(PlayerCommand command) {
        playerCommandPerformer.doCommand(command);
    }

    @Override
    public void notifyViewers(String gameID, String message) {
        connectionsManager.sendMessageToViewers(gameID, message);
    }

    @Override
    public void notifyPlayer(String playerName, String message) {
        connectionsManager.sendPersonalMessage(playerName, message);
    }

    @Override
    public void connectPlayer(String login, WebSocket.Connection connection) {
        lobbyPlayers.add(login);
        connectionsManager.addPersonalConnection(login, connection);
    }

    @Override
    public void setPlayerMove(String login, PlayerAction move) {
        for (Game game : games.values()) {
            game.setMove(login, move);
        }
    }

    @Override
    public void createGame(String gameId) {
        games.put(gameId, new GameImpl("1", this));
    }

    @Override
    public void removeGame(String gameID) {
        lobbyPlayers.add(games.get(gameID).getPlayerNames().get(0));
    }

    @Override
    public void connectViewer(String gameId, WebSocket.Connection connection) {
        connectionsManager.addViewer(gameId, connection);
    }

    public void joinGame(String login, String gameId) {
        if (games.containsKey(gameId)) {
            final Game game = games.get(gameId);
            game.addPlayer(login);
            lobbyPlayers = new ArrayList<>();
        }
    }
}
