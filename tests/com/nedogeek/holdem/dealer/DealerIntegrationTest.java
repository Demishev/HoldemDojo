package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.connections.PlayerAction;
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
        when(deskMock.getPlayerAmount(anyInt())).thenReturn(COINS_AT_START);
        when(deskMock.getLastMovedPlayer()).thenReturn(-1);
        setResponseFold();
        when(deskMock.getPlayersMove(anyInt())).thenReturn(playerActionMock);
    }

    private void resetPlayersMoves() {
        when(deskMock.getPlayerStatus(anyInt())).thenReturn(PlayerStatus.NotMoved);
    }

    private void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
        when(deskMock.getPlayerStatus(playerNumber)).thenReturn(playerStatus);
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

    private void setLastPlayerMoved(int movedPlayerNumber) {
        when(deskMock.getLastMovedPlayer()).thenReturn(movedPlayerNumber);
    }

    private void setPlayerAmount(int playerNumber, int amount) {
        when(deskMock.getPlayerAmount(playerNumber)).thenReturn(amount);
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

        verify(deskMock, never()).setGameStatus(GameStatus.STARTED);
    }

    @Test
    public void shouldSetGameStatusStartedWhenGameStatusReady() throws Exception {
        setGameStatus(GameStatus.READY);

        dealer.tick();

        verify(deskMock).setGameStatus(GameStatus.STARTED);
    }

    @Test
    public void shouldGetPlayersQuantityWhenGameStarted() throws Exception {
        setGameStatus(GameStatus.READY);

        dealer.run();

        verify(deskMock, atLeast(1)).getPlayersQuantity();
    }

    @Test
    public void shouldFirstPlayerSetDefaultAmountWhenGameStarted() throws Exception {
        setGameStatus(GameStatus.READY);

        dealer.tick();

        verify(deskMock).setPlayerAmount(0, GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldSecondPlayerSetDefaultAmountWhenGameStarted() throws Exception {
        setGameStatus(GameStatus.READY);

        dealer.tick();

        verify(deskMock).setPlayerAmount(1, GameSettings.COINS_AT_START);
    }

    @Test
    public void shouldSetDealerPlayer0WhenStartGaming() throws Exception {
        dealer.tick();

        verify(deskMock).setDealerPlayer(0);
    }

    @Test
    public void shouldShuffleCardsWhenStartGaming() throws Exception {
        dealer.tick();

        verify(deskMock).shuffleCards();
    }

    @Test
    public void shouldSecondPlayerGiveSmallBlindWhenGameStarted() throws Exception {
        dealer.tick();

        verify(deskMock).setPlayerBet(1, GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldFirstPlayerGiveBigBlindWhenGameStarted() throws Exception {
        setGameStatus(GameStatus.STARTED);

        dealer.tick();

        verify(deskMock).setPlayerBet(0, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldThirdPlayerGiveBigBlindWhenGameStartedWith3Players() throws Exception {
        setPlayersQuantity(3);

        dealer.tick();

        verify(deskMock).setPlayerBet(2, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldSetDealerPlayerNumber1WhenPreviousDealerPlayerNumberWas0() throws Exception {
        setDealerPlayerNumber(0);

        dealer.tick();

        verify(deskMock).setDealerPlayer(1);
    }

    @Test
    public void shouldSmallBlindAddedToPotWhenGameStarted() throws Exception {
        dealer.tick();

        verify(deskMock).addToPot(GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldBigBlindAddedToPotWhenGameStarted() throws Exception {
        dealer.tick();

        verify(deskMock).addToPot(GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldSecondPlayerAmountMinusSmallBlindWhenGameStarted() throws Exception {
        dealer.tick();

        verify(deskMock).setPlayerAmount(1, GameSettings.COINS_AT_START - GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldBet5WhenFirstPlayerHasOnly5Coins() throws Exception {
        setPlayerAmount(0, 5);

        dealer.tick();

        verify(deskMock).setPlayerBet(0, 5);
    }

    @Test
    public void shouldFirstPlayerAmountIs0HasOnly5Coins() throws Exception {
        setPlayerAmount(0, 5);

        dealer.tick();

        verify(deskMock).setPlayerAmount(0, 0);
    }

    @Test
    public void shouldFirstPlayerMoveRequestWhenNewGameStartedAndDealerIsSecond() throws Exception {
        setGameRound(GameRound.BLIND);
        setDealerPlayerNumber(1);

        dealer.tick();

        verify(deskMock).getPlayersMove(0);
    }

    @Test
    public void shouldSecondPlayerMoveRequestWhenTickAndNewGameSet() throws Exception {
        setGameRound(GameRound.BLIND);
        setDealerPlayerNumber(0);

        dealer.tick();

        verify(deskMock).getPlayersMove(1);
    }

    @Test
    public void shouldNoGetPlayerMoveWhenStatusNotReadyAndTick() throws Exception {
        setGameStatus(GameStatus.NOT_READY);

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
    public void shouldNotSetGameRound1WhenTickGameRoundIs1() throws Exception {
        setGameRound(GameRound.BLIND);

        dealer.tick();

        verify(deskMock, never()).setNextGameRound();
    }

    @Test
    public void shouldMoveSecondPlayerWhenFirstPlayerMovedLastFirstRoundFirstPlayerBet100Second50() throws Exception {
        setFirstRound();

        dealer.tick();

        verify(deskMock).getPlayersMove(1);
    }

    @Test
    public void shouldSetLastMovedPlayer1WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet100Second50AndPlayerActionIsBet500() throws Exception {
        setFirstRound();

        dealer.tick();

        verify(deskMock).setLastMovedPlayer(1);
    }


    @Test
    public void shouldBet50WhenFirstPlayerMovedLastFirstRoundFirstPlayerBet50Second50AndPlayerActionIsBet500() throws Exception {
        setFirstRound();

        setResponseRise(2 * SMALL_BLIND);

        dealer.tick();

        verify(deskMock).setPlayerBet(1, 4 * SMALL_BLIND);
    }

    @Test
    public void shouldMovedFirstPlayerWhenDefaultFirstRound() throws Exception {
        setFirstRound();

        dealer.tick();

        verify(deskMock).getPlayersMove(1);
    }

    @Test
    public void shouldSecondPlayerFoldWhenFirstRoundAndHeFolds() throws Exception {
        setFirstRound();

        setResponseFold();

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.Fold);
    }

    @Test
    public void shouldFirstPlayerFoldWhenFirstRoundAndFirstMovesAndHeFolds() throws Exception {
        setFirstRound();

        setPlayersBet(1,2 * SMALL_BLIND);
        setLastPlayerMoved(1);

        setResponseFold();

        dealer.tick();

        verify(deskMock).setPlayerStatus(0, PlayerStatus.Fold);
    }

    @Test
    public void shouldNoSecondPlayerFoldWhenFirstRoundAndHeBet2SmallBlinds() throws Exception {
        setFirstRound();

        setResponseRise(2 * SMALL_BLIND);

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.Fold);
    }

    @Test
    public void shouldSecondPlayerBet1WhenFirstRoundAndSecondCalls() throws Exception {
        setFirstRound();

        setResponseCall();

        dealer.tick();

        verify(deskMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldNoSecondPlayerBet0WhenFirstRoundAndHeFolds() throws Exception {
        setFirstRound();

        setResponseFold();

        dealer.tick();

        verify(deskMock, never()).setPlayerBet(1, 0);
    }

    @Test
    public void shouldSecondPlayerBet2SmallBlindWhenCallInFirstRoundWithCallValue2SmallBlind() throws Exception {
        setFirstRound();
        setCallValue(2 * SMALL_BLIND);

        setResponseCall();

        dealer.tick();

        verify(deskMock).setPlayerBet(1, 2 * SMALL_BLIND);
    }

    @Test
    public void shouldSetCallValue2SmallBlindsWhenNewGame() throws Exception {
        setGameStatus(GameStatus.STARTED);

        dealer.tick();

        verify(deskMock).setCallValue(2 * GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldFirstPlayerBetSmallBlindWhenFirstRoundWithSecondPlayerDealer() throws Exception {
        setFirstRoundSecondPlayerDealer();
        setResponseCall();

        dealer.tick();

        verify(deskMock).setPlayerBet(0,2 * SMALL_BLIND);
    }

    @Test
    public void shouldSecondPlayerBet2SmallBlindWhenRiseSmallerThan2SmallBlind() throws Exception {
        setFirstRound();
        setResponseRise(SMALL_BLIND / 2);

        dealer.tick();

        verify(deskMock).setPlayerBet(1, 4 * SMALL_BLIND);
    }

    @Test
    public void shouldSetNextGameRoundWhenGameStage1AndAllPlayersMoved() throws Exception {
        setFirstRound();

        setPlayersBet(1, 2 * SMALL_BLIND);
        setPlayerStatus(1, PlayerStatus.Check);

        setPlayerStatus(0, PlayerStatus.Call);
        setLastPlayerMoved(0);

        dealer.tick();

        verify(deskMock).setNextGameRound();

    }

    @Test
    public void shouldSetSecondPlayerCallStatusWhenFirstRoundAndHeCall() throws Exception {
        setFirstRound();
        setResponseCall();

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.Call);
    }

    @Test
    public void shouldFirstPlayerCallWhenFirstRoundWithSecondPlayerDealerAndFirstCalls() throws Exception {
        setFirstRoundSecondPlayerDealer();
        setResponseCall();

        dealer.tick();

        verify(deskMock).setPlayerStatus(0,PlayerStatus.Call);
    }

    @Test
    public void shouldNoSecondPlayerSetMoveStatusCallWhenFirstRoundGameSecondPlayerFold() throws Exception {
        setFirstRound();
        setResponseFold();

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.Call);
    }

    @Test
    public void shouldSecondPlayerSetFoldStatusCallWhenFirstRoundGameSecondPlayerFold() throws Exception {
        setFirstRound();
        setResponseFold();

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.Fold);
    }

    @Test
    public void shouldSecondPlayerSetStatusBetWhenFirstGameRoundSecondPlayerBet() throws Exception {
        setFirstRound();
        setResponseRise(0);

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.Rise);
    }

    @Test
    public void shouldNoSecondPlayerSetStatusRiseWhenFirstGameRoundSecondPlayerCall() throws Exception {
        setFirstRound();
        setResponseCall();

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.Rise);
    }

    @Test
    public void shouldFirstPlayerSetStatusBetWhenFirstGameRoundWithDealerSecondPlayerSecondPlayerBet() throws Exception {
        setFirstRoundSecondPlayerDealer();
        setResponseRise(0);

        dealer.tick();

        verify(deskMock).setPlayerStatus(0, PlayerStatus.Rise);
    }

    @Test
    public void shouldSetAllInMoveSecondPlayerWhenFirstRoundGameAndSecondRise2CoinsAtStart() throws Exception {
        setFirstRound();
        setResponseRise(2 * COINS_AT_START);

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetAllInMoveFirstPlayerWhenFirstRoundGameWithSecondPlayerDealerAndSecondRise2CoinsAtStart() throws Exception {
        setFirstRoundSecondPlayerDealer();
        setResponseRise(2 * COINS_AT_START);

        dealer.tick();

        verify(deskMock).setPlayerStatus(0, PlayerStatus.AllIn);
    }

    @Test
    public void shouldDoNotSetAllInSecondPlayerWhenFirstRoundGameAndSecondPlayerRise0() throws Exception {
        setFirstRound();
        setResponseRise(0);

        dealer.tick();

        verify(deskMock, never()).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldSetAllInSecondPlayerWhenFirstRoundGameAndSecondPlayerAllIn() throws Exception {
        setFirstRound();
        setResponseAllIn();

        dealer.tick();

        verify(deskMock).setPlayerStatus(1, PlayerStatus.AllIn);
    }

    @Test
    public void shouldNameWhen() throws Exception {
        setFirstRound();
        setResponseAllIn();

        dealer.tick();

        verify(deskMock).setPlayerBet(1, COINS_AT_START);
    }

    //TODO New round when all players bet is same as call bet or fold

    /*
       Р§С‚Рѕ Р±С‹ РµС‰Рµ РїРѕС‚РµСЃС‚РёС‚СЊ:
       РљСЂСѓРі Р·Р°РєСЂС‹РІР°РµС‚СЃСЏ, РєРѕРіРґР° РѕРґРёРЅР°РєРѕРІРѕРµ РєРѕР»-РІРѕ СЃС‚Р°РІРѕРє.
       РљСЂСѓРі Р·Р°РєСЂС‹РІР°РµС‚СЃСЏ, РєРѕРіРґР° РѕСЃС‚Р°Р»СЃСЏ РѕРґРёРЅ РЅРµ С„РѕР»РґР°РЅСѓРІС€РёР№.

       Р•СЃР»Рё РёРіСЂРѕРє С„РѕР»РґР°РЅСѓР» - РЅСѓР¶РЅРѕ СЃС‚Р°РІРёС‚СЊ РµРјСѓ СЃРѕРѕС‚РІРµС‚СЃС‚РІСѓСЋС‰РёР№ СЃС‚Р°С‚СѓСѓСЃ.       ok
       Р•СЃР»Рё РёРіСЂРѕРє Р·Р°РєРѕР»РёР» - РЅСѓР¶РЅРѕ С„РёРіР°С‡РёС‚СЊ СЃСѓРјРјСѓ РјРёРЅРёРјР°Р»СЊРЅСѓСЋ РґР»СЏ С…РѕРґР° РёРіСЂС‹.
       Р•СЃР»Рё РёРіСЂРѕРє СѓС€РµР» РІ РѕР»Р»РёРЅ - РЅСѓР¶РЅРѕ РґРµР»Р°С‚СЊ РјР°РєСЃРёРјР°Р»СЊРЅСѓСЋ СЃС‚Р°РІРєСѓ.
       Р•СЃР»Рё РёРіСЂРѕРє Р·Р°РєРѕР»РёР», РЅРѕ Сѓ РЅРµРіРѕ РЅРµ С…РІР°С‚Р°РµС‚ С„РёС€РµРє - РЅСѓР¶РЅРѕ РґРµР»Р°С‚СЊ РѕР»Р»РёРЅ.
       Р•СЃР»Рё РёРіСЂРѕРє СЂР°Р№Р·Р°РЅСѓР», РЅРѕ СЌС‚РѕРіРѕ РЅРµ С…РІР°С‚Р°РµС‚ - СЌС‚Рѕ С„РѕР»Рґ.
       Р•СЃР»Рё РёРіСЂРѕРє С‡РµРєР°РЅСѓР», РЅРѕ СЌС‚РѕРіРѕ РЅРµР»СЊР·СЏ Р±С‹Р»Рѕ РґРµР»Р°С‚СЊ - СЌС‚Рѕ С„РѕР»Рґ.
       Р•СЃР»Рё РёРіСЂРѕРє СЂР°Р№Р·Р°РЅСѓР» РґРѕ РїСЂРµРґРµР»Р°, РєРѕРіРґР° Сѓ РЅРµРіРѕ РЅРµ С…РІР°С‚Р°РµС‚ С„РёС€РµРє РЅР° СЂР°Р·Р№ РёР»Рё СЂРѕРІРЅРѕ СЃС‚РѕР»СЊРєРѕ - СЌС‚Рѕ РѕР»Р»РёРЅ.

       Р•СЃР»Рё Сѓ РёРіСЂРѕРєР° РѕР»Р»РёРЅ - С‚Рѕ РЅРµ РЅСѓР¶РЅРѕ РµРіРѕ С‚СЂРѕРіР°С‚СЊ.
       РҐРѕРґ РЅРµ РґРѕР»Р¶РµРЅ РїРµСЂРµС…РѕРґРёС‚СЊ Рє С„РѕР»РґР°РЅСѓРІС€РµРјСѓ РёРіСЂРѕРєСѓ.


       Р�РіСЂР° Р·Р°РєР°РЅС‡РёРІР°РµС‚СЃСЏ, РєРѕРіРґР° РѕСЃС‚Р°Р»СЃСЏ РѕРґРёРЅ С„РѕР»РґР°РЅСѓРІС€РёР№.
       Р�РіСЂР° Р·Р°РєР°РЅС‡РёРІР°РµС‚СЃСЏ, РєРѕРіРґР° Р·Р°РєСЂС‹Р»СЃСЏ 3-Р№ РєСЂСѓРі.

       РљРѕРЅРµС† РёРіСЂС‹ Р±СѓРґРµС‚ 4-Р№ СЌС‚Р°Рї. Р� РІ Р»СЋР±РѕРј СЃР»СѓС‡Р°Рµ РЅСѓР¶РЅРѕ РёРґС‚Рё РѕС‚ РЅРµРіРѕ..
       РљСЃС‚Р°С‚Рё, Р¶РµР»Р°С‚РµР»СЊРЅРѕ, С‡С‚РѕР±С‹ Р±С‹Р» С‚Р°РєРѕР№ РјРµС‚РѕРґ, С‚РёРїР° goToEndStage()

       Р•СЃР»Рё РІС‹РёРіСЂР°Р» РёРіСЂРѕРє СЃ РѕР»Р»РёРЅ - С‚Рѕ РЅСѓР¶РЅРѕ РѕС‚РґР°С‚СЊ РµРјСѓ С‚РѕР»СЊРєРѕ С‚Сѓ С‡Р°СЃС‚СЊ РґРµРЅРµРі, РЅР° РєРѕС‚РѕСЂСѓСЋ РѕРЅ Р·Р°СЃР»СѓР¶РёРІР°РµС‚.
       РћСЃС‚Р°Р»СЊРЅС‹Рµ Р¶Рµ РґРµРЅСЊРіРё РЅСѓР¶РЅРѕ СЂР°Р·С‹РіСЂС‹РІР°С‚СЊ РјРµР¶РґСѓ РґСЂСѓРіРёРјРё РёРіСЂРѕРєР°РјРё.
       Рђ СЃСЂРµРґРё РЅРёС… С‚РѕР¶Рµ РјРѕР¶РµС‚ Р±С‹С‚СЊ С‚РѕС‚, РєС‚Рѕ РѕР»Р»РёРЅ СЃРєР°Р·Р°Р». РџСЂР°РІРёР»Рѕ С‚Р°РєРѕРµ - Р±Р°РЅРєРё РЅСѓР¶РЅРѕ РґРµР»РёС‚СЊ РїРѕ РѕС‡РµСЂРµРґРё.
       Р’РѕС‚ РєР°Рє. Рў.Рµ. РґРѕР»Р¶РЅР° Р±С‹С‚СЊ РІРѕР·РјРѕР¶РЅРѕСЃС‚СЊ СЃРѕР·РґР°РЅРёСЏ РјРЅРѕРіРёС… Р±Р°РЅРєРѕРІ...


       Рђ РїРѕСЃР»Рµ С‚РѕРіРѕ, РєР°Рє Р·Р°РєР°РЅС‡РёРІР°РµС‚СЃСЏ РёРіСЂР° - РЅСѓР¶РЅРѕ РІС‹РїРѕР»РЅРёС‚СЊ С‡РµРіРѕ-С‚Рѕ С‚РёРїР° newGame()
       РќРѕ, РµСЃР»Рё РЅРё Сѓ РєРѕРіРѕ С„РёС€РµРє РЅРµ РѕСЃС‚Р°Р»РѕСЃСЊ - РЅСѓР¶РЅРѕ Р·Р°РєРѕРЅС‡РёС‚СЊ РёРіСЂРѕРІРѕР№ С†РёРєР», Р·Р°РїРёСЃР°С‚СЊ РїРѕР±РµРґРёС‚РµР»СЏ,
       РІС‹Р±СЂР°С‚СЊ РЅРѕРІРѕРіРѕ РґРёР»Р»РµСЂР° Рё Р·Р°РЅРѕРІРѕ.
    */
}
