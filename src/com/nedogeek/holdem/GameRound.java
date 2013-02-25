package com.nedogeek.holdem;

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
