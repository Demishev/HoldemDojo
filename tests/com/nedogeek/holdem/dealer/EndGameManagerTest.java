package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {
    private EndGameManager endGameManager;


    private Desk desk;

    @Before
    public void setUp() throws Exception {
        resetDeskMock();

        endGameManager = new EndGameManager(desk);
    }

    private void resetDeskMock() {
        desk = mock(Desk.class);
    }

    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(desk).setNewGameRound();
    }
}
