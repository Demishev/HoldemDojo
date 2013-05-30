package com.nedogeek.holdem;

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
