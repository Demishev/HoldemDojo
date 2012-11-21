package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
public class MoveManager {
    private final Desk desk;

    public MoveManager(Desk desk) {
        this.desk = desk;
    }

    void makeBet(int playerNumber, int betValue) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        final int previousBet = desk.getPlayerBet(playerNumber);
        desk.setPlayerBet(playerNumber, betValue + previousBet);
        desk.addToPot(betValue);
        desk.setPlayerAmount(playerNumber, playerAmount - betValue);
        desk.setCallValue(betValue);
    }

    void makeFold(int playerNumber) {
        desk.setPlayerStatus(playerNumber, PlayerStatus.Fold);
    }

    void makeCall(int playerNumber) {
        desk.setPlayerStatus(playerNumber,PlayerStatus.Call);
        makeBet(playerNumber, desk.getCallValue() - desk.getPlayerBet(playerNumber));
    }

    void makeAllIn(int playerNumber) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        desk.setPlayerStatus(playerNumber, PlayerStatus.AllIn);
        makeBet(playerNumber, playerAmount);
    }

    void makeRise(int playerNumber, int riseValue) {
        if (isAllInMove(playerNumber, riseValue)) {
            makeAllIn(playerNumber);
        } else {
            desk.setPlayerStatus(playerNumber, PlayerStatus.Rise);
            if (desk.getPlayerBet(playerNumber) + riseValue >= minimumRiseValue()) {
                makeBet(playerNumber, riseValue);
            } else {
                makeBet(playerNumber, minimumRiseValue() - desk.getPlayerBet(playerNumber));
            }
        }
    }


    private boolean isAllInMove(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        return playerAmount <= bet;
    }

    private int minimumRiseValue() {
        return desk.getCallValue() + 2 * GameSettings.SMALL_BLIND_AT_START;
    }

}
