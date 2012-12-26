package com.nedogeek.holdem.dealer;

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
}
