package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
public class PlayersList extends ArrayList<Player> {
    private static final int PLAYER_NOT_FOUND = -1;

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
        if (!moreThanOnePlayerNotFolds()) {
            return false;
        }
        for (Player player : this) {
            if (player.isActiveNotRisePlayer()) {
                return true;
            }
        }
        return false;
    }

    public Player getMover() {
        return get(getMoverNumber());
    }

    private int getMoverNumber() {
        int currentPlayerNumber = nextPlayer(lastMovedPlayer);

        while (currentPlayerNumber != lastMovedPlayer) {
            final Player currentPlayer = get(currentPlayerNumber);
            if (currentPlayer.isActiveNotRisePlayer()) {
                return currentPlayerNumber;
            }
            currentPlayerNumber = nextPlayer(currentPlayerNumber);
        }
        return PLAYER_NOT_FOUND;
    }

    void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
        lastMovedPlayer = dealerNumber;
        System.out.println("Dealer number is: " + dealerNumber);
    }

    public Player smallBlindPlayer() {
        return get(nextPlayer(dealerNumber));
    }

    public Player bigBlindPlayer() {
        return get(nextPlayer(nextPlayer(dealerNumber)));
    }

    public String toJSON() {
        List<String> playersJSON = new ArrayList<>();
        for (Player player : this) {
            playersJSON.add(player.toJSON());
        }

        return JSONArray.fromCollection(playersJSON).toString();
    }

    public int getDealerNumber() {
        return dealerNumber;
    }

    public boolean moreThanOnePlayerWithActiveStatus() {
        int activePlayer = 0;
        for (Player player : this) {
            if (player.getStatus() != PlayerStatus.Fold &&
                    player.getStatus() != PlayerStatus.AllIn) {
                activePlayer++;
            }
        }

        return activePlayer > 1;
    }

    public void setPlayersNotMoved() {
        for (Player player : this) {
            PlayerStatus playerStatus = player.getStatus();
            if (playerStatus != PlayerStatus.Fold && playerStatus != PlayerStatus.AllIn)
                player.setStatus(PlayerStatus.NotMoved);
        }
        lastMovedPlayer = -1;
    }

    public boolean moreThanOnePlayerNotFolds() {
        int notFoldedPlayersQuantity = 0;
        for (Player player : this) {
            if (player.getStatus() != PlayerStatus.Fold) {
                notFoldedPlayersQuantity++;
            }
        }

        return notFoldedPlayersQuantity > 1;
    }

    public void setNewGame() {
        changeDealer();

        for (Player player : this) {
            player.setStatus(PlayerStatus.NotMoved);
        }
    }
}