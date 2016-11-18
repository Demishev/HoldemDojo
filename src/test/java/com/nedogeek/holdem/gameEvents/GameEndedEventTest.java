package com.nedogeek.holdem.gameEvents;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.gamingStuff.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Konstantin Demishev
 * Date: 22.03.13
 * Time: 22:40
 */
public class GameEndedEventTest {
    private final String FIRST_PLAYER_CARD_COMBINATION = "First player card combination";
    private final String FIRST_PLAYER_NAME = "First player";

    private final String SECOND_PLAYER_CARD_COMBINATION = "Second player card combination";
    private final String SECOND_PLAYER_NAME = "Second player";

    private final String GAME_ENDED = "Game ended";


    private Player firstPlayerMock;
    private Player secondPlayerMock;

    private Map<Player, Integer> winners;

    @Before
    public void setUp() throws Exception {
        winners = new LinkedHashMap<>();

        resetPlayers();
    }

    private void resetPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        when(firstPlayerMock.getName()).thenReturn(FIRST_PLAYER_NAME);
        when(secondPlayerMock.getName()).thenReturn(SECOND_PLAYER_NAME);

        PlayerCardCombination firstPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        PlayerCardCombination secondPlayerCardCombinationMock = mock(PlayerCardCombination.class);
        when(firstPlayerMock.getCardCombination()).thenReturn(firstPlayerCardCombinationMock);
        when(firstPlayerCardCombinationMock.toString()).thenReturn(FIRST_PLAYER_CARD_COMBINATION);
        when(secondPlayerMock.getCardCombination()).thenReturn(secondPlayerCardCombinationMock);
        when(secondPlayerCardCombinationMock.toString()).thenReturn(SECOND_PLAYER_CARD_COMBINATION);
    }

    @Test
    public void shouldGameEndedEventNotNullWhenNewGameEndedEvent() throws Exception {
        assertNotNull(new GameEndedEvent(winners));
    }

    @Test
    public void shouldMatchDefaultMessageWhenEndGameEventWithoutWinners() throws Exception {
        assertEquals(GAME_ENDED, new GameEndedEvent(winners).toString());
    }

    @Test
    public void shouldMatchMessageWhenGameEndedEventWithFirstPlayerWon() throws Exception {
        winners.put(firstPlayerMock, 0);

        assertEquals(GAME_ENDED + "\n" + FIRST_PLAYER_NAME + " win $0 with " + FIRST_PLAYER_CARD_COMBINATION,
                new GameEndedEvent(winners).toString());
    }

    @Test
    public void shouldMatchMessageWhenGameEndedEventWithFirstAndSecondPlayersWon() throws Exception {
        winners.put(firstPlayerMock, 0);
        winners.put(secondPlayerMock, 250);

        assertEquals(GAME_ENDED + "\n" + FIRST_PLAYER_NAME + " win $0 with " + FIRST_PLAYER_CARD_COMBINATION +
                "\n" + SECOND_PLAYER_NAME + " win $250 with " + SECOND_PLAYER_CARD_COMBINATION,
                new GameEndedEvent(winners).toString());
    }
}
