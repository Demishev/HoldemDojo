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
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Demishev
 * Date: 10.02.13
 * Time: 0:55
 */
public class MoveEvent extends Event {
    public MoveEvent(Player player) {
        super(generateMessage(player, player.getMove().getActionType(), player.getMove().getRiseAmount()));
    }

    private static String generateMessage(Player player, PlayerAction.ActionType moveType, int bet) {
        switch (moveType) {
            case AllIn:
                return player.getName() + " goes All in.";
            case Call:
                return player.getName() + " makes call.";
            case Fold:
                return player.getName() + " folds.";
            case Check:
                return player.getName() + " checks.";
            case Rise:
                return player.getName() + " makes bet " + bet;
            default:
                return "NULL message.";
        }
    }
}
