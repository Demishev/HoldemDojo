package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 07.12.12
 * Time: 5:22
 */
public class DeskTest {
    final Player SOME_PLAYER = new Player("Some player");
    final Player SOME_OTHER_PLAYER = new Player("Some other player");
    final Player THIRD_PLAYER = new Player("Some third player");

    private Desk desk;

    @Before
    public void setUp() throws Exception {
        desk = new Desk();

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
        desk.addPlayer(SOME_PLAYER);

        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldGameStatusStartedWhenDeskSetGameStarted() throws Exception {
        desk.setGameStarted();

        assertEquals(GameStatus.STARTED, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusReadyWhenDeskSetReady() throws Exception {
        desk.addPlayer(SOME_PLAYER);
        desk.addPlayer(SOME_OTHER_PLAYER);
        desk.setReady();

        assertEquals(GameStatus.READY, desk.getGameStatus());
    }

    @Test
    public void shouldPlayerAddedToDeskWhenAddedWhileStartedAndCycleEnded() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(SOME_PLAYER);
        desk.setGameCycleEnded();

        assertEquals(1, desk.getPlayersQuantity());
    }

    @Test
    public void shouldPlayerAddedToDeskWhenAddedWhileStartedAndCycleEnded2Times() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(SOME_PLAYER);
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
        desk.addPlayer(SOME_PLAYER);
        desk.removePlayer(SOME_PLAYER);

        assertEquals(0, desk.getPlayersQuantity());
    }

    @Test
    public void shouldPlayerNotAddedToDeskWhenAddedWhileStartedPlayerRemovedAndCycleEnded() throws Exception {
        desk.setGameStarted();
        desk.addPlayer(SOME_PLAYER);
        desk.removePlayer(SOME_PLAYER);
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
        desk.addPlayer(SOME_PLAYER);
        desk.setReady();

        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusNotReadyWhenSecondPlayerRemoved() throws Exception {
        desk.addPlayer(SOME_PLAYER);
        desk.addPlayer(SOME_OTHER_PLAYER);

        desk.setReady();
        desk.removePlayer(SOME_PLAYER);

        assertEquals(GameStatus.NOT_READY, desk.getGameStatus());
    }

    @Test
    public void shouldGameStatusReadyWhenThirdPlayerRemoved() throws Exception {
        desk.addPlayer(SOME_PLAYER);
        desk.addPlayer(SOME_OTHER_PLAYER);
        desk.addPlayer(THIRD_PLAYER);

        desk.setReady();
        desk.removePlayer(SOME_PLAYER);

        assertEquals(GameStatus.READY, desk.getGameStatus());
    }

    @Test
    public void shouldDealerPlayer1WhenSetDealerPlayer1() throws Exception {
        desk.setDealerPlayer(1);

        assertEquals(1, desk.getDealerPlayerNumber());
    }
}
