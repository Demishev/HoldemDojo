package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.gamingStuff.Bank;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        bankMock = mock(Bank.class);
        playersManagerMock = mock(PlayersManager.class);

        gameCycleManager = new GameCycleManager(dealerMock, playersManagerMock, bankMock);
    }

    @Test
    public void shouldSetGameStatusStartedWhenPrepareNewGameCycle() throws Exception {
        gameCycleManager.prepareNewGameCycle();

        verify(dealerMock).setGameStarted();
    }

    @Test
    public void shouldBothPlayersSetDefaultAmountWhenGameStarted() throws Exception {
        when(playersManagerMock.getPlayersQuantity()).thenReturn(2);
        gameCycleManager.prepareNewGameCycle();

        verify(bankMock).setPlayerAmount(0, GameSettings.COINS_AT_START);
        verify(bankMock).setPlayerAmount(1, GameSettings.COINS_AT_START);
    }
}
