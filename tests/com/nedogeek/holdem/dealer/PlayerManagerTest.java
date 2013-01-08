package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
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
    private Dealer dealerMock;

    @Before
    public void setUp() throws Exception {
        resetPlayers();
        dealerMock = mock(Dealer.class);

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

        when(dealerMock.riseNeeded(secondPlayer)).thenReturn(true);      //TODO fixIt

        assertEquals(secondPlayer, playersManager.getMover());
    }

    @Test
    public void should0PlayersWhenNewPlayersManager() throws Exception {
        assertEquals(0, new PlayersManager(dealerMock).getPlayers().size());
    }

    @Test
    public void should1PlayerWhenNewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);

        assertEquals(1, playersManager.getPlayers().size());
    }

    @Test
    public void should2PlayersWhen2NewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.addPlayer(secondPlayer);

        assertEquals(2, playersManager.getPlayers().size());
    }

    private void resetPlayerManager() {
        playersManager = new PlayersManager(dealerMock);

        playersManager.setLastMovedPlayer(-1);
    }

    @Test
    public void should1PlayerWhenSameNewPlayerAddedToNewPlayersManagerTwice() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.addPlayer(firstPlayer);

        assertEquals(1, playersManager.getPlayers().size());
    }

    @Test
    public void should0PlayersWhenAddFirstPlayerAndRemoveItFromPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.removePlayer(firstPlayer);

        assertEquals(0, playersManager.getPlayers().size());
    }

    @Test
    public void should1PlayerWhenAddFirstPlayerAndRemoveSecondPlayerFromPlayersManager() throws Exception {
        resetPlayerManager();
        playersManager.addPlayer(firstPlayer);
        playersManager.removePlayer(secondPlayer);

        assertEquals(1, playersManager.getPlayers().size());
    }

    @Test
    public void shouldNullWhenNewPlayerManagerGetDealerNumber() throws Exception {

        assertEquals(null, playersManager.getDealer());
    }

    @Test
    public void shouldFirstPlayerWhenNewPlayerManagerChangeDealerNumberAndGetDealerNumber() throws Exception {
        playersManager.changeDealer();

        assertEquals(firstPlayer, playersManager.getDealer());
    }

    @Test
    public void shouldSecondPlayerWhenNewPlayerManagerChangeDealerNumberTwiceAndGetDealerNumber() throws Exception {
        playersManager.changeDealer();
        playersManager.changeDealer();

        assertEquals(secondPlayer, playersManager.getDealer());
    }

    @Test
    public void should2PlayersListWhenPlayersManagerGetPlayers() throws Exception {
        assertEquals(2, playersManager.getPlayers().size());

    }
}
