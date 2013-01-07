package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeskTest {
	private final Player FIRST_PLAYER = new Player("First player");
	private final Player SECOND_PLAYER = new Player("Second player");
	private Desk desk = new Desk();

	
	@Test
	public void shouldNotReadyWhenNoPlayers() throws Exception {
		desk.setReady();
		assertEquals("Could not be ready without players",
				GameStatus.NOT_READY, desk.getGameStatus());
	}
	
	@Test
	public void shouldNotReadyWhenOnlyOnePlayerAndSetReady() throws Exception {
		desk.addPlayer(FIRST_PLAYER);
		desk.setReady();
		assertEquals("Could not be ready with one player",
				GameStatus.NOT_READY, desk.getGameStatus());
	}
	
	@Test
	public void shouldNotReadyWhenNewDesk() throws Exception {
		assertEquals(GameStatus.NOT_READY, desk.getGameStatus());	
	}
	
}
