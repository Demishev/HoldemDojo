package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
public class PlayersList extends ArrayList<Player> {
    private final Dealer dealer;

    private int dealerNumber;
    private int lastMovedPlayer;

    PlayersList(Dealer dealer) {
        this.dealer = dealer;
        dealerNumber = -1;
    }

    @Override
    public boolean add(Player player) {
        player.registerList(this);

        return super.add(player);
    }

    public void playerMoved(Player player) {
        lastMovedPlayer = indexOf(player);
    }

    private int nextPlayer(int playerNumber) {
        if (playerNumber == size() - 1) {
            return 0;
        } else {
            return playerNumber + 1;
        }
    }

    boolean hasAvailableMovers() {
        return moreThanOnePlayerDoNotFoldsOrLost() && getMoverNumber() != -1;
    }

    private boolean moreThanOnePlayerDoNotFoldsOrLost() {
        int availableMoverStatusQuantity = 0;
        for (Player player : this) {
            final PlayerStatus playerStatus = player.getStatus();
            if (playerStatus != PlayerStatus.Fold && playerStatus != PlayerStatus.Lost) {
                availableMoverStatusQuantity++;
            }
        }
        return availableMoverStatusQuantity > 1;
    }

    Player getMover() {
        return get(getMoverNumber()); //TODO test it
    }

    private int getMoverNumber() {
        if (lastMovedPlayer == -1) {
            return nextPlayer(dealerNumber);
        }
        int nextPlayerNumber = nextPlayer(lastMovedPlayer);

        while (nextPlayerNumber != lastMovedPlayer) {
            final Player currentPlayer = get(nextPlayerNumber);
            if (currentPlayer.getStatus() == PlayerStatus.NotMoved ||
                    isActiveNotRisePlayer(currentPlayer)) {
                return nextPlayerNumber;
            }
            nextPlayerNumber = nextPlayer(nextPlayerNumber);
        }
        return -1;
    }

    private boolean isActiveNotRisePlayer(Player player) {
        return (player.getStatus() != PlayerStatus.Fold &&
                dealer.riseNeeded(player));
    }

    void addPlayer(Player player) {
        if (!contains(player))
            add(player);
    }

    public void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
    }

    public Player getDealer() {
        return (dealerNumber == -1) ? null : get(dealerNumber);
    }

    public Player smallBlindPlayer() {
        return null;   //TODO code me!
    }

    public Player bigBlindPlayer() {
        return null;  //TODO code me!
    }
}