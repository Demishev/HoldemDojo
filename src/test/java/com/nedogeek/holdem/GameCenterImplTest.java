package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.server.AdminCommandsPerformer;
import com.nedogeek.holdem.server.GameDataBean;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 19.05.13
 * Time: 20:44
 */
public class GameCenterImplTest {
    private static final String FIRST_GAME_ID = "First game";
    private static final String FIRST_PLAYER_NAME = "First player";
    private static final String MESSAGE = "MESSAGE";

    private PlayerCommandPerformer playerCommandPerformerMock;
    private AdminCommandsPerformer adminCommandsPerformerMock;
    private ConnectionsManager connectionsManagerMock;
    private Map<String, Game> gamesMock;
    private GameDataBean gameDataBeanMock;

    private GameCenterImpl gameCenterImpl;
    private final String FIRST_LOGIN = "First player";
    private WebSocket.Connection connectionMock;
    private Game gameMock;


    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        resetMocks();

        gameCenterImpl = new GameCenterImpl(connectionsManagerMock, adminCommandsPerformerMock, playerCommandPerformerMock, gamesMock);
    }

    private void resetMocks() {
        adminCommandsPerformerMock = mock(AdminCommandsPerformer.class);
        playerCommandPerformerMock = mock(PlayerCommandPerformer.class);
        connectionsManagerMock = mock(ConnectionsManager.class);
        connectionMock = mock(WebSocket.Connection.class);

        gameMock = mock(Game.class);
        gameDataBeanMock = mock(GameDataBean.class);
        when(gameMock.getGameData()).thenReturn(gameDataBeanMock);

        gamesMock = new HashMap<>();
        gamesMock.put(FIRST_GAME_ID, gameMock);
    }

    @Test
    public void shouldNoGamesWhenNewGameCenter() throws Exception {
        assertEquals(0, gameCenterImpl.getGames().size());
    }

    @Test
    public void shouldNewGameGamesWhenNewGameCenterCreateNewGame() throws Exception {
        gameCenterImpl.createGame(FIRST_GAME_ID);

        assertEquals(FIRST_GAME_ID, gameCenterImpl.getGames().toArray()[0]);
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenAddPlayer() throws Exception {
        gameCenterImpl.addPlayer(FIRST_PLAYER_NAME);

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldNoLobbyPlayersWhenFirstPlayerAddedToFirstGame() throws Exception {
        gameCenterImpl.addPlayer(FIRST_PLAYER_NAME);
        gameCenterImpl.createGame(FIRST_GAME_ID);

        gameCenterImpl.joinGame(FIRST_GAME_ID);

        assertEquals(0, gameCenterImpl.getLobbyPlayers().size());
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenFirstPlayerAddedToInvalidGame() throws Exception {
        gameCenterImpl.addPlayer(FIRST_PLAYER_NAME);
        gameCenterImpl.createGame(FIRST_GAME_ID);

        gameCenterImpl.joinGame("Invalid game id");

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenFirstPlayerAddedToUncreatedGame() throws Exception {
        gameCenterImpl.addPlayer(FIRST_PLAYER_NAME);

        gameCenterImpl.joinGame(FIRST_GAME_ID);

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldConnectionManagerSendMessageToPlayer() throws Exception {
        gameCenterImpl.notifyPlayer(FIRST_PLAYER_NAME, MESSAGE);

        verify(connectionsManagerMock).sendPersonalMessage(FIRST_PLAYER_NAME, MESSAGE);
    }

    @Test
    public void shouldConnectionManagerSendMessageToViewer() throws Exception {
        gameCenterImpl.notifyViewers(FIRST_GAME_ID, MESSAGE);

        verify(connectionsManagerMock).sendMessageToViewers(FIRST_GAME_ID, MESSAGE);
    }

    @Test
    public void shouldDoPlayerCommandWhenGameCenterPerformPlayerCommand() throws Exception {
        PlayerCommand playerCommandMock = mock(PlayerCommand.class);

        gameCenterImpl.performPlayerCommand(playerCommandMock);

        verify(playerCommandPerformerMock).doCommand(playerCommandMock);
    }


    @Test
    public void shouldDoAdminCommandWhenGameCenterPerformAdminCommand() throws Exception {
        String commandName = "Command name";
        String[] params = {};

        gameCenterImpl.performAdminCommand(commandName, params);

        verify(adminCommandsPerformerMock).performCommand(commandName, params);
    }

    @Test
    public void shouldNoNullPointerWhenGetGameDataBeanNotExistingGame() throws Exception {
        gameCenterImpl.getGameData(FIRST_GAME_ID);
    }

    @Test
    public void shouldGetGameBeanMockWhenGetFirstGameData() throws Exception {
        assertEquals(gameDataBeanMock, gameCenterImpl.getGameData(FIRST_GAME_ID));
    }

    @Test
    public void shouldPlayerAddedToLobbyWhenConnectPlayer() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_LOGIN, null);

        assertEquals(FIRST_LOGIN, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldPlayerConnectionRegisteredToConnectionManagerWhenConnectPlayer() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_LOGIN, connectionMock);

        verify(connectionsManagerMock).addPersonalConnection(FIRST_LOGIN, connectionMock);
    }

    @Test
    public void shouldFirstGameSetPlayerMoveWhenGameCenterSetPlayerMove() throws Exception {
        final PlayerAction playerAction = mock(PlayerAction.class);
        gameCenterImpl.setPlayerMove(FIRST_LOGIN, playerAction);

        verify(gameMock).setMove(FIRST_LOGIN, playerAction);
    }
}
