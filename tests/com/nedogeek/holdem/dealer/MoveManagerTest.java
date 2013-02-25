package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 13:26
 */
public class MoveManagerTest {
    private final int SMALL_BLIND = GameSettings.SMALL_BLIND_AT_START;

    private PlayerAction playerActionMock;
    List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;

    private Dealer dealerMock;
    private PlayersList playersManagerMock;

    private MoveManager moveManager;

    @Before
    public void setUp() throws Exception {
        playerActionMock = mock(PlayerAction.class);

        resetBank();
        resetPlayersManager();

        moveManager = new MoveManager(dealerMock, playersManagerMock);
    }


    private void setPlayerBet(Player player, int playerBet) {
        when(player.getBet()).thenReturn(playerBet);
    }

    private void giveStartMoneyToPlayers() {
        for (Player player: players) {
            when(player.getBalance()).thenReturn(GameSettings.COINS_AT_START);
        }
    }

    private void resetBank() {
        dealerMock = mock(Dealer.class);
    }

    private void resetPlayersManager() {
        playersManagerMock = mock(PlayersList.class);

        setPlayers();
    }

    private void setPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);

        players = new ArrayList<Player>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);
        when(playersManagerMock.iterator()).thenReturn(players.iterator());

        for (Player player: players) {
            when(player.getMove()).thenReturn(playerActionMock);
        }

        giveStartMoneyToPlayers();
    }

    private void setPlayerAction(PlayerAction.ActionType actionType, int actionValue) {
        when(playerActionMock.getRiseAmount()).thenReturn(actionValue);
        setPlayerAction(actionType);
    }

    private void setPlayerAction(PlayerAction.ActionType actionType) {
        when(playerActionMock.getActionType()).thenReturn(actionType);
    }

    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet2SB() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * SMALL_BLIND);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet1SB() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, SMALL_BLIND);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(2 * SMALL_BLIND);
    }

    @Test
    public void shouldBet40WhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(2 * SMALL_BLIND);
    }


    @Test
    public void shouldFirstPlayerStatusRiseWhenPlayerActionIsDefaultRise() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.Rise);
    }

    @Test
    public void shouldSecondPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setStatus(PlayerStatus.Fold);
    }

    @Test
    public void shouldSecondPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerAllInWhenHeRise2000AndBalanceIs1000() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerFoldWhenHeFolds() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.Fold);
    }


    @Test
    public void shouldSetAllInMoveFirstPlayerWhenFirstRise2CoinsAtStart() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * GameSettings.COINS_AT_START);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetAllInMoveSecondPlayerWhenSecondRise2CoinsAtStart() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2 * GameSettings.COINS_AT_START);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetFirstPlayerCallStatusWhenCall() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.Call);
    }

    @Test
    public void shouldSetSecondPlayerCallStatusWhenCall() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setStatus(PlayerStatus.Call);

    }

    @Test
    public void shouldFirstPlayerBet250WhenCallValueIs250() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(250);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setBet(250);
    }

    @Test
    public void shouldFirstPlayerBet500WhenCallValueIs500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(500);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setBet(500);
    }

    @Test
    public void shouldSecondPlayerBet500WhenCallValueIs500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Call);
        setCallValue(500);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(500);
    }

    private void setCallValue(int callValue) {
        when(dealerMock.getCallValue()).thenReturn(callValue);
    }


    @Test
    public void shouldNoSecondPlayerSetMoveStatusCallWhenFirstRoundGameSecondPlayerFold() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Fold);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.Call);
    }

    @Test
    public void shouldSetCallValue2SmallBlindsWhenInitialBlindFirstPlayer2SmallBlinds() throws Exception {
        moveManager.makeBigBlind(firstPlayerMock);

        verify(dealerMock).setCallValue(2 * SMALL_BLIND);
    }

    @Test
    public void shouldSetCallValue2SmallBlindsWhenInitialBlindSecondPlayer2SmallBlinds() throws Exception {
        moveManager.makeBigBlind(secondPlayerMock);

        verify(dealerMock).setCallValue(2 * SMALL_BLIND);
    }

    @Test
    public void shouldSetCallValueSmallBlindsWhenInitialBlindFirstPlayerSmallBlinds() throws Exception {
        moveManager.makeSmallBlind(firstPlayerMock);

        verify(dealerMock).setCallValue(SMALL_BLIND);
    }

    @Test
    public void shouldSecondPlayerBet2SmallBlindWhenRiseSmallerThan2SmallBlind() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, SMALL_BLIND);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(4 * SMALL_BLIND);
    }

    @Test
    public void shouldSecondPlayerBet500WhenRise500() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 500);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(500);
    }

    @Test
    public void shouldSecondPlayerBet500WhenRise800AndPlayerBetWas300() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 500);
        setPlayerBet(secondPlayerMock, 300);
        setCallValue(2 * SMALL_BLIND);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(800);
    }

    @Test
    public void shouldFirstPlayerAllInWhenPlayerBet2000WithDefaultBalance() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }


    @Test
    public void shouldSecondPlayerAllInWhenPlayerBet2000WithDefaultBalance() throws Exception {
        setPlayerAction(PlayerAction.ActionType.Rise, 2000);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerBatAllHisMoneyWhenAllIn() throws Exception {
        setPlayerAction(PlayerAction.ActionType.AllIn);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setBet(GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldSecondPlayerBatAllHisMoneyWhenAllIn() throws Exception {
        setPlayerAction(PlayerAction.ActionType.AllIn);

        moveManager.makeMove(secondPlayerMock);

        verify(secondPlayerMock).setBet(GameSettings.COINS_AT_START);
    }


    @Test
    public void shouldAllInWhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldNoAllInWhenBalanceIs1000AndCallValueIs500() throws Exception {
        setCallValue(500);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldNoSetPlayerStatusCallWhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.Call);
    }

    @Test
    public void shouldAddToPot1000WhenBalanceIs1000AndCallValueIs2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(dealerMock).addToPot(GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldNotCallValue1000WhenCallValueWas2000() throws Exception {
        setCallValue(2000);
        setPlayerAction(PlayerAction.ActionType.Call);

        moveManager.makeMove(firstPlayerMock);

        verify(dealerMock, never()).setCallValue(1000);
    }


    @Test
    public void shouldFirstPlayerStatusSmallBlindWhenDefaultGameFirstPlayerMakesSmallBlind() throws Exception {
        moveManager.makeSmallBlind(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.SmallBLind);
    }

    @Test
    public void shouldPlayerStatusAllInWhenPlayerBalance10AndMakesSmallBlind() throws Exception {
        when(firstPlayerMock.getBalance()).thenReturn(10);

        moveManager.makeSmallBlind(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldNoPlayerStatusAllInWhenPlayerBalance11AndMakesSmallBlind() throws Exception {
        when(firstPlayerMock.getBalance()).thenReturn(11);

        moveManager.makeSmallBlind(firstPlayerMock);

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldPlayerStatusAllInWhenPlayerBalance20AndMakesBigBlind() throws Exception {
        when(firstPlayerMock.getBalance()).thenReturn(20);

        moveManager.makeBigBlind(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.AllIn);
    }

    @Test
    public void shouldFirstPlayerStatusBigBlindWhenDefaultGameFirstPlayerMakesBigBlind() throws Exception {
        moveManager.makeBigBlind(firstPlayerMock);

        verify(firstPlayerMock).setStatus(PlayerStatus.BigBlind);
    }

    @Test
    public void shouldPlayerStatusAllInWhenPlayerBalance21AndMakesBigBlind() throws Exception {
        when(firstPlayerMock.getBalance()).thenReturn(21);

        moveManager.makeBigBlind(firstPlayerMock);

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.AllIn);
    }
}
