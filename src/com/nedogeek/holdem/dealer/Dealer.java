package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.connections.PlayersAction;
import com.nedogeek.holdem.gamingStuff.Desk;

/**
 * User: Konstantin Demishev
 * Date: 05.10.12
 * Time: 22:02
 */
public class Dealer implements Runnable {
    private final Desk desk;

    private MoveManager moveManager;

    private int playersQuantity;

    Dealer(Desk desk) {
        this.desk = desk;
        playersQuantity = desk.getPlayersQuantity();
        moveManager = new MoveManager(desk);
    }

    public void run() {
        tick();
    }

    void tick() {
        if (desk.getGameStatus().equals(GameStatus.Ready)) {
            prepareNewGameCycle();
        }
        if (desk.getGameStatus().equals(GameStatus.Started)) {
            if (hasAvailableMovers()) {
                makeMove();
            } else {
                setNextGameRound();
            }
        }
    }

    private void setNextGameRound() {
        desk.setNextGameRound();
    }

    private boolean hasAvailableMovers() {
        for (int i = 0; i < desk.getPlayersQuantity(); i++) {
            PlayerStatus playerStatus = desk.getPlayerStatus(i);
            if (playerStatus.equals(PlayerStatus.NotMoved)) {
                return true;
            }
        }
        return false;
    }

    private void makeMove() {
        GameRound gameRound = desk.getGameRound();
        switch (gameRound) {
            case INITIAL:
                newGame();
                break;
            case BLIND:
                int lastMovedPlayer = desk.getLastMovedPlayer();
                int moverNumber;
                if (lastMovedPlayer != -1) {
                    moverNumber = nextPlayer(lastMovedPlayer);
                } else {
                    moverNumber = nextPlayer(desk.getDealerPlayerNumber());
                }
                PlayersAction answer = desk.getPlayersMove(moverNumber);
                makeMove(moverNumber, answer);
                break;
        }
    }

    private void prepareNewGameCycle() {
        desk.setGameStatus(GameStatus.Started);
        playersQuantity = desk.getPlayersQuantity();
        for (int i = 0; i < playersQuantity; i++) {
            desk.setPlayerAmount(i, GameSettings.COINS_AT_START);
        }
    }

    private void newGame() {
        desk.shuffleCards();

        int dealerPlayerNumber = nextPlayer(desk.getDealerPlayerNumber());
        desk.setDealerPlayer(dealerPlayerNumber);

        int smallBlindPlayerNumber = nextPlayer(dealerPlayerNumber);
        makeStartBet(smallBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START);

        int bigBlindPlayerNumber = nextPlayer(smallBlindPlayerNumber);
        makeStartBet(bigBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START * 2);

        desk.setNextGameRound();
    }

    private void makeStartBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        if (playerAmount > bet) {
            moveManager.makeBet(playerNumber, bet);
        } else {
            moveManager.makeBet(playerNumber, playerAmount);
        }
    }

    private void makeMove(int playerNumber, PlayersAction playerMove) {
        switch (playerMove.getActionType()) {
            case Fold:
                moveManager.makeFold(playerNumber);
                break;
            case Check:
                break;
            case Call:
                moveManager.makeCall(playerNumber);
                break;
            case Rise:
                moveManager.makeRise(playerNumber, playerMove.getBetQuantity());
                break;
            case AllIn:
                moveManager.makeAllIn(playerNumber);
                break;
        }
        desk.setLastMovedPlayer(playerNumber);
    }

    private int nextPlayer(int playerNumber) {
        if (playerNumber == playersQuantity - 1) {
            return 0;
        } else {
            return playerNumber + 1;
        }
    }
}
