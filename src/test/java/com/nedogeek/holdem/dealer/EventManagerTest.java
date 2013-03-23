package com.nedogeek.holdem.dealer;


import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final String FIRST_PLAYER_CARD_COMBINATION = "First player cards combination";
    private final String SECOND_PLAYER_CARD_COMBINATION = "Second player cards combination";

    private final String DEFAULT_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" + PLAYERS_JSON + "," +
            "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String SECOND_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" +
            SECOND_PLAYER + JSON + CARDS +
            ",combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
            + "\"," + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String FIRST_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" +
            FIRST_PLAYER + JSON + CARDS
            + ",\"combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
            + "\"," +
            "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";


    private EventManager eventManager;

    private WebSocket.Connection firstViewerConnectionMock;
    private WebSocket.Connection secondViewerConnectionMock;
    private WebSocket.Connection firstPlayerConnectionMock;

    private WebSocket.Connection secondPlayerConnectionMock;

    private Event eventMock;

    private GameEndedEvent gameEndedEventMock;

    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private PlayersList playersListMock;

    private final String FIRST_CARD_JSON = "FirstCardJSON";
    private final String SECOND_CARD_JSON = "SecondCardJSON";

    private Card firstCardMock;
    private Card secondCardMock;

    private Dealer dealerMock;


    @Before
    public void setUp() throws Exception {
        resetConnections();

        resetDealerMock();

        resetPlayersMock();
        resetPlayerListMock();

        resetCards();

        eventMock = mock(Event.class);
        when(eventMock.toString()).thenReturn(EVENT_MOCK);

        gameEndedEventMock = mock(GameEndedEvent.class);
        when(gameEndedEventMock.toString()).thenReturn("Game ended");

        eventManager = EventManager.getTestInstance();
        eventManager.setDealer(dealerMock);
        eventManager.setPlayersList(playersListMock);
        eventManager.addViewer(firstViewerConnectionMock);
    }

    private void resetCards() {
        firstCardMock = mock(Card.class);
        secondCardMock = mock(Card.class);

        when(firstCardMock.toJSON()).thenReturn(FIRST_CARD_JSON);
        when(secondCardMock.toJSON()).thenReturn(SECOND_CARD_JSON);
    }

    private void resetConnections() {
        firstViewerConnectionMock = mock(WebSocket.Connection.class);
        secondViewerConnectionMock = mock(WebSocket.Connection.class);

        firstPlayerConnectionMock = mock(WebSocket.Connection.class);
        secondPlayerConnectionMock = mock(WebSocket.Connection.class);

        when(firstViewerConnectionMock.isOpen()).thenReturn(true);
        when(secondViewerConnectionMock.isOpen()).thenReturn(true);

        when(firstPlayerConnectionMock.isOpen()).thenReturn(true);
        when(secondViewerConnectionMock.isOpen()).thenReturn(true);
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

        when(firstPlayerMock.getName()).thenReturn(FIRST_PLAYER);
        when(secondPlayerMock.getName()).thenReturn(SECOND_PLAYER);

        when(firstPlayerMock.toJSON()).thenReturn(FIRST_PLAYER + JSON);
        when(secondPlayerMock.toJSON()).thenReturn(SECOND_PLAYER + JSON);
    }

    @SuppressWarnings("unchecked")
    private void resetPlayerListMock() {
        playersListMock = mock(PlayersList.class);

        List<Player> playersHolder = new ArrayList<>();
        playersHolder.add(firstPlayerMock);
        playersHolder.add(secondPlayerMock);

        when(playersListMock.getMoverName()).thenReturn(MOVER_NAME);
        when(playersListMock.getDealerName()).thenReturn(DEALER_NAME);

        when(playersListMock.generatePlayersJSON()).thenReturn(PLAYERS_JSON);

        when(playersListMock.generatePlayersJSON(FIRST_PLAYER)).thenReturn(FIRST_PLAYER + JSON + CARDS);
        when(playersListMock.generatePlayersJSON(SECOND_PLAYER)).thenReturn(SECOND_PLAYER + JSON + CARDS);
        when(playersListMock.generatePlayersJSON(FIRST_PLAYER, SECOND_PLAYER)).
                thenReturn(FIRST_PLAYER + JSON + CARDS + "," + SECOND_PLAYER + JSON + CARDS);

        when(playersListMock.getPlayerCardCombination(FIRST_PLAYER)).thenReturn(FIRST_PLAYER_CARD_COMBINATION);
        when(playersListMock.getPlayerCardCombination(SECOND_PLAYER)).thenReturn(SECOND_PLAYER_CARD_COMBINATION);

        when(playersListMock.iterator()).thenReturn(playersHolder.iterator(), playersHolder.iterator(), playersHolder.iterator());
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
        eventManager.addPlayer(secondPlayerConnectionMock, SECOND_PLAYER);

        eventManager.addEvent(eventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" +
                SECOND_PLAYER + JSON + CARDS
                + "," +
                "\"combination\":\"" + SECOND_PLAYER_CARD_COMBINATION
                + "\"," +
                "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(secondPlayerConnectionMock).sendMessage(message);
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
    public void shouldFirstViewerCanViewFirstPlayerCardsWhenFirstPlayerWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(firstPlayerMock));

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + "Game ended" + "\",\"players\":" +

                FIRST_PLAYER + JSON + CARDS

                + "," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(firstViewerConnectionMock).sendMessage(message);
    }

    @Test
    public void shouldFirstPlayerCanViewBothPlayersCardsWhenSecondPlayerWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(secondPlayerMock));

        eventManager.addPlayer(firstPlayerConnectionMock, FIRST_PLAYER);

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + "Game ended" + "\",\"players\":" +

                FIRST_PLAYER + JSON + CARDS + "," + SECOND_PLAYER + JSON + CARDS

                + "," +
                "\"combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
                + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(firstPlayerConnectionMock).sendMessage(message);
    }

    @Test
    public void shouldFirstViewerCanViewBothPlayersCardsWhenBothPlayersWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(firstPlayerMock, secondPlayerMock));

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + "Game ended" + "\",\"players\":" +

                FIRST_PLAYER + JSON + CARDS + "," + SECOND_PLAYER + JSON + CARDS

                + "," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(firstViewerConnectionMock).sendMessage(message);
    }

    @Test
    public void shouldMessageSendToFirstViewerOnceBecauseOfClosedConnectionShouldBeRemovedWhenAddEvent() throws Exception {
        when(firstViewerConnectionMock.isOpen()).thenReturn(false);

        eventManager.addEvent(eventMock);
        eventManager.addEvent(eventMock);

        verify(firstViewerConnectionMock).sendMessage(anyString());
    }

    @Test
    public void shouldDefaultMessageWithDeskCardsSendToFirstViewerMockEqualsDefaultMessage() throws Exception {
        when(dealerMock.getDeskCards()).thenReturn(new Card[]{firstCardMock, secondCardMock});

        eventManager.addEvent(eventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" + PLAYERS_JSON + "," +
                "\"gameStatus\":\"" + READY + "\",\"deskCards\":" +
                "[" + FIRST_CARD_JSON + "," + SECOND_CARD_JSON + "]" +
                ",\"deskPot\":0}";

        verify(firstViewerConnectionMock).sendMessage(message);
    }
}
