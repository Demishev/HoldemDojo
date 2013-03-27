package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gameEvents.MoveEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayerAction;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:26
 */
class MoveManager {
    private final Dealer dealer;
    private final PlayersList playersList;
    private final EventManager eventManager;

    MoveManager(Dealer dealer, PlayersList playersList) {
        this.dealer = dealer;
        this.playersList = playersList;
        eventManager = EventManager.getInstance();
    }

    void makeMove(Player mover) {
        delay();
        PlayerAction playerMove = mover.getMove();
        playersList.playerMoved(mover);
        eventManager.addEvent(new MoveEvent(mover));
        switch (playerMove.getActionType()) {
            case Fold:
                makeFold(mover);
                break;
            case Check:
                makeCheck(mover);
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

    private void delay() {
        try {
            Thread.sleep(GameSettings.getGameDelayValue());
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    private void makeCheck(Player player) {
        if (dealer.getCallValue() <= player.getBet()) {
            player.setStatus(PlayerStatus.Check);
        } else {
            player.setStatus(PlayerStatus.Fold);
        }
    }

    private void trySendToPot(Player player, int betValue) {
        final int playerAmount = player.getBalance();
        if (playerAmount <= betValue) {
            sendToPot(player, playerAmount);
            player.setStatus(PlayerStatus.AllIn);
        } else {
            sendToPot(player, betValue);
        }
    }

    private void sendToPot(Player player, int chips) {
        final int playerAmount = player.getBalance();
        final int playerBet = chips + player.getBet();

        player.setBet(playerBet);
        player.setBalance(playerAmount - chips);
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
        player.setStatus(PlayerStatus.Call);
        trySendToPot(player, dealer.getCallValue() - player.getBet());
    }

    private void makeAllIn(Player player) {
        trySendToPot(player, player.getBalance());
    }

    private void makeRise(Player player) {
        int riseValue = calculateRiseValue(player);
        player.setStatus(PlayerStatus.Rise);
        trySendToPot(player, riseValue);
    }

    private int calculateRiseValue(Player player) {
        int playerWantRise = player.getMove().getRiseAmount();
        int minimumRiseValue = dealer.getCallValue() + 2 * GameSettings.getSmallBlind();

        return (playerWantRise < minimumRiseValue) ? minimumRiseValue : playerWantRise;
    }

    public void makeSmallBlind(Player player) {
        player.setStatus(PlayerStatus.SmallBLind);

        trySendToPot(player, GameSettings.getSmallBlind());
        playersList.playerMoved(player);
    }

    public void makeBigBlind(Player player) {
        player.setStatus(PlayerStatus.BigBlind);

        trySendToPot(player, 2 * GameSettings.getSmallBlind());
        playersList.playerMoved(player);
    }
}
