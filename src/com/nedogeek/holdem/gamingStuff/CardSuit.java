package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 14:03
 */
public enum CardSuit {
    SPADES ("♠"),
    HEARTS ("♥"),
    DIAMONDS ("♦"),
    CLUBS ("♣");

    private final String stringValue;

    CardSuit(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
