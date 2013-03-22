package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {
    private Dealer dealerMock;
    private PlayersList playersListMock;
    private EventManager eventManagerMock = mock(EventManager.class);

    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    private EndGameManager endGameManager;


    @Before
    public void setUp() throws Exception {
        resetDeskMock();
        resetPlayersMocks();
        resetPlayerListMock();

        GameSettings.END_GAME_DELAY_VALUE = 0;

        eventManagerMock = mock(EventManager.class);

        endGameManager = new EndGameManager(dealerMock, playersListMock, eventManagerMock);
    }

    private void assertWinnerIs(final Player player) {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                GameEndedEvent event = (GameEndedEvent) invocation.getArguments()[0];
                if (event.getWinners() == null) {
                    fail();
                }
                assertTrue(event.getWinners().contains(player));
                return null;
            }
        }).when(eventManagerMock).addEvent((Event) anyObject());
    }

    private void assertNotWinner(final Player player) {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                GameEndedEvent event = (GameEndedEvent) invocation.getArguments()[0];

                if (event.getWinners() == null) {
                    fail();
                }

                assertFalse(event.getWinners().contains(player));
                return null;
            }
        }).when(eventManagerMock).addEvent((Event) anyObject());
    }

    private void setPlayersBet(int bet) {
        setPlayerBet(firstPlayerMock, bet);
        setPlayerBet(secondPlayerMock, bet);
        setPlayerBet(thirdPlayerMock, bet);
    }

    private void resetPlayersMocks() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        thirdPlayerMock = mock(Player.class);

        players = new ArrayList<>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);
        players.add(thirdPlayerMock);

        setPlayersRelations();

        PlayerCardCombination playerCardCombinationMock = mock(PlayerCardCombination.class);
        when(playerCardCombinationMock.toString()).thenReturn("");

        for (Player player : players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
            setBetSetGetListener(player);
            when(player.getCardCombination()).thenReturn(playerCardCombinationMock);
        }

        when(firstPlayerMock.getName()).thenReturn("First player");
        when(secondPlayerMock.getName()).thenReturn("Second player");
        when(thirdPlayerMock.getName()).thenReturn("Third player");
    }

    private void setBetSetGetListener(final Player player) {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                when(player.getBet()).thenReturn((Integer) invocation.getArguments()[0]);

                return null;
            }
        }).when(player).setBet(anyInt());

    }

    private void setPlayersRelations() {
        when(firstPlayerMock.compareTo(secondPlayerMock)).thenReturn(1);
        when(secondPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(firstPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(secondPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(secondPlayerMock)).thenReturn(-1);
    }

    private void resetDeskMock() {
        dealerMock = mock(Dealer.class);
    }

    private void resetPlayerListMock() {
        playersListMock = mock(PlayersList.class);

        when(playersListMock.iterator()).thenReturn(players.iterator()).thenReturn(players.iterator());
        when(playersListMock.toArray()).thenReturn(players.toArray());
        when(playersListMock.get(0)).thenReturn(firstPlayerMock);
    }

    private void setSimilarCardCombinations() {
        when(firstPlayerMock.compareTo(secondPlayerMock)).thenReturn(0);
        when(secondPlayerMock.compareTo(firstPlayerMock)).thenReturn(0);
    }

    private void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        when(player.getStatus()).thenReturn(playerStatus);
    }

    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(dealerMock).setInitialGameRound();
    }

    @Test
    public void shouldFirstPlayerSetBalance500WhenHisBet250AndSecondBet500() throws Exception {
        setPlayerBet(firstPlayerMock, 250);
        setPlayerBet(secondPlayerMock, 500);

        endGameManager.endGame();

        verify(firstPlayerMock).setBalance(500);
    }

    @Test
    public void shouldSecondPlayerSetBalance250WhenHisBet250AndSecondBet750() throws Exception {
        setPlayerBet(firstPlayerMock, 250);
        setPlayerBet(secondPlayerMock, 500);

        endGameManager.endGame();

        verify(secondPlayerMock).setBalance(250);
    }

    private void setPlayerBet(Player player, int bet) {
        player.setBet(bet);
    }

    @Test
    public void shouldFirstPlayerIsWinnerWhenDefaultGame() throws Exception {
        setPlayersBet(1000);

        assertWinnerIs(firstPlayerMock);

        endGameManager.endGame();
    }

    @Test
    public void shouldSecondPlayerIsNotWinnerWhenDefaultGame() throws Exception {
        setPlayersBet(1000);

        assertNotWinner(secondPlayerMock);

        endGameManager.endGame();
    }

    @Test
    public void shouldSecondPlayerIsWinnerWhenHisBet250AndSecondBet750() throws Exception {
        setPlayerBet(firstPlayerMock, 250);
        setPlayerBet(secondPlayerMock, 500);

        assertWinnerIs(secondPlayerMock);

        endGameManager.endGame();
    }

    @Test
    public void shouldBothPlayersWinnersWhenSimilarCardCombinations() throws Exception {
        setSimilarCardCombinations();
        setPlayersBet(1000);

        assertWinnerIs(firstPlayerMock);
        assertWinnerIs(secondPlayerMock);

        endGameManager.endGame();
    }

    @Test
    public void shouldSetGetBetWorksToPlayerMock() throws Exception {
        firstPlayerMock.setBet(1234);

        assertEquals(1234, firstPlayerMock.getBet());
    }

    //TODO Split winners when similar card combinations.
}
