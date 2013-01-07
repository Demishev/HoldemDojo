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


    public void setLastMovedPlayer(int lastMovedPlayer) {
        this.lastMovedPlayer = lastMovedPlayer;
    }

    int nextPlayer(int playerNumber) { //TODO make it private
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

//    @Deprecated
//    PlayerAction getPlayerMove() {
//        return players.get(getMoverNumber()).getMove();
//    }

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

    @Deprecated
    int getPlayersQuantity() {
        return players.size();
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Deprecated
    public void setPlayerStatus(int playerNumber, PlayerStatus playerStatus) {
        //TODO removeMe
    }

    @Deprecated
    public PlayerStatus getPlayerStatus(int playerNumber) {
        return null;
    }

    public void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
    }

    @Deprecated
    public boolean isFirstCombinationBiggerThanSecond(int biggerCombination, int smallerCombination) {
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getDealer() {
        return (dealerNumber == -1) ? null : players.get(dealerNumber);
    }

    public int smallBlindPlayerNumber() {
        return 0;
    }

    public int bigBlindPlayerNumber() {
        return 0;
    }
}