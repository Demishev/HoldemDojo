package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:13
 */
public class Card {
    private final CardSuit cardSuit;
    private final CardValue cardValue;

    public Card(CardSuit cardSuit, CardValue cardValue) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardSuit=" + cardSuit +
                ", cardValue=" + cardValue +
                '}';
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
}
