package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 13:26
 */
public class MoveManagerTest {
    private MoveManager moveManager;

    private Desk deskMock;
    private PlayerAction playerActionMock;

    @Before
    public void setUp() throws Exception {
        deskMock = mock(Desk.class);
        playerActionMock = mock(PlayerAction.class);

        moveManager = new MoveManager(deskMock);
    }

    @Test
    public void shouldSetCallValue200WhenCallValue100AndMakeCallWhenPlayerBetIs100AndPlayerAmount1000() throws Exception {
        when(deskMock.getCallValue()).thenReturn(200);
        when(deskMock.getPlayerAmount(0)).thenReturn(1000);
        when(deskMock.getPlayerBet(0)).thenReturn(100);

        when(playerActionMock.getActionType()).thenReturn(PlayerAction.ActionType.Call);

        moveManager.makeMove(0,playerActionMock);


        verify(deskMock).setCallValue(200);
    }
}
