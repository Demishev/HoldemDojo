package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
class PlayersList extends ArrayList<Player> {
    private final Dealer dealer;

    private int dealerNumber;

    private int lastMovedPlayer;

    PlayersList(Dealer dealer) {
        this.dealer = dealer;
        dealerNumber = -1;
    }

    public void setLastMovedPlayer(int lastMovedPlayer) { //TODO remove me
        this.lastMovedPlayer = lastMovedPlayer;
    }

    public void setLastMovedPlayer(Player lastMovedPlayer) {
        //TODO code me
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

    int getMoverNumber() {      //TODO replace with getMover
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

    public void removePlayer(Player player) {
        remove(player);      //TODO remove
    }

    public void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
    }

//    @Deprecated
//    public List<Player> getPlayers() {
//        return this;
//    }

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