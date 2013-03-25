package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.Dealer;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.PlayerConnectedEvent;
import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:48
 */
public class PlayersList extends ArrayList<Player> {
    private static final int PLAYER_NOT_FOUND = -1;

    private int dealerNumber = 0;
    private int lastMovedPlayer;
    private final EventManager eventManager;

    private List<Player> waitingPlayers = new ArrayList<>();

    public PlayersList() {
        eventManager = EventManager.getInstance();
    }

    /**
     * Test purposes ONLY!
     */
    PlayersList(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public boolean add(Player player) {
        if (contains(player) || waitingPlayers.contains(player)) {
            return false;
        }

        if ((size() < 2)) {
            eventManager.addEvent(new AddPlayerEvent(player));
            return super.add(player);
        } else {
            eventManager.addEvent(new PlayerConnectedEvent(player));
            return waitingPlayers.add(player);
        }
    }

    private void addWaitingPlayers() {
        for (Player player : waitingPlayers) {
            super.add(player);
            eventManager.addEvent(new AddPlayerEvent(player));
        }
        waitingPlayers.clear();
    }

    @Override
    public boolean remove(Object player) {
        eventManager.addEvent(new RemovePlayerEvent((Player) player));
        return super.remove(player);
    }

    public void playerMoved(Player player) {
        lastMovedPlayer = indexOf(player);
    }

    private int nextPlayer(int playerNumber) {
        return (playerNumber == size() - 1) ?
                0 : playerNumber + 1;
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

    private void changeDealer() {
        dealerNumber = nextPlayer(dealerNumber);
        lastMovedPlayer = dealerNumber;
    }

    public Player smallBlindPlayer() {
        return get(nextPlayer(dealerNumber));
    }

    public Player bigBlindPlayer() {
        return get(nextPlayer(nextPlayer(dealerNumber)));
    }

    public String generatePlayersJSON(String... playerNames) {
        List<String> playersJSON = new ArrayList<>();

        for (Player player : this) {
            if (playerInPlayerNames(player, playerNames)) {
                playersJSON.add(player.toJSONWithCards());
            } else {
                playersJSON.add(player.toJSON());
            }
        }

        return playersJSON.toString();
    }

    private boolean playerInPlayerNames(Player player, String[] playerNames) {
        String playerName = player.getName();
        for (String wantedName : playerNames) {
            if (playerName.equals(wantedName)) {
                return true;
            }
        }
        return false;
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
        lastMovedPlayer = dealerNumber;
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
        addWaitingPlayers();

        for (Player player : this) {
            player.setStatus(PlayerStatus.NotMoved);
        }
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        for (Player player : this) {
            if (player.getName().equals(((Player) o).getName())) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayerByName(String login, Dealer dealer) {
        for (Player player : waitingPlayers) {
            if (player.getName().equals(login)) {
                return player;
            }
        }
        for (Player player : this) {
            if (player.getName().equals(login)) {
                return player;
            }
        }
        Player player = new Player(login, dealer);
        add(player);
        return player;
    }

    public int getPot() {
        int pot = 0;
        for (Player player : this) {
            pot += player.getBet();
        }
        return pot;
    }

    public String getDealerName() {
        return this.get(dealerNumber).getName();
    }

    public String getMoverName() {
        int moverNumber = getMoverNumber();

        return (moverNumber != -1) ? this.get(moverNumber).getName() : "";
    }

    public String getPlayerCardCombination(String playerName) {
        for (Player player : this) {
            if (player.getName().equals(playerName)) {
                return player.getCardCombination().toString();
            }
        }
        return "";
    }

    public void kickPlayer(String kickedPlayer) {
        //TODO Stub!
    }

    public List<String> getPlayerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : this) {
            names.add(player.getName());
        }
        return names;
    }
}