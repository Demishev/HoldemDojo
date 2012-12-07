package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:55
 */
public class PlayerManagerTest {
    private PlayersManager playersManager;

    private Desk deskMock;

    @Before
    public void setUp() throws Exception {
        setDefaultTwoPlayersDesk();

        playersManager = new PlayersManager(deskMock);
    }

    private void setDefaultTwoPlayersDesk() {
        deskMock = mock(Desk.class);

        when(deskMock.getPlayersQuantity()).thenReturn(2);
        when(deskMock.getDealerPlayerNumber()).thenReturn(0);
        when(deskMock.getPlayerStatus(0)).thenReturn(PlayerStatus.NotMoved);
        when(deskMock.getPlayerStatus(1)).thenReturn(PlayerStatus.NotMoved);
        when(deskMock.getLastMovedPlayer()).thenReturn(-1);
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerFold() throws Exception {
        when(deskMock.getLastMovedPlayer()).thenReturn(1);
        when(deskMock.getPlayerStatus(1)).thenReturn(PlayerStatus.Fold);

        assertFalse(playersManager.hasAvailableMovers());
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerLost() throws Exception {
        when(deskMock.getLastMovedPlayer()).thenReturn(1);
        when(deskMock.getPlayerStatus(1)).thenReturn(PlayerStatus.Lost);

        assertFalse(playersManager.hasAvailableMovers());
    }
}
