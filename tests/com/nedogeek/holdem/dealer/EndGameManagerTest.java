package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.gamingStuff.Desk;
import com.nedogeek.holdem.gamingStuff.Player;
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
    private Desk deskMock;
    private PlayersManager playersManagerMock;

    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    private List<PlayerCardCombination> cardCombinations;
    private PlayerCardCombination firstPlayerCardCombinationMock;
    private PlayerCardCombination secondPlayerCardCombinationMock;
    private PlayerCardCombination thirdPlayerCardCombinationMock;

    private EndGameManager endGameManager;


    @Before
    public void setUp() throws Exception {
        resetDeskMock();
        resetPlayersMocks();
        resetPlayerManagerMock();

        endGameManager = new EndGameManager(deskMock, playersManagerMock);
    }

    private void resetPlayersMocks() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        thirdPlayerMock = mock(Player.class);

        players = new ArrayList<Player>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);

        for (Player player: players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
        }

        setCardCombinationMocks();
    }

    private void setCardCombinationMocks() {
        firstPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        secondPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        thirdPlayerCardCombinationMock = mock(PlayerCardCombination.class);

        cardCombinations = new ArrayList<PlayerCardCombination>();

        cardCombinations.add(firstPlayerCardCombinationMock);
        cardCombinations.add(secondPlayerCardCombinationMock);
        cardCombinations.add(thirdPlayerCardCombinationMock);

        setCombinationToPlayer(firstPlayerCardCombinationMock, firstPlayerMock);
        setCombinationToPlayer(secondPlayerCardCombinationMock, secondPlayerMock);
        setCombinationToPlayer(thirdPlayerCardCombinationMock, thirdPlayerMock);

        setCombinationsRelations(firstPlayerCardCombinationMock, secondPlayerCardCombinationMock);
    }

    private void setCombinationToPlayer(PlayerCardCombination combinationMock, Player playerMock) {
        when(playerMock.getCardCombination()).thenReturn(combinationMock);
    }

    private void resetDeskMock() {
        deskMock = mock(Desk.class);
    }

    private void resetPlayerManagerMock() {
        playersManagerMock = mock(PlayersManager.class);

        when(playersManagerMock.getPlayers()).thenReturn(players);
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

    private void setPlayerLost(Player player) {
        setPlayerStatus(player, PlayerStatus.Lost);
    }


    @Test
    public void shouldEndGameManagerNotNullWhenCreated() throws Exception {
        assertNotNull(endGameManager);
    }

    @Test
    public void shouldDeskSetNewGameRoundWhenEGMEndGame() throws Exception {
        endGameManager.endGame();

        verify(deskMock).setGameEnded();
    }

    @Test
    public void shouldSecondPlayerWinWhenFirstPlayerFolds() throws Exception {
        setPlayerFold(firstPlayerMock);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(secondPlayerMock);
    }

    @Test
    public void shouldSecondPlayerWinWhenFirstPlayerLost() throws Exception {
        setPlayerLost(firstPlayerMock);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(secondPlayerMock);
    }

    @Test
    public void shouldFirstPlayerWinWhenSecondPlayerFolds() throws Exception {
        setPlayerFold(secondPlayerMock);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(firstPlayerMock);
    }

    @Test
    public void shouldSecondPlayerWinWhenNoPlayerFoldsAndFirstHasWinningCombination() throws Exception {
        endGameManager.endGame();

        verify(deskMock).setPlayerWin(firstPlayerMock);
    }

    @Test
    public void shouldFirstPlayerWinWhenNoPlayerFoldsAndSecondHasWinningCombination() throws Exception {
        setCombinationsRelations(secondPlayerCardCombinationMock,firstPlayerCardCombinationMock);

        endGameManager.endGame();

        verify(deskMock).setPlayerWin(secondPlayerMock);
    }
}
