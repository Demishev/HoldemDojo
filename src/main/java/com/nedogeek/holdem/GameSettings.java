package com.nedogeek.holdem;

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
