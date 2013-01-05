package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 13:26
 */
public class MoveManagerTest {
    private final int SMALL_BLIND = GameSettings.SMALL_BLIND_AT_START;

    private MoveManager moveManager;

    private PlayerAction playerActionMock;
    private Bank bankMock;
    private PlayersManager playersManagerMock;

    @Before
    public void setUp() throws Exception {
        playerActionMock = mock(PlayerAction.class);

        resetBank();
        giveStartMoneyToPlayers();
        resetPlayersManager();

        moveManager = new MoveManager(bankMock, playersManagerMock);
    }


    private void setPlayerBet(int playerNumber, int playerBet) {
        when(bankMock.getPlayerBet(playerNumber)).thenReturn(playerBet);
    }

    private void giveStartMoneyToPlayers() {
        when(bankMock.getPlayerBalance(anyInt())).thenReturn(GameSettings.COINS_AT_START);
    }

    private void resetBank() {
        bankMock = mock(Bank.class);
    }

    private void resetPlayersManager() {
        playersManagerMock = mock(PlayersManager.class);
    }

    private void setPlayerAction(PlayerAction.ActionType actionType, int actionValue) {
        when(playerActionMock.getRiseAmount()).thenReturn(actionValue);
        setPlayerAction(actionType);
    }

    private void setPlayerAction(PlayerAction.ActionType actionType) {
        when(playerActionMock.getActionType()).thenReturn(actionType);
    }

    private void setPlayerBalance(int playerNumber, int playerBalance) {
        when(bankMock.getPlayerBalance(playerNumber)).thenReturn(playerBalance);
    }

    @Test
    public void shouldSetCallValue200WhenCallValue100AndMakeCallWhenPlayerBetIs100AndPlayerAmount1000() throws Exception {
        when(bankMock.getCallValue()).thenReturn(200);
        when(bankMock.getPlayerBalance(0)).thenReturn(1000);
        when(bankMock.getPlayerBet(0)).thenReturn(100);

        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);


        verify(bankMock).setCallValue(200);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer1WhenMoveManagerMakeMove1PlayerFold() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(1);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer1WhenMoveManagerMakeMove1PlayerCheck() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Check);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(1);
    }

    @Test
    public void shouldPlayersManagerSetLastMovedPlayer0WhenMoveManagerMakeMove0PlayerCheck() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Check);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setLastMovedPlayer(0);
    }


    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet2SB() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet1SB() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }


    @Test
    public void shouldPlayerStatusRiseWhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.Rise);
    }

    @Test
    public void shouldSecondPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.Fold);
    }

    @Test
    public void shouldSecondPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerBalance(1, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerBalance(0, 1000);
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.Fold);
    }


    @Test
    public void shouldSetAllInMoveFirstPlayerWhenFirstRise2CoinsAtStart() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * GameSettings.COINS_AT_START);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetAllInMoveSecondPlayerWhenSecondRise2CoinsAtStart() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * GameSettings.COINS_AT_START);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetFirstPlayerCallStatusWhenCall() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.Call);
    }

    @Test
    public void shouldSetSecondPlayerCallStatusWhenCall() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.Call);

    }

    @Test
    public void shouldFirstPlayerBet250WhenCallValueIs250() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(250);

        moveManager.makeMove(0, playerActionMock);

        verify(bankMock).setPlayerBet(0, 250);
    }

    @Test
    public void shouldFirstPlayerBet500WhenCallValueIs500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(500);

        moveManager.makeMove(0, playerActionMock);

        verify(bankMock).setPlayerBet(0, 500);
    }

    @Test
    public void shouldSecondPlayerBet500WhenCallValueIs500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(500);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 500);
    }

    private void setCallValue(int callValue) {
        when(bankMock.getCallValue()).thenReturn(callValue);
    }


    @Test
    public void shouldNoSecondPlayerSetMoveStatusCallWhenFirstRoundGameSecondPlayerFold() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock, never()).setPlayerStatus(0, PlayerStatus.Call);
    }

    @Test
    public void shouldSetCallValue2SmallBlindsWhenInitialBlindFirstPlayer2SmallBlinds() throws Exception {
        moveManager.makeInitialBet(0, 2 * SMALL_BLIND);

        verify(bankMock).setCallValue(2 * SMALL_BLIND);
    }

    @Test
    public void shouldSetCallValue2SmallBlindsWhenInitialBlindSecondPlayer2SmallBlinds() throws Exception {
        moveManager.makeInitialBet(1, 2 * SMALL_BLIND);

        verify(bankMock).setCallValue(2 * SMALL_BLIND);
    }

    @Test
    public void shouldSetCallValueSmallBlindsWhenInitialBlindFirstPlayerSmallBlinds() throws Exception {
        moveManager.makeInitialBet(0,SMALL_BLIND);

        verify(bankMock).setCallValue(SMALL_BLIND);
    }

    @Test
    public void shouldSecondPlayerBet2SmallBlindWhenRiseSmallerThan2SmallBlind() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, SMALL_BLIND);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 4 * SMALL_BLIND);
    }

    @Test
    public void shouldSecondPlayerBet500WhenRise500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 500);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 500);
    }

    @Test
    public void shouldSecondPlayerBet500WhenRise800AndPlayerBetWas300() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 500);
        setPlayerBet(1, 300);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, 800);
    }

    @Test
    public void shouldFirstPlayerAllInWhenPlayerBet2000WithDefaultBalance() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }


    @Test
    public void shouldSecondPlayerAllInWhenPlayerBet2000WithDefaultBalance() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(1, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerBatAllHisMoneyWhenAllIn() throws Exception {
        setPlayerAction(PlayerAction.ActionType.AllIn);

        moveManager.makeMove(0, playerActionMock);

        verify(bankMock).setPlayerBet(0, GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldSecondPlayerBatAllHisMoneyWhenAllIn() throws Exception {
        setPlayerAction(PlayerAction.ActionType.AllIn);

        moveManager.makeMove(1, playerActionMock);

        verify(bankMock).setPlayerBet(1, GameSettings.COINS_AT_START);
    }


    @Test
    public void shouldAllInWhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldNoAllInWhenBalanceIs1000AndCallValueIs500() throws Exception {
        setCallValue(500);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock, never()).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldNoSetPlayerStatusCallWhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);

        verify(playersManagerMock, never()).setPlayerStatus(0, PlayerStatus.Call);
    }

    @Test
    public void shouldAddToPot1000WhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(0, playerActionMock);

        verify(bankMock).addToPot(GameSettings.COINS_AT_START);
    }

    //TODO Check increase of call value.

    //TODO If AllIn and Balance is smaller than callValue - do not change call value!
}
