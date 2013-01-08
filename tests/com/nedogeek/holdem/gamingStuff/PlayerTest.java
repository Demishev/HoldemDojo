package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.PlayersList;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Konstantin Demishev
 * Date: 08.01.13
 * Time: 3:23
 */
public class PlayerTest {
    private static final String NAME = "Player name";

    private PlayersList playersListMock;

    private Player player;

    @Before
    public void setUp() throws Exception {
        playersListMock = mock(PlayersList.class);

        player = new Player(NAME);
    }

    @Test
    public void shouldNameSavedWhenPlayerCreates() throws Exception {
        assertEquals(NAME, player.getName());
    }

    @Test
    public void shouldMakeMoveToPlayersListWhenPlayerMakesBet() throws Exception {
        player.registerList(playersListMock);

        player.makeBet(500);

        verify(playersListMock).playerMoved(player);
    }

    @Test
    public void shouldNotMovedWhenNewPlayerGetStatus() throws Exception {
        assertEquals(PlayerStatus.NotMoved, player.getStatus());
    }

    @Test
    public void shouldFoldWhenNewPlayerSetStatusFoldAndGetStatus() throws Exception {
        player.setStatus(PlayerStatus.Fold);

        assertEquals(PlayerStatus.Fold, player.getStatus());
    }
}
