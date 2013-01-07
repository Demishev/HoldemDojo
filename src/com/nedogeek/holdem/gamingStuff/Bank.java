package com.nedogeek.holdem.gamingStuff;

/**
 * User: Konstantin Demishev
 * Date: 25.12.12
 * Time: 0:02
 */
public class Bank {
    private int callValue;

    @Deprecated
    public void setPlayerAmount(int playerNumber, int coinsAtStart) {

    }

    @Deprecated
    public boolean riseNeeded(Player playerNumber) {
        return true;
    }

    @Deprecated
    public int getCallValue() {
        return 0;
    }

    @Deprecated
    public int getPlayerBet(int playerNumber) {
        return 0;
    }

    @Deprecated
    public int getPlayerBalance(int playerNumber) {
        return 0;
    }

    @Deprecated
    public void setCallValue(int callValue) {
        this.callValue = callValue;
    }

    @Deprecated
    public void addToPot(int betValue) {

    }

    @Deprecated
    public void setPlayerBet(int playerNumber, int bet) {

    }
}
