package com.nedogeek.holdem.dealer;


import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gameEvents.Event;
import com.nedogeek.holdem.gameEvents.GameEndedEvent;
import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + EVENT_MOCK + ",\"players\":" + PLAYERS_JSON + "," +
            "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String SECOND_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":\"" + EVENT_MOCK + "\",\"players\":" +
            SECOND_PLAYER + JSON + CARDS +
            ",combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
            + "\"," + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

    private final String FIRST_PLAYER_WITH_CARDS_MESSAGE = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
            "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + EVENT_MOCK + ",\"players\":" +
            FIRST_PLAYER + JSON + CARDS
            + ",\"combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
            + "\"," +
            "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";


    private EventManager eventManager;

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

    private ConnectionsManager connectionsManagerMock;


    @Before
    public void setUp() throws Exception {
        resetDealerMock();

        resetPlayersMock();
        resetPlayerListMock();

        resetCards();

        eventMock = mock(Event.class);
        when(eventMock.toJSON()).thenReturn(EVENT_MOCK);

        gameEndedEventMock = mock(GameEndedEvent.class);
        when(gameEndedEventMock.toJSON()).thenReturn("Game ended");

        connectionsManagerMock = mock(ConnectionsManager.class);

        eventManager = new EventManager();
        eventManager.setDealer(dealerMock);
        eventManager.setPlayersList(playersListMock);
        eventManager.setConnectionsManager(connectionsManagerMock);
    }

    private void resetCards() {
        firstCardMock = mock(Card.class);
        secondCardMock = mock(Card.class);

        when(firstCardMock.toJSON()).thenReturn(FIRST_CARD_JSON);
        when(secondCardMock.toJSON()).thenReturn(SECOND_CARD_JSON);
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
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add(FIRST_PLAYER);
        playerNames.add(SECOND_PLAYER);
        when(playersListMock.getPlayerNames()).thenReturn(playerNames);
    }


    @Test
    public void shouldConnectionManagerMockSendMessageDefaultToViewersWhenAddGameEvent() throws Exception {
        eventManager.addEvent(eventMock);

        verify(connectionsManagerMock).sendMessageToViewers("DEFAULT", DEFAULT_MESSAGE);
    }

    @Test
    public void shouldConnectionManagerMockSendMessageDefaultToViewersTwiceWhenAddGameEvent2Times() throws Exception {
        eventManager.addEvent(eventMock);
        eventManager.addEvent(eventMock);

        verify(connectionsManagerMock, times(2)).sendMessageToViewers("DEFAULT", DEFAULT_MESSAGE);
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
    public void shouldInMessageFirstPlayerWithCardsSendToFirstPlayerConnection() throws Exception {
        eventManager.addEvent(eventMock);

        verify(connectionsManagerMock).sendPersonalMessage(FIRST_PLAYER, FIRST_PLAYER_WITH_CARDS_MESSAGE);
    }

    @Test
    public void shouldInMessageSecondPlayerWithCardsSendToSecondPlayerConnection() throws Exception {
        eventManager.addEvent(eventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + EVENT_MOCK + ",\"players\":" +
                SECOND_PLAYER + JSON + CARDS
                + "," +
                "\"combination\":\"" + SECOND_PLAYER_CARD_COMBINATION
                + "\"," +
                "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(connectionsManagerMock).sendPersonalMessage(SECOND_PLAYER, message);
    }

    @Test
    public void shouldNewerPlayersCardsSendToViewersWhenBothPlayerConnectionsAdded() throws Exception {
        eventManager.addEvent(eventMock);

        verify(connectionsManagerMock, never()).sendPersonalMessage(SECOND_PLAYER, FIRST_PLAYER_WITH_CARDS_MESSAGE);
        verify(connectionsManagerMock, never()).sendPersonalMessage(FIRST_PLAYER, SECOND_PLAYER_WITH_CARDS_MESSAGE);

        verify(connectionsManagerMock, never()).sendMessageToViewers("DEFAULT", FIRST_PLAYER_WITH_CARDS_MESSAGE);
        verify(connectionsManagerMock, never()).sendMessageToViewers("DEFAULT", SECOND_PLAYER_WITH_CARDS_MESSAGE);
    }


    @Test
    public void shouldFirstViewerCanViewFirstPlayerCardsWhenFirstPlayerWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(firstPlayerMock));

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + "Game ended" + ",\"players\":" +

                FIRST_PLAYER + JSON + CARDS

                + "," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(connectionsManagerMock).sendMessageToViewers("DEFAULT", message);
    }

    @Test
    public void shouldFirstPlayerCanViewBothPlayersCardsWhenSecondPlayerWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(secondPlayerMock));

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + "Game ended" + ",\"players\":" +

                FIRST_PLAYER + JSON + CARDS + "," + SECOND_PLAYER + JSON + CARDS

                + "," +
                "\"combination\":\"" + FIRST_PLAYER_CARD_COMBINATION
                + "\"," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(connectionsManagerMock).sendPersonalMessage(FIRST_PLAYER, message);
    }

    @Test
    public void shouldFirstViewerCanViewBothPlayersCardsWhenBothPlayersWon() throws Exception {
        when(gameEndedEventMock.getWinners()).thenReturn(Arrays.asList(firstPlayerMock, secondPlayerMock));

        eventManager.addEvent(gameEndedEventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + "Game ended" + ",\"players\":" +

                FIRST_PLAYER + JSON + CARDS + "," + SECOND_PLAYER + JSON + CARDS

                + "," + "\"gameStatus\":\"" + READY + "\",\"deskCards\":[],\"deskPot\":0}";

        verify(connectionsManagerMock).sendMessageToViewers("DEFAULT", message);
    }

    @Test
    public void shouldDefaultMessageWithDeskCardsSendToFirstViewerMockEqualsDefaultMessage() throws Exception {
        when(dealerMock.getDeskCards()).thenReturn(new Card[]{firstCardMock, secondCardMock});

        eventManager.addEvent(eventMock);

        String message = "{\"gameRound\":\"" + INITIAL + "\",\"dealer\":\"" + DEALER_NAME + "\"," +
                "\"mover\":\"" + MOVER_NAME + "\",\"event\":" + EVENT_MOCK + ",\"players\":" + PLAYERS_JSON + "," +
                "\"gameStatus\":\"" + READY + "\",\"deskCards\":" +
                "[" + FIRST_CARD_JSON + "," + SECOND_CARD_JSON + "]" +
                ",\"deskPot\":0}";

        verify(connectionsManagerMock).sendMessageToViewers("DEFAULT", message);
    }
}
