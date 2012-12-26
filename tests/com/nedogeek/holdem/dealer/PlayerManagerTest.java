package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:55
 */
public class PlayerManagerTest {
    final Player FIRST_PLAYER = new Player("First player");
    final Player SECOND_PLAYER = new Player("Second player");

    private PlayersManager playersManager;
    private Bank bank;

    @Before
    public void setUp() throws Exception {
        resetPlayers();
        bank = mock(Bank.class);

        clearPlayerManager();
        setDefaultTwoPlayersDesk();
    }

    private void resetPlayers() {
        FIRST_PLAYER.setStatus(PlayerStatus.NotMoved);
        SECOND_PLAYER.setStatus(PlayerStatus.NotMoved);
    }

    private void setDefaultTwoPlayersDesk() {
        playersManager.addPlayer(FIRST_PLAYER);
        playersManager.addPlayer(SECOND_PLAYER);
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerFold() throws Exception {
        playersManager.setDealerNumber(1);
        SECOND_PLAYER.setStatus(PlayerStatus.Fold);

        assertFalse(playersManager.hasAvailableMovers());
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerLost() throws Exception {
        playersManager.setLastMovedPlayer(1);
        SECOND_PLAYER.setStatus(PlayerStatus.Fold);

        assertFalse(playersManager.hasAvailableMovers());
    }

    @Test
    public void shouldMoveWhen() throws Exception {
        FIRST_PLAYER.setStatus(PlayerStatus.Rise);
        SECOND_PLAYER.setStatus(PlayerStatus.Rise);
        playersManager.setLastMovedPlayer(0);

        when(bank.riseNeeded(SECOND_PLAYER)).thenReturn(true);

        assertEquals(1, playersManager.getMoverNumber());
    }

    @Test
    public void should0PlayersWhenNewPlayersManager() throws Exception {
        assertEquals(0, new PlayersManager(bank).getPlayersQuantity());
    }

    @Test
    public void should1PlayerWhenNewPlayerAddedToNewPlayersManager() throws Exception {
        clearPlayerManager();
        playersManager.addPlayer(FIRST_PLAYER);

        assertEquals(1, playersManager.getPlayersQuantity());
    }

    @Test
    public void should2PlayersWhen2NewPlayerAddedToNewPlayersManager() throws Exception {
        clearPlayerManager();
        playersManager.addPlayer(FIRST_PLAYER);
        playersManager.addPlayer(SECOND_PLAYER);

        assertEquals(2, playersManager.getPlayersQuantity());
    }

    private void clearPlayerManager() {
        playersManager = new PlayersManager(bank);

        playersManager.setDealerNumber(0);
        playersManager.setLastMovedPlayer(-1);
    }

    @Test
    public void should1PlayerWhenSameNewPlayerAddedToNewPlayersManagerTwice() throws Exception {
        clearPlayerManager();
        playersManager.addPlayer(FIRST_PLAYER);
        playersManager.addPlayer(FIRST_PLAYER);

        assertEquals(1, playersManager.getPlayersQuantity());
    }

    @Test
    public void should0PlayersWhenAddFirstPlayerAndRemoveItFromPlayersManager() throws Exception {
        clearPlayerManager();
        playersManager.addPlayer(FIRST_PLAYER);
        playersManager.removePlayer(FIRST_PLAYER);

        assertEquals(0, playersManager.getPlayersQuantity());
    }

    @Test
    public void should1PlayerWhenAddFirstPlayerAndRemoveSecondPlayerFromPlayersManager() throws Exception {
        clearPlayerManager();
        playersManager.addPlayer(FIRST_PLAYER);
        playersManager.removePlayer(SECOND_PLAYER);

        assertEquals(1, playersManager.getPlayersQuantity());
    }
}
