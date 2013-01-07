package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Bank;
import com.nedogeek.holdem.gamingStuff.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
class PlayersManager {
    private final Bank bank;

    private int dealerNumber;

    List<Player> players = new ArrayList<Player>();
    private int lastMovedPlayer;

    PlayersManager(Bank bank) {
        this.bank = bank;
        dealerNumber = -1;
    }


    public void setLastMovedPlayer(int lastMovedPlayer) { //TODO remove me
        this.lastMovedPlayer = lastMovedPlayer;
    }

    public void setLastMovedPlayer(Player lastMovedPlayer) {
        //TODO code me
    }

    private int nextPlayer(int playerNumber) {
        if (playerNumber == players.size() - 1) {
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
        for (Player player : players) {
            final PlayerStatus playerStatus = player.getStatus();
            if (playerStatus != PlayerStatus.Fold && playerStatus != PlayerStatus.Lost) {
                availableMoverStatusQuantity++;
            }
        }
        return availableMoverStatusQuantity > 1;
    }

    Player getMover() {
        return players.get(getMoverNumber()); //TODO test it
    }

    int getMoverNumber() {      //TODO replace with getMover
        if (lastMovedPlayer == -1) {
            return nextPlayer(dealerNumber);
        }
        int nextPlayerNumber = nextPlayer(lastMovedPlayer);

        while (nextPlayerNumber != lastMovedPlayer) {
            final Player currentPlayer = players.get(nextPlayerNumber);
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
                bank.riseNeeded(player));
    }

    void addPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getDealer() {
        return (dealerNumber == -1) ? null : players.get(dealerNumber);
    }

    public Player smallBlindPlayer() {
        return null;
    }

    public Player bigBlindPlayer() {
        return null;  //TODO code me!
    }
}