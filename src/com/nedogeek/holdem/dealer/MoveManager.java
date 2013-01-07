package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
public class MoveManager {
    private final Bank bank;
    private final PlayersManager playersManager;

    MoveManager(Bank bank, PlayersManager playersManager) {
        this.bank = bank;
        this.playersManager = playersManager;
    }

    void makeMove(int playerNumber) {
        PlayerAction playerMove = getMover(playerNumber).getMove();
        switch (playerMove.getActionType()) {
            case Fold:
                makeFold(playerNumber);
                break;
            case Check:
                break;
            case Call:
                makeCall(playerNumber);
                break;
            case Rise:
                makeRise(playerNumber, playerMove.getRiseAmount());
                break;
            case AllIn:
                makeAllIn(playerNumber);
                break;
        }
        playersManager.setLastMovedPlayer(playerNumber);
    }

    void makeInitialBet(int playerNumber, int initialBet) {
        makeBet(playerNumber, initialBet);
    }

    private void makeBet(int playerNumber, int betValue) {
        final int playerAmount = bank.getPlayerBalance(playerNumber);
        final int previousBet = bank.getPlayerBet(playerNumber);
        final int playerBet = betValue + previousBet;

        bank.setPlayerBet(playerNumber, playerBet);
        bank.addToPot(betValue);
        bank.setPlayerAmount(playerNumber, playerAmount - betValue);
        checkCallValue(playerBet);
    }

    private void checkCallValue(int playerBet) {
        if (bank.getCallValue() < playerBet) {
            bank.setCallValue(playerBet);
        }
    }

    private void makeFold(int playerNumber) {
        Player mover = getMover(playerNumber);

        mover.setStatus(PlayerStatus.Fold);
    }

    private Player getMover(int playerNumber) {
        return playersManager.getPlayers().get(playerNumber);
    }

    private void makeCall(int playerNumber) {
        if (bank.getPlayerBalance(playerNumber) < bank.getCallValue() - bank.getPlayerBet(playerNumber)) {
            makeAllIn(playerNumber);
        } else {
            getMover(playerNumber).setStatus(PlayerStatus.Call);
            makeBet(playerNumber, bank.getCallValue() - bank.getPlayerBet(playerNumber));
        }
    }

    private void makeAllIn(int playerNumber) {
        getMover(playerNumber).setStatus(PlayerStatus.AllIn);
        makeBet(playerNumber, bank.getPlayerBalance(playerNumber));
    }

    private void makeRise(int playerNumber, int riseValue) {
        if (isAllInMove(playerNumber, riseValue)) {
            makeAllIn(playerNumber);
        } else {
            Player mover = getMover(playerNumber);
            mover.setStatus(PlayerStatus.Rise);
            if (bank.getPlayerBet(playerNumber) + riseValue >= minimumRiseValue()) {
                makeBet(playerNumber, riseValue);
            } else {
                makeBet(playerNumber, minimumRiseValue() - bank.getPlayerBet(playerNumber));
            }
        }
    }

    private boolean isAllInMove(int playerNumber, int bet) {
        return bank.getPlayerBalance(playerNumber) <= bet;
    }

    private int minimumRiseValue() {
        return bank.getCallValue() + 2 * GameSettings.SMALL_BLIND_AT_START;
    }
}
