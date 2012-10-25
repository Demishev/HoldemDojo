package com.nedogeek.holdem.dealer;

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

    private int playersQuantity;

    Dealer(Desk desk) {
        this.desk = desk;
        playersQuantity = desk.getPlayersQuantity();
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
                changeGameRound();
            }
        }
    }

    private void changeGameRound() {
        desk.setGameRound(2);
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
        int gameRound = desk.getGameRound();
        switch (gameRound) {
            case 0:
                newGame();
                break;
            case 1:
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

        desk.setGameRound(1);
    }

    private void makeStartBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        if (playerAmount > bet) {
            makeBet(playerNumber, bet);
        } else {
            makeBet(playerNumber, playerAmount);
        }
    }

    private void makeMove(int playerNumber, PlayersAction playerMove) {
        switch (playerMove.getActionType()) {
            case Fold:
                playerFolds(playerNumber);
                break;
            case Check:
                break;
            case Call:
                desk.setPlayerStatus(playerNumber,PlayerStatus.Call);
                makeBet(playerNumber, desk.getCallValue() - desk.getPlayerBet(playerNumber));
                break;
            case Rise:
                final int bet = playerMove.getBetQuantity();

                if (isAllInMove(playerNumber, bet)) {
                    makeAllIn(playerNumber);
                } else {
                    makeRise(playerNumber, bet);
                }
                break;
            case AllIn:
                makeAllIn(playerNumber);
                break;
        }
        desk.setLastMovedPlayer(playerNumber);
    }

    private boolean isAllInMove(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        return playerAmount <= bet;
    }

    private int minimumRiseValue() {
        return desk.getCallValue() + 2 * GameSettings.SMALL_BLIND_AT_START;
    }

    private void playerFolds(int playerNumber) {
        desk.setPlayerStatus(playerNumber, PlayerStatus.Fold);
    }

    private void makeRise(int playerNumber, int bet) {
        desk.setPlayerStatus(playerNumber, PlayerStatus.Rise);

        if (desk.getPlayerBet(playerNumber) + bet >= minimumRiseValue()) {
            makeBet(playerNumber, bet);
        } else {
            makeBet(playerNumber, minimumRiseValue() - desk.getPlayerBet(playerNumber));
        }
    }

    private void makeBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        final int previousBet = desk.getPlayerBet(playerNumber);
        desk.setPlayerBet(playerNumber, bet + previousBet);
        desk.addToPot(bet);
        desk.setPlayerAmount(playerNumber, playerAmount - bet);
        desk.setCallValue(bet);
    }

    private void makeAllIn(int playerNumber) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        desk.setPlayerStatus(playerNumber, PlayerStatus.AllIn);
        makeBet(playerNumber, playerAmount);
    }

    private int nextPlayer(int playerNumber) {
        if (playerNumber == playersQuantity - 1) {
            return 0;
        } else {
            return playerNumber + 1;
        }
    }
}
