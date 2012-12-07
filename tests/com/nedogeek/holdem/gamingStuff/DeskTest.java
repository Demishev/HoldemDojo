package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * User: Konstantin Demishev
 * Date: 07.12.12
 * Time: 5:22
 */
public class DeskTest {
    Player FIRST_PLAYER_MOCK;
    Player SECOND_PLAYER_MOCK;
    Player THIRD_PLAYER_MOCK;

    private Desk desk;

    @Before
    public void setUp() throws Exception {
        desk = new Desk();
        resetPlayerMocks();
    }

    private void resetPlayerMocks() {
        FIRST_PLAYER_MOCK = mock(Player.class);
        SECOND_PLAYER_MOCK = mock(Player.class);
        THIRD_PLAYER_MOCK = mock(Player.class);
    }

    @Test
    public void shouldGameStatusNotReadyWhenDeskCreated() throws Exception {
        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void should1PlayerWhenAddPlayerToNewDesk() throws Exception {
        desk.addPlayer(new Player("Some player"));

        assertEquals(1, desk.getPlayersQuantity());
    }

    @Test
    public void should0PlayersWhenNewDesk() throws Exception {
        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldNotPlayerAddedWhenGameStatusSTARTED() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(FIRST_PLAYER_MOCK);

        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldGameStatusStartedWhenDeskSetGameStarted() throws Exception {
        desk.setGameStarted();

        assertEquals(GameStatus.STARTED, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusReadyWhenDeskSetReady() throws Exception {
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.addPlayer(SECOND_PLAYER_MOCK);
        desk.setReady();

        assertEquals(GameStatus.READY, desk.getGameStatus());
    }

    @Test
    public void shouldPlayerAddedToDeskWhenAddedWhileStartedAndCycleEnded() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.setGameCycleEnded();

        assertEquals(1, desk.getPlayersQuantity());
    }

    @Test
    public void shouldPlayerAddedToDeskWhenAddedWhileStartedAndCycleEnded2Times() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.setGameCycleEnded();
        desk.setGameCycleEnded();

        assertEquals(1, desk.getPlayersQuantity());
    }

    @Test
    public void shouldGameCycleEndedReadyWhenDeskSetGameCycleEnded() throws Exception {
        desk.setGameCycleEnded();

        assertEquals(GameStatus.CYCLE_ENDED, desk.getGameStatus());
    }

    @Test
    public void shouldPlayerQuantity0WhenPlayerRemoved() throws Exception {
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.removePlayer(FIRST_PLAYER_MOCK);

        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldPlayerNotAddedToDeskWhenAddedWhileStartedPlayerRemovedAndCycleEnded() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.removePlayer(FIRST_PLAYER_MOCK);
        desk.setGameCycleEnded();

        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldNotGameReadyWhen0PlayersOnDesk() throws Exception {
        desk.setReady();

        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void shouldNotGameReadyWhen1PlayerOnDesk() throws Exception {
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.setReady();

        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusNotReadyWhenSecondPlayerRemoved() throws Exception {
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.addPlayer(SECOND_PLAYER_MOCK);

        desk.setReady();
        desk.removePlayer(FIRST_PLAYER_MOCK);

        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusReadyWhenThirdPlayerRemoved() throws Exception {
        desk.addPlayer(FIRST_PLAYER_MOCK);
        desk.addPlayer(SECOND_PLAYER_MOCK);
        desk.addPlayer(THIRD_PLAYER_MOCK);

        desk.setReady();
        desk.removePlayer(FIRST_PLAYER_MOCK);

        assertEquals(GameStatus.READY, desk.getGameStatus());
    }

    @Test
    public void shouldDealerPlayer1WhenSetDealerPlayer1() throws Exception {
        desk.setDealerPlayer(1);

        assertEquals(1, desk.getDealerPlayerNumber());
    }
}
