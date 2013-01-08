package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 25.12.12
 * Time: 0:08
 */
public class GameCycleManagerTest {

    private Dealer dealerMock;
    private PlayersList playersListMock;
    GameCycleManager gameCycleManager;

    private Player firstPlayerMock;
    private Player secondPlayerMock;

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        resetPlayersManager();

        gameCycleManager = new GameCycleManager(dealerMock, playersListMock);
    }

    private void resetPlayersManager() {
        playersListMock = mock(PlayersList.class);

        resetPlayers();
    }

    private void resetPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);

        List<Player> players = new ArrayList<Player>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);

        when(playersListMock.iterator()).thenReturn(players.iterator());
    }

    @Test
    public void shouldSetGameStatusStartedWhenPrepareNewGameCycle() throws Exception {
        gameCycleManager.prepareNewGameCycle();

        verify(dealerMock).setGameStarted();
    }

    @Test
    public void shouldBothPlayersSetDefaultAmountWhenGameStarted() throws Exception {
        gameCycleManager.prepareNewGameCycle();

        verify(firstPlayerMock).setBalance(GameSettings.COINS_AT_START);
        verify(secondPlayerMock).setBalance(GameSettings.COINS_AT_START);
    }
}
