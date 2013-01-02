package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 13:26
 */
public class MoveManagerTest {
    private final int SMALL_BLIND = GameSettings.SMALL_BLIND_AT_START;

    private MoveManager moveManager;

    private PlayerAction playerActionMock;
    private Bank bankMock;
    private PlayersManager playersManagerMock;

    @Before
    public void setUp() throws Exception {
        playerActionMock = mock(PlayerAction.class);

        resetBank();
        resetPlayersManager();

        moveManager = new MoveManager(bankMock, playersManagerMock);
    }

    private void resetBank() {
        bankMock = mock(Bank.class);
    }

    private void resetPlayersManager() {
        playersManagerMock = mock(PlayersManager.class);
    }

    @Test
    public void shouldSetCallValue200WhenCallValue100AndMakeCallWhenPlayerBetIs100AndPlayerAmount1000() throws Exception {
        when(bankMock.getCallValue()).thenReturn(200);
        when(bankMock.getPlayerBalance(0)).thenReturn(1000);
        when(bankMock.getPlayerBet(0)).thenReturn(100);

        setActionType(PlayerAction.ActionType.Call);

        moveManager.makeMove(0,playerActionMock);


        verify(bankMock).setCallValue(200);
    }

    private OngoingStubbing<PlayerAction.ActionType> setActionType(PlayerAction.ActionType actionType) {
        return when(playerActionMock.getActionType()).thenReturn(actionType);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer1WhenMoveManagerMakeMove1PlayerFold() throws Exception {
        setActionType(PlayerAction.ActionType.Fold);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(1);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer1WhenMoveManagerMakeMove1PlayerCheck() throws Exception {
        setActionType(PlayerAction.ActionType.Check);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(1);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer0WhenMoveManagerMakeMove0PlayerCheck() throws Exception {
        setActionType(PlayerAction.ActionType.Check);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(0);
    }


    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet2SB() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet1SB() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }


    @Test
    public void shouldPlayerStatusRiseWhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.Rise);
    }

    @Test
    public void shouldSecondPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.Fold);
    }

    @Test
    public void shouldSecondPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerBalance(0, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.Fold);
    }




    private void setPlayerAction(PlayerAction.ActionType actionType, int actionValue) {
        when(playerActionMock.getRiseAmount()).thenReturn(actionValue);
        setPlayerAction(actionType);
    }

    private void setPlayerAction(PlayerAction.ActionType actionType) {
        when(playerActionMock.getActionType()).thenReturn(actionType);
    }

    private void setPlayerBalance(int playerNumber, int playerBalance) {
        when(bankMock.getPlayerBalance(playerNumber)).thenReturn(playerBalance);
    }

}
