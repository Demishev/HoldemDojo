package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 13:58
 */
public enum CardValue {
    TWO ("2", "2"),
    THREE ("3", "3"),
    FOUR ("4", "4"),
    FIVE ("5", "5"),
    SIX ("6", "6"),
    SEVEN ("7", "7"),
    EIGHT ("8", "8"),
    NINE ("9", "9"),
    TEN ("10", "10"),
    JACK ("J", "Jack"),
    QUEEN ("Q", "Queen"),
    KING ("K", "King"),
    ACE ("A", "Ace");

    private final String stringValue;
    private final String fullName;

    CardValue(String stringValue, String fullName) {
        this.stringValue = stringValue;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public String getFullName() {
        return fullName;
    }



}
