package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 06.12.12
 * Time: 14:24
 */
public class EndGameManagerTest {
    private Dealer dealerMock;
    private PlayersList playersListMock;

    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    private PlayerCardCombination firstPlayerCardCombinationMock;
    private PlayerCardCombination secondPlayerCardCombinationMock;
    private PlayerCardCombination thirdPlayerCardCombinationMock;

    private EndGameManager endGameManager;


    @Before
    public void setUp() throws Exception {
        resetDeskMock();
        resetPlayersMocks();
        resetPlayerListMock();

        endGameManager = new EndGameManager(dealerMock, playersListMock);
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

        for (Player player : players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
        }

        setCardCombinationMocks();
    }

    private void setPlayersRelations() {
        when(firstPlayerMock.compareTo(secondPlayerMock)).thenReturn(1);
        when(secondPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(firstPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(firstPlayerMock)).thenReturn(-1);
        when(secondPlayerMock.compareTo(thirdPlayerMock)).thenReturn(1);
        when(thirdPlayerMock.compareTo(secondPlayerMock)).thenReturn(-1);
    }

    private void setCardCombinationMocks() {
        firstPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        secondPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        thirdPlayerCardCombinationMock = mock(PlayerCardCombination.class);

//        List<PlayerCardCombination> cardCombinations = new ArrayList<>();
//                                                             TODO meybe remove it?
//        cardCombinations.add(firstPlayerCardCombinationMock);
//        cardCombinations.add(secondPlayerCardCombinationMock);
//        cardCombinations.add(thirdPlayerCardCombinationMock);

        setCombinationToPlayer(firstPlayerCardCombinationMock, firstPlayerMock);
        setCombinationToPlayer(secondPlayerCardCombinationMock, secondPlayerMock);
        setCombinationToPlayer(thirdPlayerCardCombinationMock, thirdPlayerMock);

        setCombinationsRelations(firstPlayerCardCombinationMock, secondPlayerCardCombinationMock);
        setCombinationsRelations(firstPlayerCardCombinationMock, thirdPlayerCardCombinationMock);
        setCombinationsRelations(secondPlayerCardCombinationMock, thirdPlayerCardCombinationMock);
    }

    private void setCombinationToPlayer(PlayerCardCombination combinationMock, Player playerMock) {
        when(playerMock.getCardCombination()).thenReturn(combinationMock);
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

    private void setCombinationsRelations(PlayerCardCombination biggerCombination, PlayerCardCombination smallerCombination) {
        when(biggerCombination.compareTo(smallerCombination)).thenReturn(1);
        when(smallerCombination.compareTo(biggerCombination)).thenReturn(-1);
    }

    private void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        when(player.getStatus()).thenReturn(playerStatus);
    }

    private void setPlayerFold(Player player) {
        setPlayerStatus(player, PlayerStatus.Fold);
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
    public void shouldSecondPlayerWinWhenFirstPlayerFolds() throws Exception {
        setPlayerFold(firstPlayerMock);

        endGameManager.endGame();

        verify(dealerMock).setPlayerWin(secondPlayerMock);
    }

    @Test
    public void shouldFirstPlayerWinWhenSecondPlayerFolds() throws Exception {
        setPlayerFold(secondPlayerMock);

        endGameManager.endGame();

        verify(dealerMock).setPlayerWin(firstPlayerMock);
    }

    @Test
    public void shouldSecondPlayerWinWhenNoPlayerFoldsAndFirstHasWinningCombination() throws Exception {
        endGameManager.endGame();

        verify(dealerMock).setPlayerWin(firstPlayerMock);
    }

    @Test
    public void shouldFirstPlayerWinWhenNoPlayerFoldsAndSecondHasWinningCombination() throws Exception {
        setCombinationsRelations(secondPlayerCardCombinationMock, firstPlayerCardCombinationMock);

        endGameManager.endGame();

        verify(dealerMock).setPlayerWin(secondPlayerMock);
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

        verify(secondPlayerMock).setBalance(750);
    }

    private void setPlayerBet(Player player, int bet) {
        when(player.getBet()).thenReturn(bet);
    }

    /*
    * Задача такая:
    *   есть несколько человек и нужно верно раздать им их выигрыши.
    *   Например, если их 2, то нужно отдать первому то, что он заслужил, а уже второму остаток.
    *
    *   Можно попробовать сделать так:
    *       1. Создаем список из кандидатов на победу.
    *       2. Сортируем его по убыванию.
    *       3. Раздаем кандидатам такую сумму:
    *
    *
     */
}
