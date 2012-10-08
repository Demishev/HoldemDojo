package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class DealerTest {
    private Dealer dealer;
    private Desk deskMock;

    @Before
    public void setUp() throws Exception {
        resetDeskMock();

        createDealer();
    }

    private void createDealer() {
        dealer = new Dealer(deskMock);
    }

    private void resetDeskMock() {
        deskMock = mock(Desk.class);
        setGameStatus(GameStatus.Ready);
        int PLAYERS_QUANTITY = 2;
        setPlayersQuantity(PLAYERS_QUANTITY);
        setDealerPlayerNumber(-1);
        when(deskMock.getPlayerAmount(anyInt())).thenReturn(GameSettings.COINS_AT_START);
    }

    private void setPlayerAmount(int playerNumber, int amount) {
        when(deskMock.getPlayerAmount(playerNumber)).thenReturn(amount);
    }

    private void setDealerPlayerNumber(int dealerPlayerNumber) {
        when(deskMock.getDealerPlayerNumber()).thenReturn(dealerPlayerNumber);
    }

    private void setPlayersQuantity(int PLAYERS_QUANTITY) {
        when(deskMock.getPlayersQuantity()).thenReturn(PLAYERS_QUANTITY);
    }

    private void setGameStatus(GameStatus newGameStatus) {
        when(deskMock.getGameStatus()).thenReturn(newGameStatus);
    }

    @Test
    public void shouldEngineNotNull() throws Exception {
        assertNotNull(dealer);
    }

    @Test
    public void shouldDeskIsGamePossibleWhenStart() throws Exception {
        dealer.run();

        verify(deskMock, atLeast(1)).getGameStatus();
    }

    @Test
    public void shouldNotSetGameStatusStartedWhenGameStatusNotReady() throws Exception {
        setGameStatus(GameStatus.Not_Ready);

        dealer.run();

        verify(deskMock, never()).setGameStatus(GameStatus.Started);
    }

    @Test
    public void shouldSetGameStatusStartedWhenGameStatusReady() throws Exception {
        dealer.run();

        verify(deskMock).setGameStatus(GameStatus.Started);
    }

    @Test
    public void shouldGetPlayersQuantityWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).getPlayersQuantity();
    }

    @Test
    public void shouldFirstPlayerSetDefaultAmountWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).setPlayerAmount(0, GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldSecondPlayerSetDefaultAmountWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).setPlayerAmount(1, GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldNoDeskGetPlayersQuantityWhenGameStatusIsNotReady() throws Exception {
        setGameStatus(GameStatus.Not_Ready);

        dealer.run();

        verify(deskMock, never()).getPlayersQuantity();
    }

    @Test
    public void shouldSetDealerPlayer0WhenStartGaming() throws Exception {
        dealer.run();

        verify(deskMock).setDealerPlayer(0);
    }

    @Test
    public void shouldShuffleCardsWhenStartGaming() throws Exception {
        dealer.run();

        verify(deskMock).shuffleCards();
    }

    @Test
    public void shouldSecondPlayerGiveSmallBlindWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).setPlayerBet(1,GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldFirstPlayerGiveBigBlindWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).setPlayerBet(0,GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldThirdPlayerGiveBigBlindWhenGameStartedWith3Players() throws Exception {
        setPlayersQuantity(3);

        dealer.run();

        verify(deskMock).setPlayerBet(2,GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldSetDealerPlayerNumber1WhenPreviousDealerPlayerNumberWas0() throws Exception {
        setDealerPlayerNumber(0);

        dealer.run();

        verify(deskMock).setDealerPlayer(1);
    }

    @Test
    public void shouldSmallBlindAddedToPotWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).addToPot(GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldBigBlindAddedToPotWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).addToPot(GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldSecondPlayerAmountMinusSmallBlindWhenGameStarted() throws Exception {
        dealer.run();

        verify(deskMock).setPlayerAmount(1, GameSettings.COINS_AT_START - GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldBet5WhenFirstPlayerHasOnly5Coins() throws Exception {
        setPlayerAmount(0, 5);

        dealer.run();

        verify(deskMock).setPlayerBet(0, 5);
    }

    @Test
    public void shouldFirstPlayerAmountIs0HasOnly5Coins() throws Exception {
        setPlayerAmount(0, 5);

        dealer.run();

        verify(deskMock).setPlayerAmount(0, 0);
    }

    @Test
    public void shouldFirstPlayerMoveRequestWhenNewGameStartedAndDealerIsSecond() throws Exception {
        setDealerPlayerNumber(1);

        dealer.run();

        verify(deskMock).getPlayersMove(0);
    }

    @Test
    public void shouldSecondPlayerMoveRequestWhenTickAndNewGameSet() throws Exception {
        setDealerPlayerNumber(0);

        dealer.tick();

        verify(deskMock).getPlayersMove(1);
    }

    @Test
    public void shouldNoGetPlayerMoveWhenStatusNotReadyAndTick() throws Exception {
        setGameStatus(GameStatus.Not_Ready);

        dealer.tick();

        verify(deskMock, never()).getPlayersMove(1);
    }

    @Test
    public void shouldGetGameRoundNumberWhenTick() throws Exception {
        dealer.tick();

        verify(deskMock).getGameRound();
    }

    @Test
    public void shouldNotGetGameRoundNumberWhenTickAndGameStatusNotReady() throws Exception {
        setGameStatus(GameStatus.Not_Ready);

        dealer.tick();

        verify(deskMock, never()).getGameRound();
    }
}
