package com.nedogeek.holdem;

import com.nedogeek.holdem.dealer.ConnectionsManager;
import com.nedogeek.holdem.server.AdminCommandsPerformer;
import com.nedogeek.holdem.server.GameDataBean;
import org.junit.Before;
import org.junit.Test;

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
    private Map gamesMock;
    private GameDataBean gameDataBeanMock;

    private GameCenterImpl gameCenterImpl;


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

        Game gameMock = mock(Game.class);
        gameDataBeanMock = mock(GameDataBean.class);
        when(gameMock.getGameData()).thenReturn(gameDataBeanMock);

        gamesMock = mock(Map.class);
        when(gamesMock.get(FIRST_GAME_ID)).thenReturn(gameMock);
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
    public void shouldGeWhen() throws Exception {
        assertEquals(gameDataBeanMock, gameCenterImpl.getGameData(FIRST_GAME_ID));
    }
}
