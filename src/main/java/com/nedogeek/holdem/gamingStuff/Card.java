package com.nedogeek.holdem.gamingStuff;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:13
 */
public class Card implements Comparable<Card> {
    private final CardSuit cardSuit;
    private final CardValue cardValue;

    public Card(CardSuit cardSuit, CardValue cardValue) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
    }

    CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public String getCardValueName() {
        return cardValue.getFullName();
    }

    @Override
    public String toString() {
        return "" + cardValue + cardSuit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return cardSuit == card.cardSuit && cardValue == card.cardValue;

    }

    @Override
    public int hashCode() {
        int result = cardSuit != null ? cardSuit.hashCode() : 0;
        result = 31 * result + (cardValue != null ? cardValue.hashCode() : 0);
        return result;
    }


    public int compareTo(Card o) {
        return cardValue.compareTo(o.getCardValue());
    }

    public boolean sameSuit(Card otherCard) {
        return cardSuit == otherCard.getCardSuit();
    }

    public boolean isNear(Card otherCard) {
        int cardValueNumber = cardValue.ordinal();
        int otherCardValue = otherCard.cardValue.ordinal();
        return Math.abs(cardValueNumber - otherCardValue) == 1;
    }

    public boolean sameValue(Card otherCard) {
        return cardValue == otherCard.getCardValue();
    }

    public String toJSON() {
        Map<String, String> cardData = new HashMap<>();

        cardData.put("cardSuit", cardSuit.toString());
        cardData.put("cardValue", cardValue.toString());

        return JSONObject.fromMap(cardData).toString();
    }
}
