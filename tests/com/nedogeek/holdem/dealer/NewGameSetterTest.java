package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 07.12.12
 * Time: 7:24
 */
public class NewGameSetterTest {
    private NewGameSetter newGameSetter;

    private Desk deskMock;

    @Before
    public void setUp() throws Exception {
        resetDeskMock();

        newGameSetter = new NewGameSetter(deskMock);

    }

    private void resetDeskMock() {
        deskMock = mock(Desk.class);

        setPlayersQuantity(2);
        setPlayerStatus(anyInt(), PlayerStatus.NotMoved);
    }

    private void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
        when(deskMock.getPlayerStatus(playerNumber)).thenReturn(playerStatus);
    }

    private void setPlayersQuantity(int playersQuantity) {
        when(deskMock.getPlayersQuantity()).thenReturn(playersQuantity);
    }

    @Test
    public void shouldSetFirstPlayerStatusNotMovedWhenDefaultNewGameSet() throws Exception {
        newGameSetter.setNewGame();

        verify(deskMock).setPlayerStatus(0, PlayerStatus.NotMoved);
    }

    @Test
    public void shouldSetSecondPlayerStatusNotMovedWhenDefaultNewGameSet() throws Exception {
        newGameSetter.setNewGame();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.NotMoved);
    }

    @Test
    public void shouldNameWhen() throws Exception {
        setPlayersQuantity(3);
        setPlayerStatus(0, PlayerStatus.Lost);
        setPlayerStatus(2, PlayerStatus.NotMoved);

        newGameSetter.setNewGame();

        verify(deskMock, never()).setPlayerStatus(0, PlayerStatus.NotMoved);
    }
}
