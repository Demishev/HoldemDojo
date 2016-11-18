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
 * Time: 12:09
 */

var login = undefined;
var server = "ws://" + window.location.host + "/ws";

gameData = {


    players: [
        {
            name: "First player",
            balance: 100,
            pot: 28,

            cards: [
                {cardValue: "K", cardSuit: "♠"},
                {cardValue: "10", cardSuit: "♥"}
            ],

            status: "Folded"
        },
        {
            name: "Second player",
            balance: 12004,
            pot: 148,

            cards: [
                {cardValue: "2", cardSuit: "♦"},
                {cardValue: "10", cardSuit: "♦"}
            ],

            status: "Bet 15"
        },
        {
            name: "Third player",
            balance: 23,
            pot: 0
        }
    ],


    deskCards: [
        {cardValue: "2", cardSuit: "♦"},
        {cardValue: "4", cardSuit: "♦"},
        {cardValue: "5", cardSuit: "♦"}
    ],

    deskPot: 580,
    mover: "First player",
    dealer: "Second player",

    event: ["Some event"],

    combination: "Some test card combination text."

};