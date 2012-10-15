package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.GameStatus;
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
        makeBet(smallBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START);

        int bigBlindPlayerNumber = nextPlayer(smallBlindPlayerNumber);
        makeBet(bigBlindPlayerNumber, GameSettings.SMALL_BLIND_AT_START * 2);

        desk.setNextGameRound();
    }

    private void makeMove(int playerNumber, PlayersAction playerMove) {
        switch (playerMove.getActionType()) {
            case Fold:
                playerFolds(playerNumber);
                break;
            case Check:
                break;
            case Call:
                makeBet(playerNumber, desk.getCallValue() - desk.getPlayerBet(playerNumber));
                break;
            case Bet:
                if (desk.getPlayerBet(playerNumber) + playerMove.getBetQuantity() > desk.getCallValue()) {
                    makeBet(playerNumber, playerMove.getBetQuantity());
                } else {
                    playerFolds(playerNumber);
                }
                break;
            case AllIn:
                break;
        }
        desk.setLastMovedPlayer(playerNumber);
    }

    private void playerFolds(int playerNumber) {
        desk.setPlayerFold(playerNumber);
    }

    private void makeBet(int playerNumber, int bet) {
        final int playerAmount = desk.getPlayerAmount(playerNumber);
        if (playerAmount < bet) {
            bet = playerAmount;
        }
        desk.setPlayerBet(playerNumber, bet);
        desk.addToPot(bet);
        desk.setPlayerAmount(playerNumber, playerAmount - bet);
        desk.setCallValue(bet);
    }

    private int nextPlayer(int playerNumber) {
        if (playerNumber == playersQuantity - 1) {
            return 0;
        } else {
            return playerNumber + 1;
        }
    }
}
