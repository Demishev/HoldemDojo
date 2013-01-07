package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Bank;
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
    private PlayersManager playersManagerMock;
    private Bank bankMock;
    GameCycleManager gameCycleManager;

    private Player firstPlayerMock;
    private Player secondPlayerMock;

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        bankMock = mock(Bank.class);
        resetPlayersManager();

        gameCycleManager = new GameCycleManager(dealerMock, playersManagerMock, bankMock);
    }

    private void resetPlayersManager() {
        playersManagerMock = mock(PlayersManager.class);

        resetPlayers();
    }

    private void resetPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);

        List<Player> players = new ArrayList<Player>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);

        when(playersManagerMock.getPlayers()).thenReturn(players);
    }

    @Test
    public void shouldSetGameStatusStartedWhenPrepareNewGameCycle() throws Exception {
        gameCycleManager.prepareNewGameCycle();

        verify(dealerMock).setGameStarted();
    }

    @Test
    public void shouldBothPlayersSetDefaultAmountWhenGameStarted() throws Exception {
        gameCycleManager.prepareNewGameCycle();

        verify(bankMock).setPlayerAmount(0, GameSettings.COINS_AT_START);
        verify(bankMock).setPlayerAmount(1, GameSettings.COINS_AT_START);

        verify(bankMock, never()).setPlayerAmount(2, GameSettings.COINS_AT_START);
    }
}
