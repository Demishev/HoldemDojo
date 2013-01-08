package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
public class MoveManager {
    private final Dealer dealer;
    private final PlayersManager playersManager;

    MoveManager(Dealer dealer, PlayersManager playersManager) {
        this.dealer = dealer;
        this.playersManager = playersManager;
    }

    void makeMove(Player mover) {
        PlayerAction playerMove = mover.getMove();
        switch (playerMove.getActionType()) {
            case Fold:
                makeFold(mover);
                break;
            case Check:
                break;
            case Call:
                makeCall(mover);
                break;
            case Rise:
                makeRise(mover);
                break;
            case AllIn:
                makeAllIn(mover);
                break;
        }
        playersManager.setLastMovedPlayer(mover); //TODO test me
    }

    void makeInitialBet(Player player, int initialBet) {
        makeBet(player, initialBet);
    }

    private void makeBet(Player player, int betValue) {
        final int playerAmount = player.getBalance();
        final int previousBet = player.getBet();
        final int playerBet = betValue + previousBet;

        player.setBet(playerBet);
        dealer.addToPot(betValue);
        player.setBalance(playerAmount - betValue);
        checkCallValue(playerBet);
    }

    private void checkCallValue(int playerBet) {
        if (dealer.getCallValue() < playerBet) {
            dealer.setCallValue(playerBet);
        }
    }

    private void makeFold(Player player) {
        player.setStatus(PlayerStatus.Fold);
    }

    private void makeCall(Player player) {
        if (player.getBalance() < dealer.getCallValue() - player.getBet()) {
            makeAllIn(player);
        } else {
            player.setStatus(PlayerStatus.Call);
            makeBet(player, dealer.getCallValue() - player.getBet());
        }
    }

    private void makeAllIn(Player player) {
        player.setStatus(PlayerStatus.AllIn);
        makeBet(player, player.getBalance());
    }

    private void makeRise(Player player) {
        int riseValue = player.getMove().getRiseAmount();
        if (isAllInMove(player, riseValue)) {
            makeAllIn(player);
        } else {
            player.setStatus(PlayerStatus.Rise);
            if (riseValue >= minimumRiseValue()) {
                makeBet(player, riseValue);
            } else {
                makeBet(player, minimumRiseValue());
            }
        }
    }

    private boolean isAllInMove(Player player, int bet) {
        return player.getBalance() <= bet;
    }

    private int minimumRiseValue() {
        return dealer.getCallValue() + 2 * GameSettings.SMALL_BLIND_AT_START;
    }
}
