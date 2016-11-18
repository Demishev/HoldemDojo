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
    public void shouldOneGameWhenNewGameCenter() throws Exception {
        assertEquals(1, gameCenterImpl.getGames().size());
    }

    @Test
    public void shouldNewGameGamesWhenNewGameCenterCreateNewGame() throws Exception {
        gameCenterImpl.createGame(FIRST_GAME_ID);

        assertEquals(FIRST_GAME_ID, gameCenterImpl.getGames().toArray()[0]);
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenAddPlayer() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_PLAYER_NAME, null);

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldNoLobbyPlayersWhenFirstPlayerAddedToFirstGame() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_PLAYER_NAME, null);
        gameCenterImpl.createGame(FIRST_GAME_ID);

        gameCenterImpl.joinGame(FIRST_PLAYER_NAME, FIRST_GAME_ID);

        assertEquals(0, gameCenterImpl.getLobbyPlayers().size());
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenFirstPlayerAddedToInvalidGame() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_PLAYER_NAME, null);
        gameCenterImpl.createGame(FIRST_GAME_ID);

        gameCenterImpl.joinGame(FIRST_PLAYER_NAME, "Invalid game id");

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldFirstPlayerInLobbyWhenFirstPlayerAddedToUncreatedGame() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_PLAYER_NAME, null);

        gameCenterImpl.joinGame(FIRST_PLAYER_NAME, "Not created game");

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

    @Test
    public void shouldFirstPlayerInLobbyWhenCreateFirstGameConnectFirstPlayerJoinFirstGameAdnRemoveFirstGame() throws Exception {
        gameCenterImpl.connectPlayer(FIRST_LOGIN, connectionMock);
        gameCenterImpl.createGame(FIRST_GAME_ID);
        gameCenterImpl.joinGame(FIRST_PLAYER_NAME, FIRST_GAME_ID);

        gameCenterImpl.removeGame(FIRST_GAME_ID);

        assertEquals(FIRST_PLAYER_NAME, gameCenterImpl.getLobbyPlayers().get(0));
    }

    @Test
    public void shouldAddPublicConnectionToConnectionManagerWhenConnectViewerToFirstGame() throws Exception {
        gameCenterImpl.connectViewer(FIRST_GAME_ID, connectionMock);

        verify(connectionsManagerMock).addViewer(FIRST_GAME_ID, connectionMock);
    }

    @Test
    public void shouldAddPublicConnectionToConnectionManagerWhenConnectViewerToSecondGame() throws Exception {
        final String SECOND_GAME_ID = "Second game";
        gameCenterImpl.connectViewer(SECOND_GAME_ID, connectionMock);

        verify(connectionsManagerMock).addViewer(SECOND_GAME_ID, connectionMock);
    }
}
