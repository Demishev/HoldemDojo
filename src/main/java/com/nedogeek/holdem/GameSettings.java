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
 * Date: 05.10.12
 * Time: 22:46
 */
public class GameSettings {
    private static int coinsAtStart = 1000;
    private static int smallBlind = 10;
    private static long gameDelayValue = 1000;
    private static long endGameDelayValue = 3000;
    private static int maximumPlayers = 12;

    public static int getCoinsAtStart() {
        return coinsAtStart;
    }

    public static int getSmallBlind() {
        return smallBlind;
    }

    public static long getGameDelayValue() {
        return gameDelayValue;
    }

    public static long getEndGameDelayValue() {
        return endGameDelayValue;
    }

    public static void setCoinsAtStart(int coinsAtStart) {
        if (coinsAtStart > 0) {
            GameSettings.coinsAtStart = coinsAtStart;
        }
    }

    public static void setSmallBlind(int smallBlind) {
        if (smallBlind > 0) {
            GameSettings.smallBlind = smallBlind;
        }
    }

    public static void setGameDelayValue(long gameDelayValue) {
        if (gameDelayValue >= 0) {
            GameSettings.gameDelayValue = gameDelayValue;
        }
    }

    public static void setEndGameDelayValue(long endGameDelayValue) {
        if (endGameDelayValue >= 0) {
            GameSettings.endGameDelayValue = endGameDelayValue;
        }
    }

    public static int getMaximumPlayers() {
        return maximumPlayers;
    }
}
