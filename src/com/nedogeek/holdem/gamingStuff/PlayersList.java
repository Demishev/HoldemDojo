package com.nedogeek.holdem.gamingStuff;

import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.dealer.EventManager;
import com.nedogeek.holdem.gameEvents.AddPlayerEvent;
import com.nedogeek.holdem.gameEvents.PlayerConnectedEvent;import com.nedogeek.holdem.gameEvents.RemovePlayerEvent;
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
    private final EventManager eventManager;

    private List<Player> waitingPlayers = new ArrayList<>();

    public PlayersList() {
        dealerNumber = 0;
        eventManager = EventManager.getInstance();
    }
    
    /**
     * Test purposes ONLY!
     */
    PlayersList(EventManager eventManager) {
    	dealerNumber = 0;
    	this.eventManager = eventManager;
    }
    @Override
    public boolean add(Player player) {
        eventManager.addEvent(new PlayerConnectedEvent(player));
        return !contains(player) && !waitingPlayers.contains(player) && waitingPlayers.add(player);
    }

    private void addWaitingPlayers() {
        for (Player player: waitingPlayers) {
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
		
		for (Player player: this) {
			if (player.getName().equals(((Player) o).getName())) {
				return true;
			}
		}
		return false;
	}
}