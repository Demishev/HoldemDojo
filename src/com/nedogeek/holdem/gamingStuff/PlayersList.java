package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;

import java.util.ArrayList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
public class PlayersList extends ArrayList<Player> {

    private int dealerNumber;
    private int lastMovedPlayer;

    public PlayersList() {
       dealerNumber = 0;
    }

    @Override
    public boolean add(Player player) {
        if (!contains(player)) {
            player.registerList(this);
            return super.add(player);
        }
        return false;
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

    public boolean hasAvailableMovers() {
        return moreThanOnePlayerDoNotFoldsOrLost() && getMoverNumber() != -1;
    }

    public boolean moreThanOnePlayerDoNotFoldsOrLost() {
        int availableMoverStatusQuantity = 0;
        for (Player player : this) {
            final PlayerStatus playerStatus = player.getStatus();
            if (playerStatus != PlayerStatus.Fold && playerStatus != PlayerStatus.Lost) {
                availableMoverStatusQuantity++;
            }
        }
        return availableMoverStatusQuantity > 1;
    }

    public Player getMover() {
        return get(getMoverNumber());
    }

    private int getMoverNumber() {
        if (lastMovedPlayer == -1) {
            return nextPlayer(dealerNumber);
        }
        int nextPlayerNumber = nextPlayer(lastMovedPlayer);

        while (nextPlayerNumber != lastMovedPlayer) {
            final Player currentPlayer = get(nextPlayerNumber);
            if (currentPlayer.getStatus() == PlayerStatus.NotMoved ||
                    currentPlayer.isActiveNotRisePlayer()) {
                return nextPlayerNumber;
            }
            nextPlayerNumber = nextPlayer(nextPlayerNumber);
        }
        return -1;
    }    

    public void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
        System.out.println("Dealer number is: " + dealerNumber);
    }

    public Player smallBlindPlayer() {
        return get(nextPlayer(dealerNumber));
    }

    public Player bigBlindPlayer() {
        return get(nextPlayer(nextPlayer(dealerNumber)));
    }
}