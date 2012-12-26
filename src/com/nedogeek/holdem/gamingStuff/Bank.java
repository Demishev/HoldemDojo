package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 25.12.12
 * Time: 0:02
 */
public class Bank {
    private int callValue;

    public void setPlayerAmount(int playerNumber, int coinsAtStart) {

    }

    public boolean riseNeeded(Player playerNumber) {
        return true;
    }

    public int getCallValue() {
        return 0;
    }

    public int getPlayerBet(int playerNumber) {
        return 0;
    }

    public int getPlayerBalance(int playerNumber) {
        return 0;
    }

    public void setCallValue(int callValue) {
        this.callValue = callValue;
    }

    public void addToPot(int betValue) {

    }

    public void setPlayerBet(int playerNumber, int bet) {

    }
}
