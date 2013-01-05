package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class DealerIntegrationTest {
    private final int COINS_AT_START = 1000;
    private final int SMALL_BLIND = 10;

    private Dealer dealer;
    private Desk deskMock;
    private PlayerAction playerActionMock;

    @Before
    public void setUp() throws Exception {
        resetPlayerActionMock();
        resetDeskMock();

        createDealer();
    }

    private void resetPlayerActionMock() {
        playerActionMock = mock(PlayerAction.class);
    }

    private void createDealer() {
        dealer = new Dealer(deskMock);
    }

    private void resetDeskMock() {
        deskMock = mock(Desk.class);
        setGameStatus(GameStatus.STARTED);
        setGameRound(GameRound.INITIAL);
        int PLAYERS_QUANTITY = 2;
        setPlayersQuantity(PLAYERS_QUANTITY);
        resetPlayersMoves();
        setDealerPlayerNumber(-1);
        when(deskMock.getPlayerBallance(anyInt())).thenReturn(COINS_AT_START);
        when(deskMock.getLastMovedPlayer()).thenReturn(-1);
        setResponseFold();
        when(deskMock.getPlayersMove(anyInt())).thenReturn(playerActionMock);
    }

    private void resetPlayersMoves() {
        when(deskMock.getPlayerStatus(anyInt())).thenReturn(PlayerStatus.NotMoved);
    }

    private void setResponseType(PlayerAction.ActionType actionType) {
        when(playerActionMock.getActionType()).thenReturn(actionType);
    }

    private void setFirstRound() {
        when(deskMock.getGameRound()).thenReturn(GameRound.BLIND);
        setDealerPlayerNumber(0);
        setGameStatus(GameStatus.STARTED);

        setPlayersBet(1, SMALL_BLIND);
        setPlayerAmount(1, COINS_AT_START - SMALL_BLIND);

        setPlayersBet(0, 2 * SMALL_BLIND);
        setPlayerAmount(0, COINS_AT_START - 2 * SMALL_BLIND);

        when(deskMock.getLastMovedPlayer()).thenReturn(-1);

        setCallValue(2 * SMALL_BLIND);
    }

    private void setFirstRoundSecondPlayerDealer() {
        setFirstRound();

        setDealerPlayerNumber(1);

        setPlayersBet(0, SMALL_BLIND);
        setPlayerAmount(0, COINS_AT_START - SMALL_BLIND);

        setPlayersBet(1, 2 * SMALL_BLIND);
        setPlayerAmount(1, COINS_AT_START - 2 * SMALL_BLIND);
    }

    private void setCallValue(int minimumBet) {
        when(deskMock.getCallValue()).thenReturn(minimumBet);
    }

    private void setResponseRise(int bet) {
        setResponseType(PlayerAction.ActionType.Rise);
        when(playerActionMock.getRiseAmount()).thenReturn(bet);
    }

    private void setResponseFold() {
        setResponseType(PlayerAction.ActionType.Fold);
    }
    private void setResponseAllIn() {
        setResponseType(PlayerAction.ActionType.AllIn);
    }

    private void setResponseCall() {
        setResponseType(PlayerAction.ActionType.Call);
    }

    private void setPlayersBet(int playerNumber, int playersBet) {
        when(deskMock.getPlayerBet(playerNumber)).thenReturn(playersBet);
    }

    private void setPlayerAmount(int playerNumber, int amount) {
        when(deskMock.getPlayerBallance(playerNumber)).thenReturn(amount);
    }

    private void setDealerPlayerNumber(int dealerPlayerNumber) {
        when(deskMock.getDealerPlayerNumber()).thenReturn(dealerPlayerNumber);
    }

    private void setPlayersQuantity(int PLAYERS_QUANTITY) {
        when(deskMock.getPlayersQuantity()).thenReturn(PLAYERS_QUANTITY);

        createDealer();
    }

    private void setGameStatus(GameStatus newGameStatus) {
        when(deskMock.getGameStatus()).thenReturn(newGameStatus);
    }

    private void setGameRound(GameRound newGameRound) {
        when(deskMock.getGameRound()).thenReturn(newGameRound);
    }

    @Test
    public void shouldEngineNotNull() throws Exception {
        assertNotNull(dealer);
    }

    @Test
    public void shouldDeskIsGamePossibleWhenStart() throws Exception {
        dealer.tick();

        verify(deskMock, atLeast(1)).getGameStatus();
    }

    @Test
    public void shouldNotSetGameStatusStartedWhenGameStatusNotReady() throws Exception {
        setGameStatus(GameStatus.NOT_READY);

        dealer.run();

        verify(deskMock, never()).setGameStarted();
    }


    @Test
    public void shouldShuffleCardsWhenStartGaming() throws Exception {
        dealer.tick();

        verify(deskMock).resetCards();
    }

    @Test
    public void shouldGetGameRoundNumberWhenTick() throws Exception {
        dealer.tick();

        verify(deskMock).getGameRound();
    }

    @Test
    public void shouldNotGetGameRoundNumberWhenTickAndGameStatusNotReady() throws Exception {
        setGameStatus(GameStatus.NOT_READY);

        dealer.tick();

        verify(deskMock, never()).getGameRound();
    }

    @Test
    public void shouldSetGameRound1WhenTick() throws Exception {
        dealer.tick();

        verify(deskMock).setNextGameRound();
    }

    @Test
    public void shouldNoSecondPlayerBet0WhenFirstRoundAndHeFolds() throws Exception {
        setFirstRound();

        setResponseFold();

        dealer.tick();

        verify(deskMock, never()).setPlayerBet(1, 0);
    }

    @Test
    public void shouldNoSecondPlayerSetStatusRiseWhenFirstGameRoundSecondPlayerCall() throws Exception {
        setFirstRound();
        setResponseCall();

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.Rise);
    }

    @Test
    public void shouldDoNotSetAllInSecondPlayerWhenFirstRoundGameAndSecondPlayerRise0() throws Exception {
        setFirstRound();
        setResponseRise(0);

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.AllIn);
    }

}
