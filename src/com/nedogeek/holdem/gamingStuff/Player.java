package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.dealer.Dealer;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 12:07
 */
public class Player {
    private final String name;
    private final Dealer dealer;

    private PlayerStatus status;
    private int bet;
    private int balance = GameSettings.COINS_AT_START;
    private Card[] cards;

    public Player(String name, Dealer dealer) {
        this.name = name;
        this.dealer = dealer;

        status = PlayerStatus.NotMoved;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public PlayerAction getMove() {
        return null;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public PlayerCardCombination getCardCombination() {
        Card[] deskCards = dealer.getDeskCards();

        Card[] allCards = new Card[cards.length + deskCards.length];
        System.arraycopy(cards, 0, allCards, 0, cards.length);
        System.arraycopy(deskCards, 0, allCards, cards.length, deskCards.length);

        return new PlayerCardCombination(allCards);
    }

    public int getBet() {
        return bet;
    }

    public int getBalance() {
        return balance;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public boolean isActiveNotRisePlayer() {
        return isActiveStatus() || (!isPassiveStatus() && bet < dealer.getCallValue());
    }

    private boolean isActiveStatus() {
        return status == PlayerStatus.NotMoved ||
                status == PlayerStatus.SmallBLind || status == PlayerStatus.BigBlind;
    }

    private boolean isPassiveStatus() {
        return status == PlayerStatus.Fold || status == PlayerStatus.AllIn;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + ", status=" + status + ", bet=" + bet
                + ", balance=" + balance + "]";
    }

    public String toJSON() {
        return "[\"name\":\"" + name + "\", \"status\":\"" + status + "\",\"bet\":" + bet
                + ", \"balance\":" + balance + "]";
    }


    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
