package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
public class MoveManager {
    private final Dealer dealer;
    private final PlayersList playersManager;

    MoveManager(Dealer dealer, PlayersList playersManager) {
        this.dealer = dealer;
        this.playersManager = playersManager;
    }

    void makeMove(Player mover) {
        PlayerAction playerMove = mover.getMove();
        playersManager.playerMoved(mover);
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
            default:
                makeFold(mover);

        }
    }

    private void makeInitialBet(Player player, int initialBet) {
        if (player.getBalance() > initialBet) {
            makeBet(player, initialBet);
        } else {
            makeAllIn(player);
        }
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
        System.out.println(player + " making fold.");
        player.setStatus(PlayerStatus.Fold);
    }

    private void makeCall(Player player) {
        System.out.println(player + " making call.");
        if (player.getBalance() < dealer.getCallValue() - player.getBet()) {
            makeAllIn(player);
        } else {
            player.setStatus(PlayerStatus.Call);
            makeBet(player, dealer.getCallValue() - player.getBet());
        }
    }

    private void makeAllIn(Player player) {
        System.out.println(player + " making AllIn.");
        player.setStatus(PlayerStatus.AllIn);
        makeBet(player, player.getBalance());
    }

    private void makeRise(Player player) {
        System.out.println(player + " making rise");
        int riseValue = calculateRiseValue(player);
        if (isAllInMove(player, riseValue)) {
            makeAllIn(player);
        } else {
            player.setStatus(PlayerStatus.Rise);
            makeBet(player, riseValue);
        }
    }

    private int calculateRiseValue(Player player) {
        int playerWantRise = player.getMove().getRiseAmount();
        return (playerWantRise < minimumRiseValue()) ? minimumRiseValue() : playerWantRise;
    }

    private boolean isAllInMove(Player player, int bet) {
        return player.getBalance() <= bet;
    }

    private int minimumRiseValue() {
        return dealer.getCallValue() + 2 * GameSettings.SMALL_BLIND;
    }

    public void makeSmallBlind(Player player) {
        player.setStatus(PlayerStatus.SmallBLind);

        makeInitialBet(player, GameSettings.SMALL_BLIND);
    }

    public void makeBigBlind(Player player) {
        player.setStatus(PlayerStatus.BigBlind);

        makeInitialBet(player, 2 * GameSettings.SMALL_BLIND);
    }
}
