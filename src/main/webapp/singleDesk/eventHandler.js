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
/**
 * User: Konstantin Demishev
 * Date: 20.03.13
 * Time: 21:13
 */

eventTypes = {
    PLAYER_ADDED: "PLAYER_ADDED",
    CARDS_GIVEN: "CARDS_GIVEN",
    ROUND_CHANGED: "ROUND_CHANGED",
    MOVE_EVENT: "MOVE_EVENT",
    NEW_GAME_STARTED: "NEW_GAME_STARTED",
    PLAYER_CONNECTED: "PLAYER_CONNECTED",
    MOVER_IS: "MOVER_IS",
    PLAYER_WIN: "PLAYER_WIN",
    PLAYER_DISCONNECTED: "PLAYER_DISCONNECTED"
};


var parseEvent = function (event) {
    eventType = event.EventType;

    if (eventType == eventType.PLAYER_ADDED) {
        drawGameData();
    } else if (eventType == eventTypes.CARDS_GIVEN) {

    } else if (eventType == eventTypes.ROUND_CHANGED) {

    } else if (eventType == eventTypes.MOVE_EVENT) {

    } else if (eventType == eventTypes.NEW_GAME_STARTED) {

    } else if (eventType == eventTypes.PLAYER_CONNECTED) {

    } else if (eventType == eventTypes.MOVER_IS) {

    } else if (eventType == eventTypes.PLAYER_WIN) {

    } else if (eventType == eventTypes.PLAYER_DISCONNECTED) {
        drawGameData();
    }
};


