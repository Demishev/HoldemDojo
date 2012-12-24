package com.nedogeek.holdem.gamingStuff;

import static org.junit.Assert.*;
import org.junit.Test;

import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.connections.Player;

public class DeskTest {
	private final Player FIRST_PLAYER = new Player("First player");
	private final Player SECOND_PLAYER = new Player("Second player");
	private Desk desk = new Desk();
	
	@Test
	public void addPalyer() throws Exception {
		Desk desk = new Desk();
		assertEquals(0, desk.getPlayersQuantity());
		desk.addPlayer(new Player(""));
		assertEquals(1, desk.getPlayersQuantity());
		desk.addPlayer(new Player(""));
		assertEquals(2, desk.getPlayersQuantity());
	}
	
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
	
	
	@Test
	public void shouldDeskStatusReadyWhenNewDeskAdd2PlayersAndSetReady() throws Exception {
		Desk desk = new Desk();
		desk.addPlayer(FIRST_PLAYER);
		desk.addPlayer(SECOND_PLAYER);
		desk.setReady();
		assertEquals(GameStatus.READY, desk.getGameStatus());
	}
}
