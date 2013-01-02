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
    final Player firstPlayer = new Player("First player");
    final Player secondPlayer = new Player("Second player");

    private PlayersManager playersManager;
    private Bank bank;

    @Before
    public void setUp() throws Exception {
        resetPlayers();
        bank = mock(Bank.class);

        resetPlayerManager();
        setDefaultTwoPlayersGame();
    }

    private void resetPlayers() {
        firstPlayer.setStatus(PlayerStatus.NotMoved);
        secondPlayer.setStatus(PlayerStatus.NotMoved);
    }

    private void setDefaultTwoPlayersGame() {
        playersManager.addPlayer(firstPlayer);
        playersManager.addPlayer(secondPlayer);
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerFold() throws Exception {
        secondPlayer.setStatus(PlayerStatus.Fold);

        assertFalse(playersManager.hasAvailableMovers());
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerLost() throws Exception {
        playersManager.setLastMovedPlayer(1);
        secondPlayer.setStatus(PlayerStatus.Fold);

        assertFalse(playersManager.hasAvailableMovers());
    }

    @Test
    public void shouldMoveWhen() throws Exception {
        firstPlayer.setStatus(PlayerStatus.Rise);
        secondPlayer.setStatus(PlayerStatus.Rise);
        playersManager.setLastMovedPlayer(0);

        when(bank.riseNeeded(secondPlayer)).thenReturn(true);

        assertEquals(1, playersManager.getMoverNumber());
    }

    @Test
    public void should0PlayersWhenNewPlayersManager() throws Exception {
        assertEquals(0, new PlayersManager(bank).getPlayersQuantity());
    }

    @Test
    public void should1PlayerWhenNewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);

        assertEquals(1, playersManager.getPlayersQuantity());
    }

    @Test
    public void should2PlayersWhen2NewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.addPlayer(secondPlayer);

        assertEquals(2, playersManager.getPlayersQuantity());
    }

    private void resetPlayerManager() {
        playersManager = new PlayersManager(bank);

        playersManager.setLastMovedPlayer(-1);
    }

    @Test
    public void should1PlayerWhenSameNewPlayerAddedToNewPlayersManagerTwice() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.addPlayer(firstPlayer);

        assertEquals(1, playersManager.getPlayersQuantity());
    }

    @Test
    public void should0PlayersWhenAddFirstPlayerAndRemoveItFromPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.removePlayer(firstPlayer);

        assertEquals(0, playersManager.getPlayersQuantity());
    }

    @Test
    public void should1PlayerWhenAddFirstPlayerAndRemoveSecondPlayerFromPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.removePlayer(secondPlayer);

        assertEquals(1, playersManager.getPlayersQuantity());
    }

    @Test
    public void shouldMinus1WhenNewPlayerManagerGetDealerNumber() throws Exception {

        assertEquals(-1, playersManager.getDealerNumber());
    }

    @Test
    public void should0WhenNewPlayerManagerChangeDealerNumberAndGetDealerNumber() throws Exception {
        playersManager.changeDealer();

        assertEquals(0, playersManager.getDealerNumber());
    }

    @Test
    public void should1WhenNewPlayerManagerChangeDealerNumberTwiceAndGetDealerNumber() throws Exception {
        playersManager.changeDealer();
        playersManager.changeDealer();

        assertEquals(1, playersManager.getDealerNumber());
    }

}
