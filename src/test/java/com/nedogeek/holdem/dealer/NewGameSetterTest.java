package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 07.12.12
 * Time: 7:24
 */
public class NewGameSetterTest {
    private NewGameSetter newGameSetter;

    private Dealer dealerMock;
    private PlayersList playersManagerMock;
    private MoveManager moveManagerMock;
    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        resetPlayerManagerMock();
        resetMoveManagerMock();

        newGameSetter = new NewGameSetter(dealerMock, playersManagerMock, moveManagerMock, new EventManager());

    }

    private void resetMoveManagerMock() {
        moveManagerMock = mock(MoveManager.class);

    }

    private void resetPlayerManagerMock() {
        playersManagerMock = mock(PlayersList.class);

        resetPlayers();

        resetIterator();
        setDealerIs(0);
    }

    private void resetIterator() {
        when(playersManagerMock.iterator()).thenReturn(players.iterator());
    }

    private void resetPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        thirdPlayerMock = mock(Player.class);

        players = new ArrayList<>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);
        players.add(thirdPlayerMock);

        for (Player player : players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
        }

        players.remove(thirdPlayerMock);

        when(playersManagerMock.size()).thenReturn(2);
    }

    private void setDealerIs(int dealerNumber) {
        if (dealerNumber == 0) {
            when(playersManagerMock.smallBlindPlayer()).thenReturn(secondPlayerMock);
            when(playersManagerMock.bigBlindPlayer()).thenReturn(firstPlayerMock);
        }

        if (dealerNumber == 1) {
            when(playersManagerMock.smallBlindPlayer()).thenReturn(firstPlayerMock);
            when(playersManagerMock.bigBlindPlayer()).thenReturn(secondPlayerMock);
        }
    }

    private void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        when(player.getStatus()).thenReturn(playerStatus);
    }

    @Test
    public void shouldBeGivenCardsToFirstPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(firstPlayerMock);
    }

    @Test
    public void shouldBeGivenCardsToSecondPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(secondPlayerMock);
    }

    @Test
    public void shouldBeGivenCardsToThirdPlayerWhen3PlayersNewGame() throws Exception {
        players.add(thirdPlayerMock);
        resetIterator();

        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(thirdPlayerMock);
    }

    @Test
    public void shouldNotBeGivenCardsToThirdPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock, never()).giveCardsToPlayer(thirdPlayerMock);
    }

    @Test
    public void shouldSmallBlindAddedToPotWhenGameStarted() throws Exception {
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeSmallBlind(secondPlayerMock);
    }

    @Test
    public void shouldBigBlindAddedToPotWhenGameStarted() throws Exception {
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeBigBlind(firstPlayerMock);
    }

    @Test
    public void shouldFirstPlayerSmallBlindAddedToPotWhenGameStartedAndDealerIsSecondPlayer() throws Exception {
        setDealerIs(1);
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeSmallBlind(firstPlayerMock);
    }

    @Test
    public void shouldSecondPlayerBigBlindAddedToPotWhenGameStartedAndDealerIsSecondPlayer() throws Exception {
        setDealerIs(1);
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeBigBlind(secondPlayerMock);
    }

    @Test
    public void shouldNotSetGameRound1WhenTickGameRoundIs1() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).setNextGameRound();
    }

    @Test
    public void shouldShuffleCardsWhenStartGaming() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).resetCards();
    }

    @Test
    public void shouldPlayersListSetNewGameWhenNewGameSetterSetNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(playersManagerMock).setNewGame();
    }

    @Test
    public void shouldNotSetNewGameWhenPlayersListSizeIs0() throws Exception {
        when(playersManagerMock.size()).thenReturn(0);

        newGameSetter.setNewGame();

        verify(playersManagerMock, never()).setNewGame();
    }

    @Test
    public void shouldNotSetNewGameWhenPlayersListSizeIs1() throws Exception {
        when(playersManagerMock.size()).thenReturn(1);

        newGameSetter.setNewGame();

        verify(playersManagerMock, never()).setNewGame();
    }

    @Test
    public void shouldDealerSetCallValue0WhenSetNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).setCallValue(0);
    }
}
