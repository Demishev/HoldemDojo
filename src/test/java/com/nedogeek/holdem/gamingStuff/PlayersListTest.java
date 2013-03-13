package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.EventManager;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 1:55
 */
public class PlayersListTest {
    private PlayersList playersList;

    private Player firstPlayer = mock(Player.class);
    private Player secondPlayer = mock(Player.class);
    private EventManager eventManagerMock = mock(EventManager.class);

    @Before
    public void setUp() throws Exception {

        resetPlayers();

        resetPlayerList();
        setDefaultTwoPlayersGame();
    }

    private void resetPlayers() {
        firstPlayer = mock(Player.class);
        secondPlayer = mock(Player.class);
        
        when(firstPlayer.getName()).thenReturn("First player");
        when(secondPlayer.getName()).thenReturn("Second player");

        when(firstPlayer.getStatus()).thenReturn(PlayerStatus.NotMoved);
        when(secondPlayer.getStatus()).thenReturn(PlayerStatus.NotMoved);
    }

    private void setDefaultTwoPlayersGame() {
        playersList.add(firstPlayer);
        playersList.add(secondPlayer);
        playersList.setNewGame();
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerFold() throws Exception {
        when(secondPlayer.getStatus()).thenReturn(PlayerStatus.Fold);

        assertFalse(playersList.hasAvailableMovers());
    }

    @Test
    public void shouldFalseWhenDefaultDeskSecondPlayerLost() throws Exception {
        playersList.playerMoved(secondPlayer);
        when(secondPlayer.getStatus()).thenReturn(PlayerStatus.Fold);

        assertFalse(playersList.hasAvailableMovers());
    }

    @Test
    public void should0PlayersWhenNewPlayersManager() throws Exception {
        assertEquals(0, new PlayersList().size());
    }

    @Test
    public void should1PlayerWhenNewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerList();
        playersList.add(firstPlayer);
        playersList.setNewGame();

        assertEquals(1, playersList.size());
    }

    @Test
    public void should2PlayersWhen2NewPlayerAddedToNewPlayersManager() throws Exception {
        resetPlayerList();
        playersList.add(firstPlayer);
        playersList.add(secondPlayer);
        playersList.setNewGame();

        assertEquals(2, playersList.size());
    }

    private void resetPlayerList() {
        playersList = new PlayersList(eventManagerMock);
    }

    @Test
    public void should1PlayerWhenSameNewPlayerAddedToNewPlayersManagerTwice() throws Exception {
        resetPlayerList();
        playersList.add(firstPlayer);
        playersList.add(firstPlayer);
        playersList.setNewGame();

        assertEquals(1, playersList.size());
    }

    @Test
    public void should2PlayersListWhenPlayersManagerGetPlayers() throws Exception {
        assertEquals(2, playersList.size());
    }

    @Test
    public void shouldFirstPlayerWhenSmallBlindPlayer() throws Exception {
        assertEquals(firstPlayer, playersList.smallBlindPlayer());
    }

    @Test
    public void shouldSecondPlayerWhenBigBlindPlayer() throws Exception {
        assertEquals(secondPlayer, playersList.bigBlindPlayer());
    }

    @Test
    public void shouldThirdPlayerWhenAddThirdPlayerAndGetBigBlindPlayer() throws Exception {
        Player thirdPlayer = mock(Player.class);
        playersList.add(thirdPlayer);
        playersList.setNewGame();

        assertEquals(thirdPlayer, playersList.bigBlindPlayer());
    }

    @Test
    public void shouldNotHasAvailableMoversWhenBothPlayersAreNotActiveNotRisingPlayers() throws Exception {
        when(firstPlayer.isActiveNotRisePlayer()).thenReturn(false);
        when(secondPlayer.isActiveNotRisePlayer()).thenReturn(false);

        assertFalse(playersList.hasAvailableMovers());
    }

    @Test
    public void shouldChangeDealerWhenSetNewGame() throws Exception {
        playersList.setNewGame();

        assertEquals(0, playersList.getDealerNumber());
    }

    @Test
    public void shouldAllPlayersSetNotMovedStatusesWhenPlayersAdded() throws Exception {
        verify(firstPlayer).setStatus(PlayerStatus.NotMoved);
        verify(secondPlayer).setStatus(PlayerStatus.NotMoved);
    }

    @Test
    public void shouldHasAvailableMoversWhenFirstPlayerIsAllInAndSecondIsActive() throws Exception {
        when(firstPlayer.getStatus()).thenReturn(PlayerStatus.AllIn);

        when(secondPlayer.isActiveNotRisePlayer()).thenReturn(true);

        assertTrue(playersList.hasAvailableMovers());
    }

    @Test
    public void shouldNotHasAvailableMoversWhenFirstPlayerIsFoldAndSecondIsActive() throws Exception {
        when(firstPlayer.getStatus()).thenReturn(PlayerStatus.Fold);

        when(secondPlayer.isActiveNotRisePlayer()).thenReturn(true);

        assertFalse(playersList.hasAvailableMovers());
    }

    @Test
    public void should2PlayersWhenFirstAddedManagedAndAddedAgainSecondTime() throws Exception {
        resetPlayerList();

        playersList.add(firstPlayer);
        playersList.setNewGame();
        playersList.add(firstPlayer);
        playersList.setNewGame();

        assertEquals(1, playersList.size());
    }

    @Test
    public void shouldPlayerAddedWhenAddPlayerAndSetNewGame() throws Exception {
        resetPlayerList();

        playersList.add(firstPlayer);
        playersList.setNewGame();

        assertEquals(1, playersList.size());
    }

    @Test
    public void should2PlayersWhenAddThirdPlayerWithoutSetNewGame() throws Exception {
        playersList.add(mock(Player.class));

        assertEquals(2, playersList.size());
    }

    @Test
    public void shouldFirstPlayerAddedImmediatelyWhenPlayersListSize0() throws Exception {
        resetPlayerList();

        playersList.add(firstPlayer);

        assertEquals(1, playersList.size());
    }

    @Test
    public void shouldSecondPlayerAddedImmediatelyWhenPlayersListSize1() throws Exception {
        resetPlayerList();

        playersList.add(firstPlayer);
        playersList.add(secondPlayer);

        assertEquals(2, playersList.size());
    }
}
