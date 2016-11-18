package com.nedogeek.holdem;

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
 * Date: 21.11.12
 * Time: 23:07
 */
public enum GameRound {
    INITIAL,
    BLIND,
    THREE_CARDS,
    FOUR_CARDS,
    FIVE_CARDS,
    FINAL;

    public static GameRound next(GameRound gameRound) {
        return GameRound.values()[gameRound.ordinal() + 1];
    }
}
