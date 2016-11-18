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


import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 22.03.13
 * Time: 12:01
 */
public class GameEndedEvent extends Event {
    private final Map<Player, Integer> winners;

    public GameEndedEvent(Map<Player, Integer> winners) {
        super(generateEventMessage(winners));
        this.winners = winners;

    }

    private static String generateEventMessage(Map<Player, Integer> winners) {
        String message = "Game ended";

        for (Player winner : winners.keySet()) {
            message += '\n';
            message += winner.getName();
            message += " win $";
            message += winners.get(winner);
            message += " with ";
            message += winner.getCardCombination().toString();
        }

        return message;
    }

    public List<Player> getWinners() {
        return new ArrayList<>(winners.keySet());
    }
}
