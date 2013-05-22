package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.server.AdminCommandsPerformer;
import com.nedogeek.holdem.server.GameDataBean;

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

    private static final GameCenterImpl instance = new GameCenterImpl();

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

    GameCenterImpl() {
        connectionsManager = new ConnectionsManager();
        adminCommandsPerformer = new AdminCommandsPerformer(null);
        playerCommandPerformer = new PlayerCommandPerformer();

        games = new HashMap<>();
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
        return null;//TODO stub
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
