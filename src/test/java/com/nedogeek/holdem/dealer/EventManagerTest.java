package com.nedogeek.holdem.dealer;


import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 09.03.13
 * Time: 14:29
 */
public class EventManagerTest {

    private final String FIRST_PLAYER = "FirstPlayer";
    private final String SECOND_PLAYER = "SecondPlayer";

    private final String JSON = "JSON";
    private final String CARDS = "Cards";

    private final String MOVER_NAME = "MoverName";
    private final String DEALER_NAME = "DealerName";
    private final String PLAYERS_JSON = "PlayersJSON";

    private final String INITIAL = "INITIAL";

    private final String EVENT_MOCK = "EventMock";
    private final String READY = "READY";

    private final String DEFAULT_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":\"" + PLAYERS_JSON + "\"," +
            "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String SECOND_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":\"" +

            SECOND_PLAYER + JSON + CARDS

            + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String FIRST_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":\"" +

            FIRST_PLAYER + JSON + CARDS

            + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";


    private EventManager eventManager;

    private WebSocket.Connection firstViewerConnectionMock;
    private WebSocket.Connection secondViewerConnectionMock;
    private WebSocket.Connection firstPlayerConnectionMock;

    private WebSocket.Connection secondPlayerConnectionMock;

    private Event eventMock;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private PlayersList playersListMock;

    private Dealer dealerMock;


    @Before
    public void setUp() throws Exception {
        firstViewerConnectionMock = mock(WebSocket.Connection.class);
        secondViewerConnectionMock = mock(WebSocket.Connection.class);

        firstPlayerConnectionMock = mock(WebSocket.Connection.class);
        secondPlayerConnectionMock = mock(WebSocket.Connection.class);

        resetDealerMock();

        resetPlayersMock();
        resetPlayerLisMock();

        eventMock = mock(Event.class);
        when(eventMock.toString()).thenReturn(EVENT_MOCK);

        eventManager = EventManager.getTestInstance();
        eventManager.setDealer(dealerMock);
        eventManager.setPlayersList(playersListMock);
        eventManager.addViewer(firstViewerConnectionMock);
    }

    private void resetDealerMock() {
        dealerMock = mock(Dealer.class);

        when(dealerMock.getGameStatus()).thenReturn(GameStatus.READY);
        when(dealerMock.getGameRound()).thenReturn(GameRound.INITIAL);
        when(dealerMock.getDeskCards()).thenReturn(new Card[]{});
    }

    private void resetPlayersMock() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);

        when(firstPlayerMock.toJSON()).thenReturn(FIRST_PLAYER + JSON);
        when(secondPlayerMock.toJSON()).thenReturn(SECOND_PLAYER + JSON);
    }

    @SuppressWarnings("unchecked")
    private void resetPlayerLisMock() {
        playersListMock = mock(PlayersList.class);

        List<Player> playersHolder = new ArrayList<>();
        playersHolder.add(firstPlayerMock);
        playersHolder.add(secondPlayerMock);

        when(playersListMock.getMoverName()).thenReturn(MOVER_NAME);
        when(playersListMock.getDealerName()).thenReturn(DEALER_NAME);

        when(playersListMock.generatePlayersJSON()).thenReturn(PLAYERS_JSON);

        when(playersListMock.generatePlayersJSON(FIRST_PLAYER)).thenReturn(FIRST_PLAYER + JSON + CARDS);
        when(playersListMock.generatePlayersJSON(SECOND_PLAYER)).thenReturn(SECOND_PLAYER + JSON + CARDS);

        when(playersListMock.iterator()).thenReturn(playersHolder.iterator(), playersHolder.iterator(), playersHolder.iterator());
    }


    private void setFirstPlayerWin() {

    }

    @After
    public void tearDown() throws Exception {  //TODO RemoveIt???
        eventManager.closeConnection(firstViewerConnectionMock);
        eventManager.closeConnection(secondViewerConnectionMock);
    }

    @Test
    public void shouldFirstViewerMockSendMessageWhenAddGameEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock).sendMessage(anyString());
    }

    @Test
    public void shouldConnectionCloseWhenThirdViewerThrowsIOException() throws Exception {
        WebSocket.Connection throwingConnection = mock(WebSocket.Connection.class);
        Mockito.doThrow(new IOException()).when(throwingConnection).sendMessage(anyString());
        eventManager.addViewer(throwingConnection);

        eventManager.addEvent(eventMock);

        verify(throwingConnection).close();
    }

    @Test
    public void shouldSecondViewerMockSendMessageWhenItAddsToEventManager() throws Exception {
        eventManager.addViewer(secondViewerConnectionMock);

        eventManager.addEvent(eventMock);

        verify(secondViewerConnectionMock).sendMessage(anyString());
    }

    @Test
    public void shouldFirstViewerMockSendMessageWhenSecondViewerAddsToEventManager() throws Exception {
        eventManager.addViewer(secondViewerConnectionMock);

        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock).sendMessage(anyString());
    }

    @Test
    public void shouldNotNotifyFirstViewerWhenConnectionClosed() throws Exception {
        eventManager.closeConnection(firstViewerConnectionMock);

        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock, never()).sendMessage(anyString());
    }

    @Test
    public void shouldPlayersListMockGeneratePlayersJSONWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(playersListMock, atLeast(1)).generatePlayersJSON();
    }

    @Test
    public void shouldDealerGetGameStatusWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(dealerMock, atLeast(1)).getGameStatus();
    }

    @Test
    public void shouldDealerGetGameRoundWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(dealerMock, atLeast(1)).getGameRound();
    }

    @Test
    public void shouldPlayerListGetPotWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(playersListMock, atLeast(1)).getPot();
    }

    @Test
    public void shouldPlayerListGetDealerNameWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(playersListMock, atLeast(1)).getDealerName();
    }

    @Test
    public void shouldPlayerListGetMoverNameWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(playersListMock, atLeast(1)).getMoverName();
    }

    @Test
    public void shouldDeskGetDeskCardsWhenAddSomeEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(dealerMock, atLeast(1)).getDeskCards();
    }

    @Test
    public void shouldDefaultMessageSendToFirstViewerMockEqualsDefaultMessage() throws Exception {
        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock).sendMessage(DEFAULT_MESSAGE);
    }

    @Test
    public void shouldDefaultMessageSendToSecondViewerMockEqualsDefaultMessage() throws Exception {
        eventManager.addViewer(secondViewerConnectionMock);

        eventManager.addEvent(eventMock);

        verify(secondViewerConnectionMock).sendMessage(DEFAULT_MESSAGE);
    }

    @Test
    public void shouldInMessageFirstPlayerWithCardsSendToFirstPlayerConnection() throws Exception {
        eventManager.addPlayer(firstPlayerConnectionMock, FIRST_PLAYER);

        eventManager.addEvent(eventMock);

        verify(firstPlayerConnectionMock).sendMessage(FIRST_PLAYER_WITH_CARDS_MESSAGE);
    }

    @Test
    public void shouldInMessageSecondPlayerWithCardsSendToSecondPlayerConnection() throws Exception {
        eventManager.addPlayer(firstPlayerConnectionMock, SECOND_PLAYER);

        eventManager.addEvent(eventMock);

        verify(firstPlayerConnectionMock).sendMessage(SECOND_PLAYER_WITH_CARDS_MESSAGE);
    }

    @Test
    public void shouldNewerPlayersCardsSendToViewersWhenBothPlayerConnectionsAdded() throws Exception {
        eventManager.addPlayer(firstPlayerConnectionMock, FIRST_PLAYER);
        eventManager.addPlayer(secondPlayerConnectionMock, SECOND_PLAYER);

        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock, never()).sendMessage(FIRST_PLAYER_WITH_CARDS_MESSAGE);
        verify(firstViewerConnectionMock, never()).sendMessage(SECOND_PLAYER_WITH_CARDS_MESSAGE);

        verify(secondViewerConnectionMock, never()).sendMessage(FIRST_PLAYER_WITH_CARDS_MESSAGE);
        verify(secondViewerConnectionMock, never()).sendMessage(SECOND_PLAYER_WITH_CARDS_MESSAGE);
    }

    @Test
    public void shouldNewerPlayersCardsSendToNotPropitiatePlayerConnectionsWhenBothPlayerConnectionsAdded() throws Exception {
        eventManager.addPlayer(firstPlayerConnectionMock, FIRST_PLAYER);
        eventManager.addPlayer(secondPlayerConnectionMock, SECOND_PLAYER);

        eventManager.addEvent(eventMock);

        verify(firstPlayerConnectionMock, never()).sendMessage(SECOND_PLAYER_WITH_CARDS_MESSAGE);
        verify(secondPlayerConnectionMock, never()).sendMessage(FIRST_PLAYER_WITH_CARDS_MESSAGE);
    }

    @Test
    public void shouldSecondPlayerConnectionCanViewBothPlayersCardsWhenFirstPlayerWon() throws Exception {
        eventManager.addPlayer(secondPlayerConnectionMock, SECOND_PLAYER);

        setFirstPlayerWin();
    }

    /*
    * Задача такая: нужно отправлять каждому коннекшену именно то, что ему нужно:
    *   - Его карты.
    *   - Может и другие карты, если позволяет игровой раунд.

     Из чего будет состоять сообщение для игрока:
     - Игровой раунд.
     - Игровой статус.
     - Игровой пот.
     - Карты на столе.
     - Игровая комбинация. (если игрок залогинен)
     - Последнее игровое сообщение.
     - Игроков с картами или же без карт:
        С картами, если они победители и сейчас конец игры.
        Без карт в других случаях.

     - Еще можно отправить специальный маркерный параметр, который будет сообщать, что игроку нужно походить.

    * */
}
