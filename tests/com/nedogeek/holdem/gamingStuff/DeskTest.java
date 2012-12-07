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
}
