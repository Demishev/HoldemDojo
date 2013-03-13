package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;
import com.nedogeek.holdem.dealer.Dealer;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 12:07
 */
public class Player implements Comparable<Player> {
    private final String name;
    private final Dealer dealer;

    private PlayerStatus status;
    private int bet;
    private int balance = GameSettings.COINS_AT_START;
    private Card[] cards;
    
    private PlayerAction playerAction;

    public Player(String name, Dealer dealer) {
        this.name = name;
        this.dealer = dealer;

        status = PlayerStatus.NotMoved;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public PlayerAction getMove() {
        return playerAction;
    }
    
    public void setMove(PlayerAction playerAction) {
    	this.playerAction = playerAction;
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
        Map<String, Serializable> playerData = new HashMap<>();
        playerData.put("name", name);
        playerData.put("status", status);
        playerData.put("bet", bet);
        playerData.put("balance", balance);

        return JSONObject.fromMap(playerData).toString();
    }


    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public int compareTo(Player o) {
        if (this.getStatus() == PlayerStatus.Fold && o.getStatus() == PlayerStatus.Fold) {
            return 0;
        }

        if (o.getStatus() == PlayerStatus.Fold) {
            return 1;
        }

        if (this.getStatus() == PlayerStatus.Fold) {
            return -1;
        }

        return this.getCardCombination().compareTo(o.getCardCombination());
    }
    
    
}
