package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.combinations.PlayerCardCombination;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 12:07
 */
public class Player {
    private final String name;

    private PlayerStatus status;
    private PlayerStatus playerStatus;
    private int bet;
    private int balance;

    //TODO class is a stub
    public Player(String name) {
        this.name = name;
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
}
