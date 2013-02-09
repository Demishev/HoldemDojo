package com.nedogeek.holdem.gamingStuff;

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
    private int balance;
    private PlayersList playersList;

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
        return null;
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

    public void registerList(PlayersList playersList) {
        this.playersList = playersList;
    }

    public String getName() {
        return name;
    }

    public void makeBet(int bet) {
        int chips = getChipsFromBalance(bet);
        dealer.sendToPot(chips);
        this.bet = chips;

        playersList.playerMoved(this);
    }

    private int getChipsFromBalance(int bet) {
        if (bet < balance) {
            balance = balance - bet;
            return bet;
        } else {
            int chips = balance;
            balance = 0;
            return chips;
        }
    }

	public boolean isActiveNotRisePlayer() {
		//TODO Implement checking is player needed rise.
		return (getStatus() != PlayerStatus.Fold);
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

	
	
	
}
