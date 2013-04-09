package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.gameEvents.NewGameStartedEvent;
import com.nedogeek.holdem.gamingStuff.Player;
import com.nedogeek.holdem.gamingStuff.PlayersList;

/**
 * User: Konstantin Demishev
 * Date: 21.11.12
 * Time: 23:42
 */
class NewGameSetter {
    private final Dealer dealer;
    private final MoveManager moveManager;
    private final PlayersList playersList;
    private final EventManager eventManager;

    public NewGameSetter(Dealer dealer, PlayersList playersList, MoveManager moveManager,
                         EventManager eventManager) {
        this.dealer = dealer;
        this.playersList = playersList;
        this.moveManager = moveManager;
        this.eventManager = eventManager;
    }

    void setNewGame() {
        if (playersList.size() > 1) {
            dealer.setCallValue(0);
            playersList.setNewGame();

            resetCards();
            makeInitialBets();


            dealer.setNextGameRound();
            eventManager.addEvent(new NewGameStartedEvent());
        }
    }

    private void resetCards() {
        dealer.resetCards();

        for (Player player : playersList) {
            dealer.giveCardsToPlayer(player);
        }
    }

    private void makeInitialBets() {
        moveManager.makeSmallBlind(playersList.smallBlindPlayer());
        moveManager.makeBigBlind(playersList.bigBlindPlayer());
    }
}
