package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.server.AdminCommandsPerformer;
import com.nedogeek.holdem.server.GameDataBean;
import org.eclipse.jetty.websocket.WebSocket;

import java.util.*;

/**
 * User: Konstantin Demishev
 * Date: 19.05.13
 * Time: 20:44
 */
class GameCenterImpl implements GameCenter {
    private Set<String> gameIDs = new HashSet<>();
    private List<String> lobbyPlayers = new ArrayList<>();

    private final ConnectionsManager connectionsManager;

    private final AdminCommandsPerformer adminCommandsPerformer;
    private final PlayerCommandPerformer playerCommandPerformer;

    private final Map<String, Game> games;

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

    @Override
    public List<String> getLobbyPlayers() {
        return lobbyPlayers;
    }

    @Override
    public Set<String> getGames() {
        return gameIDs;
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

    public void createGame(String gameId) {
        gameIDs.add(gameId);
    }

    public void addPlayer(String playerName) {
        lobbyPlayers.add(playerName);
    }

    public void joinGame(String gameId) {
        if (gameIDs.contains(gameId))
            lobbyPlayers = new ArrayList<>();
    }
}
